package com.chrisaraneo.mwl.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Cover;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.CoverRepository;


@RestController
@RequestMapping("/api")
public class CoverController {

    @Autowired
    CoverRepository coverRepository;
    
    @Autowired
    AlbumRepository albumRepository;

    @GetMapping("/covers")
    public List<Cover> getAllCovers() {
        return coverRepository.findAll();
    }
    
    @GetMapping("/covers/{id}")
    public Cover getCoverById(@PathVariable(value = "id") Integer coverID) {
        return coverRepository.findById(coverID)
                .orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));
    }

    @PostMapping("/covers")
    @Secured("ROLE_ADMIN")
    public Cover createCover(@Valid @RequestBody Cover cover) {
        return coverRepository.save(cover);
    }
    
    @PostMapping("/covers/{coverID}/{albumID}")
    @Secured("ROLE_ADMIN")
    public Album attachCoverToAlbum(
    		@PathVariable(value = "coverID") Integer coverID,
    		@PathVariable(value = "albumID") Integer albumID) {
    	Cover cover = coverRepository.findById(coverID)
    			.orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));
    	
    	Album album = albumRepository.findById(albumID)
    			.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	album.setCover(cover);
        return albumRepository.save(album);
    }

    @DeleteMapping("/covers/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<EmptyJson> deleteCover(@PathVariable(value = "id") Integer coverID) {
        Cover cover = coverRepository.findById(coverID)
                .orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));

        List<Album> albums = albumRepository.findAllAlbumsWithCover(coverID);
        
        for(Album album : albums) {
        	album.setCover(null);
        	albumRepository.save(album);
        }
        albumRepository.flush();
        
        coverRepository.delete(cover);
        return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    }
    
    @DeleteMapping("/covers/{coverID}/{albumID}")
    @Secured("ROLE_ADMIN")
    public Album detachCoverToAlbum(
    		@PathVariable(value = "coverID") Integer coverID,
    		@PathVariable(value = "albumID") Integer albumID) {
    	Cover cover = coverRepository.findById(coverID)
                .orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));
    	
    	Album album = albumRepository.findById(albumID)
    			.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	Cover albumsCover = album.getCover();
    	if(albumsCover != null) {
    		if(cover.getCoverID() == albumsCover.getCoverID()) {
    			album.setCover(null);
    		}
    	}
    	
        return albumRepository.save(album);
    }
}