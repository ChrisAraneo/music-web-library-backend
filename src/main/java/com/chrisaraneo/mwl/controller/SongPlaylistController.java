package com.chrisaraneo.mwl.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

class SortByTrack implements Comparator<SongPlaylist> 
{ 
	@Override
	public int compare(SongPlaylist arg0, SongPlaylist arg1) {
		SongPlaylistKey key0 = arg0.getId();
		SongPlaylistKey key1 = arg0.getId();
		return key0.getTrackNumber() - key1.getTrackNumber();
	} 
} 

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
	
    private Integer updateTracksAndReturnNext(Playlist playlist) {
    	Integer playlistID = playlist.getPlaylistID();
	  	
	  	// Getting elements
	  	Set<SongPlaylist> sps = songPlaylistRepository.findAllSongsInPlaylist(playlistID);
	  	List<SongPlaylist> list = new ArrayList<SongPlaylist>();
	  	for(SongPlaylist sp : sps) {
	  		list.add(sp);
	  	}
	  	
	  	// Sorting by track
	  	Collections.sort(list, new SortByTrack());
	  	
	  	// Updating - removing old and adding new
	  	int length = list.size();
	  	for(int i=0; i<length; i++) {
	  		SongPlaylist ps = list.get(i);
	  		Song song = ps.getSong();
	  		
	  		songPlaylistRepository.delete(ps);
	  		
	  		SongPlaylist ps2 = new SongPlaylist();
	  		ps2.setId(new SongPlaylistKey(i+1, playlist));
	  		ps2.setSong(song);
	  		
	  		songPlaylistRepository.save(ps2);
	  		songPlaylistRepository.flush();
	  	}
	  	
	  	return length + 1;
    }
    
	@PostMapping("/playlists/{playlistID}/{songID}")
	@Secured("ROLE_ADMIN")
	public SongPlaylist addSongToPlaylist(
	  		@PathVariable(value = "playlistID") Integer playlistID,
	  		@PathVariable(value = "songID") Integer songID) {
	  	
	  	Playlist playlist = playlistRepository.findById(playlistID)
	              .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
	  	
	  	// Update tracks...
	  	Integer next = this.updateTracksAndReturnNext(playlist);
	  	
	  	Song song = songRepository.findById(songID)
	              .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
	  	
	  	SongPlaylistKey id = new SongPlaylistKey(next, playlist);
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
    		  	
    		  	this.updateTracksAndReturnNext(playlist);
    		  	
    		  	Playlist updated = playlistRepository.findById(playlistID)
    		  			.orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
    		  	Set<SongPlaylist> songsInUpdated = songPlaylistRepository.findAllSongsInPlaylist(playlistID);
    		  	return Optional.ofNullable(new PlaylistWithSongs(updated, songsInUpdated));
    		}
    	}
    	
    	throw new BadRequestException("Unauthorized operation");
	}
	
}
