package com.chrisaraneo.mwl.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.BadRequestException;
import com.chrisaraneo.mwl.exception.ForbiddenException;
import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.RoleName;
import com.chrisaraneo.mwl.model.SongPlaylist;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.model.extended.PlaylistWithSongs;
import com.chrisaraneo.mwl.repository.PlaylistRepository;
import com.chrisaraneo.mwl.repository.SongPlaylistRepository;
import com.chrisaraneo.mwl.repository.UserRepository;
import com.chrisaraneo.mwl.security.CurrentUser;
import com.chrisaraneo.mwl.security.UserPrincipal;


@RestController
@RequestMapping("/api")
public class PlaylistController {

    @Autowired
    PlaylistRepository playlistRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    SongPlaylistRepository songPlaylistRepository;
    
    
    @GetMapping("/playlists")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public List<Playlist> getAllPlaylists(@CurrentUser UserPrincipal currentUser) {
    	Long id = currentUser.getID();
        return playlistRepository.findAllUserPlaylists(id);
    }
    
    @GetMapping("/playlists/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public Playlist getPlaylistById(
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer playlistID) {
    	
    	Long userID = currentUser.getID();
    	
    	Playlist playlist = playlistRepository.findUserPlaylist(userID, playlistID);
    	if(playlist == null) {
    		throw new BadRequestException("This playlist doesn't exist or user is not authorized to GET this playlist.");
    	}
    	
    	Set<SongPlaylist> songs = songPlaylistRepository.findAllSongsInPlaylist(playlistID);
		return new PlaylistWithSongs(playlist, songs);
    }

    @PostMapping("/playlists")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public Playlist createPlaylist(
    		@Valid @RequestBody Playlist playlist,
    		@CurrentUser UserPrincipal currentUser) {
    	
    	String title = playlist.getTitle();
    	if(title != null && !title.isEmpty()) {
    		Optional<User> user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getPassword());
	    	if(user.isPresent()) {
	    		playlist.setUser(user.get());
	    		return playlistRepository.save(playlist);
	    	} else {
	    		throw new ResourceNotFoundException("User", "id", currentUser.getID());
	    	}
    	} else {
    		throw new BadRequestException("Invalid playlist title");
    	}
    }
    
    @PutMapping("/playlists/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public Playlist updatePlaylist(
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer playlistID,
            @Valid @RequestBody Playlist modified) {

        Playlist playlist = playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));

        String email = currentUser.getEmail();
    	String username = currentUser.getUsername();
    	
    	Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    	if(user.isPresent()) {
    		if(playlist.getUser().getID() == user.get().getID()) {
    			playlist.setUser(user.get());
    		    playlist.setTitle(modified.getTitle());
    		    return playlistRepository.save(playlist);
    		} else {
    			throw new ForbiddenException("This user is not an owner of this review");
    		}
    	} else {
    		throw new BadRequestException("User information is not provided in request");
    	}
    }
    
    private void removeAllSongsFromPlaylist(Playlist playlist) {
    	Integer playlistID = playlist.getPlaylistID();
    	Set<SongPlaylist> sp = songPlaylistRepository.findAllSongsInPlaylist(playlistID);
    	for(SongPlaylist song : sp) {
    		Integer songID = song.getSong().getSongID();
    		Integer track = song.getId().getTrackNumber();
    		SongPlaylistKey id = new SongPlaylistKey(track, playlist);
    		songPlaylistRepository.deleteById(id);
    	}
    	songPlaylistRepository.flush();
    	playlistRepository.flush();
    }
    
    @DeleteMapping("/playlists/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmptyJson> deletePlaylist(
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer playlistID) {
    	
    	Playlist playlist = playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
    	
    	if(currentUser.hasRole((RoleName.ROLE_ADMIN).toString())) {
    		removeAllSongsFromPlaylist(playlist);
    		playlistRepository.delete(playlist);
    		playlistRepository.flush();
    		return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    	} else {
    		String email = currentUser.getEmail();
    		String username = currentUser.getUsername();
    		Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    		if(user.isPresent()) {
    			User u = playlist.getUser();
    			if(u != null) {
    				Optional<User> creator = userRepository.findByUsernameOrEmail(u.getUsername(), u.getEmail());
    				if(creator.isPresent()) {
    					if(creator.get().getID() == u.getID()) {
    						removeAllSongsFromPlaylist(playlist);
    						playlistRepository.delete(playlist);
    						playlistRepository.flush();
    						return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    					} else {
    						return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.FORBIDDEN);
    					}
    				}
    			}
    		}
    	}
    	
    	throw new ResourceNotFoundException("Playlist", "id", playlistID);
    }
    
}