package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongURL;
import com.chrisaraneo.mwl.model.extended.ArtistWithURLs;
import com.chrisaraneo.mwl.model.extended.SongWithURLs;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.repository.SongURLRepository;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SongURLController {

    @Autowired
    SongURLRepository songURLRepository;
    
    @Autowired
    SongRepository songRepository;

    @GetMapping("/songurls")
    public List<SongURL> getSongURLs() {
        return songURLRepository.findAll();
    }
    
    @GetMapping("/songurls/{id}")
    public SongURL getSongURLByID(@PathVariable(value = "id") Integer songURLID) {
        return songURLRepository.findById(songURLID)
        		.orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songURLID));
    }
    
    @PostMapping("/songurls")
    @Secured("ROLE_ADMIN")
    public Song createSongURL(@Valid @RequestBody SongURL url) throws ResourceNotFoundException {
    	
    	Song A = url.getSong();
    	if(A != null) {
    		Integer songID = A.getSongID();
    		Song song = songRepository.findById(songID)
    				.orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songID));
    		url.setSong(song);
    		songURLRepository.save(url);
    		Set<SongURL> urls = songURLRepository.findAllBySong(song.getSongID());
        	
            return new SongWithURLs(song, urls);
    	} else {
    		throw new ResourceNotFoundException("SongURL", "id", null);
    	}
    	
    }

    @DeleteMapping("/songurls/{id}")
    @Secured("ROLE_ADMIN")
    public Song deleteSongURL(@PathVariable(value = "id") Integer songURLID) {
    	
        SongURL url = songURLRepository.findById(songURLID)
                .orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songURLID));

        Integer songID = url.getSong().getSongID();
        
        songURLRepository.delete(url);
        songURLRepository.flush();
        
        Song song = songRepository.findById(songID)
        		.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));

        Set<SongURL> urls = songURLRepository.findAllBySong(songID);
    	return new SongWithURLs(song, urls);
    }
}