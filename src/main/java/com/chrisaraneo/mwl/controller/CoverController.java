package com.chrisaraneo.mwl.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Cover;
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
    public Cover createCover(@Valid Cover cover, @RequestParam("album") Integer albumID) {
    	Album album = albumRepository.findById(albumID)
    			.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
	    
    	album.setCover(cover);
    	
    	albumRepository.save(album);
        return coverRepository.save(cover);
    }

    @PutMapping("/covers/{id}")
    public Cover updateCover(@PathVariable(value = "id") Integer coverID,
                                           @Valid Cover modified) {

        Cover cover = coverRepository.findById(coverID)
                .orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));

        cover.setData(modified.getData());

        return coverRepository.save(cover);
    }

    @DeleteMapping("/covers/{id}")
    public ResponseEntity<?> deleteCover(@PathVariable(value = "id") Integer coverID) {
        Cover cover = coverRepository.findById(coverID)
                .orElseThrow(() -> new ResourceNotFoundException("Cover", "id", coverID));

        coverRepository.delete(cover);

        return ResponseEntity.ok().build();
    }
}