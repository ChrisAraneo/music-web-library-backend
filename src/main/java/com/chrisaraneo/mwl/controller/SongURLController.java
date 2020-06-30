package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongURL;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.repository.SongURLRepository;

import java.util.List;

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
    public SongURL createSongURL(@Valid SongURL url, @RequestParam("song") Integer songID) throws ResourceNotFoundException {
    	Song song = songRepository.findById(songID)
    		.orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songID));
    	url.setSong(song);

        return songURLRepository.save(url);
    }

    @PutMapping("/songurls/{id}")
    @Secured("ROLE_ADMIN")
    public SongURL updateSongURL(@PathVariable(value = "id") Integer songURLID,
                                           @Valid @ModelAttribute SongURL modified) {

        SongURL url = songURLRepository.findById(songURLID)
        		.orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songURLID));

        url.setURL(modified.getURL());
        url.setSong(modified.getSong());

        return songURLRepository.save(url);
    }

    @DeleteMapping("/songurls/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteSongURL(@PathVariable(value = "id") Integer songURLID) {
        SongURL url = songURLRepository.findById(songURLID)
                .orElseThrow(() -> new ResourceNotFoundException("SongURL", "id", songURLID));

        songURLRepository.delete(url);

        return ResponseEntity.ok().build();
    }
}