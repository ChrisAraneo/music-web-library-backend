package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.extended.AlbumUndetailed;
import com.chrisaraneo.mwl.model.extended.ArtistDetailed;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.ArtistRepository;
import com.chrisaraneo.mwl.repository.ArtistTypeRepository;
import com.chrisaraneo.mwl.repository.ArtistURLRepository;
import com.chrisaraneo.mwl.repository.SongAlbumRepository;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashSet;
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
    
    @Autowired
    SongAlbumRepository songAlbumRepository;
    
    @Autowired
    ArtistURLRepository artistURLRepository;

    
    @GetMapping("/artists")
    public Object getAllArtists() {
        return artistRepository.findAllBy(); 
    }
    
    @GetMapping("/artists/{id}")
    public Artist getArtistById(@PathVariable(value = "id") Integer artistID) {
    	
    	Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));
    	
    	Set<AlbumUndetailed> albums = new HashSet<AlbumUndetailed>();
    	for(Song song : artist.getSongs()) {
    		Set<SongAlbum> sas = songAlbumRepository.findAlbumsWithSong(song.getSongID());
    		for(SongAlbum sa : sas) {
    			SongAlbumKey id = sa.getId();
    			Album album = id.getAlbum();
    			AlbumUndetailed au = new AlbumUndetailed(album);
    			if(!albums.contains(au)) {
    				albums.add(au);
    			}
    		}
    	}
    	
    	Set<ArtistURL> urls = artistURLRepository.findAllByArtist(artist.getArtistID());
    	
        return new ArtistDetailed(artist, albums, urls);
    }

    @PostMapping("/artists")
    @Secured("ROLE_ADMIN")
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return artistRepository.save(artist);
    }
    
    @PostMapping("/artists/{artistID}/{songID}")
    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
    public Artist updateArtist(
    		@PathVariable(value = "id") Integer artistID,
            @Valid @RequestBody Artist modified) {

        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artist.setArtistName(modified.getArtistName());
        artist.setBirthDate(modified.getBirthDate());
        artist.setCountry(modified.getCountry());
        artist.setFirstName(modified.getFirstName());
        artist.setLastName(modified.getLastName());
        artist.setArtistType(modified.getArtistType());

        return artistRepository.save(artist);
    }

    @DeleteMapping("/artists/{id}")
    @Secured("ROLE_ADMIN")
    public Object deleteArtist(@PathVariable(value = "id") Integer artistID) {
        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artistRepository.delete(artist);

        return new EmptyJson();
    }
    
    @DeleteMapping("/artists/{artistID}/{songID}")
    @Secured("ROLE_ADMIN")
    public Object deleteSongFromArtist(
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
    	
    	return new EmptyJson();
    }
    
}