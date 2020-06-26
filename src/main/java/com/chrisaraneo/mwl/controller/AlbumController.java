package com.chrisaraneo.mwl.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.SongRepository;

@RestController
@RequestMapping("/api")
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    @Autowired
    SongRepository songRepository;

    @GetMapping("/albums")
    @CrossOrigin(origins = "*")
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }
    
    @GetMapping("/albums/{id}")
    @CrossOrigin(origins = "*")
    public Album getAlbumById(@PathVariable(value = "id") Integer albumID) {
        return albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    }

    @PostMapping("/albums")
    public Album createAlbum(@Valid Album album) {
        return albumRepository.save(album);
    }
    
    @PostMapping("/albums/{albumID}/{songID}")
    public Album addSongToAlbum(
    		@PathVariable(value = "albumID") Integer albumID,
    		@PathVariable(value = "songID") Integer songID) {
    	
    	Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	Song song = songRepository.findById(songID)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	album.getSongs().add(song);
    	song.getAlbums().add(album);
    	
    	songRepository.save(song);
        return albumRepository.save(album);
    }

    @PutMapping("/album/{id}")
    public Album updateAlbum(@PathVariable(value = "id") Integer albumID,
                                           @Valid Album modified) {

        Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        album.setTitle(modified.getTitle());
        album.setYear(modified.getYear());

        return albumRepository.save(album);
    }

    @DeleteMapping("/album/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable(value = "id") Integer albumID) {
        Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        albumRepository.delete(album);

        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/album/{albumID}/{songID}")
    public ResponseEntity<?> deleteSongFromAlbum(
    		@PathVariable(value = "albumID") Integer albumID,
    		@PathVariable(value = "songID") Integer songID) {
    	
    	Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	Song song = songRepository.findById(songID)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	album.getSongs().remove(song);
    	song.getAlbums().remove(album);

        return ResponseEntity.ok().build();
    }
    
}