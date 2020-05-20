package com.chrisaraneo.mwl.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.repository.SongRepository;

public class SongController {

    @Autowired
    SongRepository songRepository;

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songRepository.findAll();
    }
    
    @GetMapping("/songs/{id}")
    public Song getSongByID(@PathVariable(value = "id") Integer songID) {
        return songRepository.findById(songID)
        		.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    }

    @PostMapping("/songs")
    public @Valid Song createSong(@Valid Song song) {
        return songRepository.save(song);
    }

    @PutMapping("/songs/{id}")
    public Song updateSong(@PathVariable(value = "id") Integer songID,
                                           @Valid @ModelAttribute Song modified) {

        Song song = songRepository.findById(songID)
        		.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));

        song.setAlbums(modified.getAlbums());
        song.setArtists(modified.getArtists());
        song.setBpm(modified.getBpm());
        song.setComment(modified.getComment());
        song.setGenre(modified.getGenre());
        song.setLanguage(modified.getLanguage());
        song.setLength(modified.getLength());
        song.setMainKey(modified.getMainKey());
        song.setPublisher(modified.getPublisher());
//        song.setSongsPlaylists(modified.getSongsPlaylists());
//        song.setSongURLs(modified.getSongURLs());
        song.setTerms(modified.getTerms());
        song.setTitle(modified.getTitle());
        song.setWebsite(modified.getWebsite());
        song.setYear(modified.getYear());
        
        return songRepository.save(song);
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable(value = "id") Integer songID) {
        Song song = songRepository.findById(songID)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));

        songRepository.delete(song);

        return ResponseEntity.ok().build();
    }
}