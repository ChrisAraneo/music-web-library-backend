package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.ArtistTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured("ROLE_ADMIN")
    public ArtistType createArtistType(@Valid @RequestBody ArtistType artistType) {
        return artistTypeRepository.save(artistType);
    }

    @PutMapping("/artisttypes/{id}")
    @Secured("ROLE_ADMIN")
    public ArtistType updateArtistType(
    		@PathVariable(value = "id") Integer artistTypeID,
            @Valid @RequestBody ArtistType modified) {

        ArtistType artistType = artistTypeRepository.findById(artistTypeID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistType", "id", artistTypeID));

        artistType.setName(modified.getName());

        return artistTypeRepository.save(artistType);
    }

    @DeleteMapping("/artisttypes/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<EmptyJson> deleteArtistType(@PathVariable(value = "id") Integer artistTypeID) {
        ArtistType artistType = artistTypeRepository.findById(artistTypeID)
                .orElseThrow(() -> new ResourceNotFoundException("ArtistType", "id", artistTypeID));

        artistTypeRepository.delete(artistType);

        return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    }
}