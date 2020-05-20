package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.repository.ArtistRepository;
import com.chrisaraneo.mwl.repository.ArtistTypeRepository;
import com.chrisaraneo.mwl.repository.SongRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;
    
    @Autowired
    ArtistTypeRepository artistTypeRepository;
    
    @Autowired
    SongRepository songRepository;

    @GetMapping("/artists")
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
    
    @GetMapping("/artists/{id}")
    public Artist getArtistById(@PathVariable(value = "id") Integer artistID) {
        return artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));
    }

    @PostMapping("/artists")
    public Artist createArtist(@Valid Artist artist, @RequestParam("type") Optional<Integer> type) {    	
    	if(type.isPresent()) {
    		Optional<ArtistType> artistType = artistTypeRepository.findById(type.get());
	    	if(artistType.isPresent()) {
	    		artist.setArtistType(artistType.get());
	    	}
    	}

        return artistRepository.save(artist);
    }
    
    @PostMapping("/artists/{artistID}/{songID}")
    public Artist addSongToArtist(
    		@RequestParam("artistID") Integer artistID,
    		@RequestParam("songID") Integer songID) {
    	
    	Artist artist = artistRepository.findById(artistID)
    			.orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

    	Song song = songRepository.findById(songID)
    			.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	Set<Artist> artists = song.getArtists();
    	artists.add(artist);
    	song.setArtists(artists);
    	
    	Set<Song> songs = artist.getSongs();
    	songs.add(song);
    	artist.setSongs(songs);

    	songRepository.save(song);
        return artistRepository.save(artist);
    }

    @PutMapping("/artists/{id}")
    public Artist updateArtist(@PathVariable(value = "id") Integer artistID,
                                           @Valid Artist modified) {

        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artist.setArtistName(modified.getArtistName());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable(value = "id") Integer artistID) {
        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artistRepository.delete(artist);

        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/artists/{artistID}/{songID}")
    public ResponseEntity<?> deleteSongFromArtist(
    		@RequestParam("artistID") Integer artistID,
    		@RequestParam("songID") Integer songID) {
    	
    	Artist artist = artistRepository.findById(artistID)
    			.orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

    	Song song = songRepository.findById(songID)
    			.orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	Set<Artist> artists = song.getArtists();
    	artists.remove(artist);
    	song.setArtists(artists);
    	
    	Set<Song> songs = artist.getSongs();
    	songs.remove(song);
    	artist.setSongs(songs);

    	songRepository.save(song);
    	artistRepository.save(artist);
    	
    	return ResponseEntity.ok().build();
    }
    
}