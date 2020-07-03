package com.chrisaraneo.mwl.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.SongPlaylist;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.PlaylistRepository;
import com.chrisaraneo.mwl.repository.SongAlbumRepository;
import com.chrisaraneo.mwl.repository.SongPlaylistRepository;
import com.chrisaraneo.mwl.repository.SongRepository;

@RestController
@RequestMapping("/api")
public class SongPlaylistController {

    @Autowired
    PlaylistRepository playlistRepository;
    
    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongPlaylistRepository songPlaylistRepository;
	
    
	@PostMapping("/playlist/{playlistID}/{songID}/{track}")
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
	
	@DeleteMapping("/playlist/{playlistID}/{songID}/{track}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> removeSongFromPlaylist(
	  		@PathVariable(value = "playlistID") Integer playlistID,
	  		@PathVariable(value = "songID") Integer songID,
	  		@PathVariable(value = "track") Integer track) {
	  	
	  	Playlist playlist = playlistRepository.findById(playlistID)
	              .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
	  	
	  	Song song = songRepository.findById(songID)
	              .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
	  	
	  	SongPlaylistKey id = new SongPlaylistKey(track, playlist);
	  	songPlaylistRepository.deleteById(id);
	  	
	  	return ResponseEntity.ok().build();
	}
	
}
