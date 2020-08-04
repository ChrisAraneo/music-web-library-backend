package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.model.extended.ArtistDetailed;
import com.chrisaraneo.mwl.model.extended.ArtistWithURLs;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.ArtistRepository;
import com.chrisaraneo.mwl.repository.ArtistURLRepository;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ArtistURLController {

    @Autowired
    ArtistURLRepository artistURLRepository;
    
    @Autowired
    ArtistRepository artistRepository;
    
    @Autowired
    AlbumRepository albumRepository;

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
//    @Secured("ROLE_ADMIN")
    public Artist createArtistURL(@Valid @RequestBody ArtistURL url) throws ResourceNotFoundException {
    	
    	Artist A = url.getArtist();
    	if(A != null) {
    		Integer artistID = A.getArtistID();
    		Artist artist = artistRepository.findById(artistID)
    				.orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistID));
    		url.setArtist(artist);
    		
    		artistURLRepository.save(url);
    		
    		Set<ArtistURL> urls = artistURLRepository.findAllByArtist(artistID);
            return new ArtistWithURLs(artist, urls);
    	} else {
    		throw new ResourceNotFoundException("ArtistURL", "id", null);
    	}
    	
    }

    @DeleteMapping("/artisturls/{id}")
    @Secured("ROLE_ADMIN")
    public Artist deleteArtistURL(@PathVariable(value = "id") Integer artistURLID) {
        ArtistURL url = artistURLRepository.findById(artistURLID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistURLID));

        Integer artistID = url.getArtist().getArtistID();
        
        artistURLRepository.delete(url);
        artistURLRepository.flush();
        artistRepository.flush();
        
        Artist artist = artistRepository.findById(artistID)
        		.orElseThrow(() -> new ResourceNotFoundException("ArtistURL", "id", artistID));
        
        Set<ArtistURL> urls = artistURLRepository.findAllByArtist(artistID);

        return new ArtistWithURLs(artist, urls);
    }
}