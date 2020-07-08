package com.chrisaraneo.mwl.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.BadRequestException;
import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongPlaylist;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.model.extended.PlaylistWithSongs;
import com.chrisaraneo.mwl.repository.PlaylistRepository;
import com.chrisaraneo.mwl.repository.SongPlaylistRepository;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.repository.UserRepository;
import com.chrisaraneo.mwl.security.CurrentUser;
import com.chrisaraneo.mwl.security.UserPrincipal;

@RestController
@RequestMapping("/api")
public class SongPlaylistController {

    @Autowired
    PlaylistRepository playlistRepository;
    
    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongPlaylistRepository songPlaylistRepository;
    
    @Autowired
    UserRepository userRepository;
	
    
	@PostMapping("/playlists/{playlistID}/{songID}/{track}")
	@Secured("ROLE_ADMIN")
	public SongPlaylist addSongToPlaylist(
	  		@PathVariable(value = "playlistID") Integer playlistID,
	  		@PathVariable(value = "songID") Integer songID,
	  		@PathVariable(value = "track") Integer track) {
	  	
	  	Playlist playlist = playlistRepository.findById(playlistID)
	              .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
	  	
	  	Song song = songRepository.findById(songID)
	              .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
	  	
	  	SongPlaylistKey id = new SongPlaylistKey(track, playlist);
	  	SongPlaylist sp = songPlaylistRepository.save(new SongPlaylist(id, song));
	  	return sp;
	}
	
	@DeleteMapping("/playlists/{playlistID}/{track}")
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
	public Optional<PlaylistWithSongs> removeSongFromPlaylist(
	  		@PathVariable(value = "playlistID") Integer playlistID,
	  		@PathVariable(value = "track") Integer track,
	  		@CurrentUser UserPrincipal currentUser) {
	  	
	  	Playlist playlist = playlistRepository.findById(playlistID)
	              .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
	  	
	  	String email = currentUser.getEmail();
    	String username = currentUser.getUsername();
    	
    	Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    	if(user.isPresent()) {
    		if(playlist.getUser().getID() == user.get().getID()) {
    			SongPlaylist song = songPlaylistRepository.findSongByTrackInPlaylist(track, playlistID);
    		  	songPlaylistRepository.deleteById(song.getId());
    		  	songPlaylistRepository.flush();
    		  	playlistRepository.flush();
    		  	
    		  	Playlist updated = playlistRepository.findById(playlistID)
    		  			.orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
    		  	Set<SongPlaylist> songsInUpdated = songPlaylistRepository.findAllSongsInPlaylist(playlistID);
    		  	return Optional.ofNullable(new PlaylistWithSongs(updated, songsInUpdated));
    		}
    	}
    	
    	throw new BadRequestException("Unauthorized operation");
	}
	
}
