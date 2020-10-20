package com.chrisaraneo.mwl.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.extended.AlbumWithSongs;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.SongAlbumRepository;
import com.chrisaraneo.mwl.repository.SongRepository;

@RestController
@RequestMapping("/api")
public class SongAlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongAlbumRepository songAlbumRepository;
	
    
	@PostMapping("/albums/{albumID}/{songID}/{track}")
	@Secured("ROLE_ADMIN")
	public Album addSongToAlbum(
	  		@PathVariable(value = "albumID") Integer albumID,
	  		@PathVariable(value = "songID") Integer songID,
	  		@PathVariable(value = "track") Integer track) {
	  	
	  	Album album = albumRepository.findById(albumID)
	              .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
	  	
	  	Song song = songRepository.findById(songID)
	              .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
	  	
	  	SongAlbumKey id = new SongAlbumKey(track, album);
	  	SongAlbum sa = songAlbumRepository.save(new SongAlbum(id, song));
	  	
	  	albumRepository.flush();
	  	
    	Set<SongAlbum> songs = songAlbumRepository.findAllSongsInAlbum(albumID);
	  	return new AlbumWithSongs(album, songs);
	}
	
	@DeleteMapping("/albums/{albumID}/{songID}/{track}")
	@Secured("ROLE_ADMIN")
	public Album removeSongFromAlbum(
	  		@PathVariable(value = "albumID") Integer albumID,
	  		@PathVariable(value = "songID") Integer songID,
	  		@PathVariable(value = "track") Integer track) {
	  	
	  	Album album = albumRepository.findById(albumID)
	              .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
	  	
	  	songRepository.findById(songID)
	              .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
	  	
	  	SongAlbumKey id = new SongAlbumKey(track, album);
	  	songAlbumRepository.deleteById(id);
	  	
	  	albumRepository.flush();
	  	
	  	Set<SongAlbum> songs = songAlbumRepository.findAllSongsInAlbum(albumID);
	  	return new AlbumWithSongs(album, songs);
	}
	
}
