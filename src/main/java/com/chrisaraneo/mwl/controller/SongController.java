package com.chrisaraneo.mwl.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongURL;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.model.extended.SongWithURLs;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.repository.SongURLRepository;


@RestController
@RequestMapping("/api")
public class SongController {

    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongURLRepository songURLRepository;

    
    @GetMapping("/songs")
    @CrossOrigin(origins = "*")
    public Object getAllSongs() {
        return songRepository.findAllBy(); 
    }
    
    @GetMapping("/songs/{id}")
    @CrossOrigin(origins = "*")
    public Song getSongByID(@PathVariable(value = "id") Integer songID) {
    	Song song = songRepository.findById(songID)
        		.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	Set<SongURL> urls = songURLRepository.findAllBySong(song.getSongID());
    	return new SongWithURLs(song, urls);
    }

    @PostMapping("/songs")
    @Secured("ROLE_ADMIN")
    public @Valid Song createSong(@Valid @RequestBody Song song) {
        return songRepository.save(song);
    }

    @PutMapping("/songs/{id}")
    @Secured("ROLE_ADMIN")
    public Song updateSong(
    		@PathVariable(value = "id") Integer songID,
            @Valid @RequestBody Song modified) {

        Song song = songRepository.findById(songID)
        		.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));

        song.setArtists(modified.getArtists());
        song.setBpm(modified.getBpm());
        song.setComment(modified.getComment());
        song.setGenre(modified.getGenre());
        song.setLanguage(modified.getLanguage());
        song.setLength(modified.getLength());
        song.setMainKey(modified.getMainKey());
        song.setPublisher(modified.getPublisher());
        song.setTerms(modified.getTerms());
        song.setTitle(modified.getTitle());
        song.setWebsite(modified.getWebsite());
        song.setYear(modified.getYear());
        
        return songRepository.save(new Song(modified));
    }

    @DeleteMapping("/songs/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<EmptyJson> deleteSong(@PathVariable(value = "id") Integer songID) {
        Song song = songRepository.findById(songID)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));

        songRepository.delete(song);

        return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    }
}