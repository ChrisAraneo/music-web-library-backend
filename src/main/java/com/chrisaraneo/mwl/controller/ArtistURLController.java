package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.repository.ArtistRepository;
import com.chrisaraneo.mwl.repository.ArtistURLRepository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ArtistURLController {

    @Autowired
    ArtistURLRepository artistURLRepository;
    
    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artisturls")
    public List<ArtistURL> getArtistURLs() {
        return artistURLRepository.findAll();
    }
    
    @GetMapping("/artisturls/{id}")
    public ArtistURL getArtistURLByID(@PathVariable(value = "id") Integer artistURLID) {
        return artistURLRepository.findById(artistURLID)
        		.orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistURLID));
    }
    
    @PostMapping("/artisturls")
    public ArtistURL createArtistURL(@Valid ArtistURL url, @RequestParam("artist") Integer artistID) throws ResourceNotFoundException {
    	Artist artist = artistRepository.findById(artistID)
    		.orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistID));
    	url.setArtist(artist);
    	
        return artistURLRepository.save(url);
    }

    @PutMapping("/artisturls/{id}")
    public ArtistURL updateArtistURL(@PathVariable(value = "id") Integer artistURLID,
                                           @Valid @ModelAttribute ArtistURL modified) {

        ArtistURL url = artistURLRepository.findById(artistURLID)
        		.orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistURLID));

        url.setURL(modified.getURL());
        url.setArtist(modified.getArtist());

        return artistURLRepository.save(url);
    }

    @DeleteMapping("/artisturls/{id}")
    public ResponseEntity<?> deleteArtistURL(@PathVariable(value = "id") Integer artistURLID) {
        ArtistURL url = artistURLRepository.findById(artistURLID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistURLID));

        artistURLRepository.delete(url);

        return ResponseEntity.ok().build();
    }
}