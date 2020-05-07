package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artists")
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @PostMapping("/artists")
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @GetMapping("/artists/{id}")
    public Artist getArtistById(@PathVariable(value = "id") Long artistID) {
        return artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));
    }

    @PutMapping("/artists/{id}")
    public Artist updateArtist(@PathVariable(value = "id") Long artistID,
                                           @Valid @RequestBody Artist modified) {

        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artist.setArtistName(modified.getArtistName());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable(value = "id") Long artistID) {
        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artistRepository.delete(artist);

        return ResponseEntity.ok().build();
    }
}
