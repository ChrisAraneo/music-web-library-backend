package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.repository.ArtistTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ArtistTypeController {

    @Autowired
    ArtistTypeRepository artistTypeRepository;

    @GetMapping("/artisttypes")
    public List<ArtistType> getAllArtistTypes() {
        return artistTypeRepository.findAll();
    }
    
    @GetMapping("/artisttypes/{id}")
    public ArtistType getArtistTypeByID(@PathVariable(value = "id") Integer artistTypeID) {
        return artistTypeRepository.findById(artistTypeID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistType", "id", artistTypeID));
    }

    @PostMapping("/artisttypes")
    public ArtistType createArtistType(@Valid ArtistType artistType) {
        return artistTypeRepository.save(artistType);
    }

    @PutMapping("/artisttypes/{id}")
    public ArtistType updateArtistType(@PathVariable(value = "id") Integer artistTypeID,
                                           @Valid ArtistType modified) {

        ArtistType artistType = artistTypeRepository.findById(artistTypeID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistType", "id", artistTypeID));

        artistType.setName(modified.getName());

        ArtistType updatedArtistType = artistTypeRepository.save(artistType);
        return updatedArtistType;
    }

    @DeleteMapping("/artisttypes/{id}")
    public ResponseEntity<?> deleteArtistType(@PathVariable(value = "id") Integer artistTypeID) {
        ArtistType artistType = artistTypeRepository.findById(artistTypeID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistType", "id", artistTypeID));

        artistTypeRepository.delete(artistType);

        return ResponseEntity.ok().build();
    }
}