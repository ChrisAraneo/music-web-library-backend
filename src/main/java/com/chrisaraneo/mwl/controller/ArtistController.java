package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
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

class AlbumUndetailed {
	private Integer albumID;
	private String title;
	private Integer year;
	public Integer getAlbumID() { return albumID; }
	public void setAlbumID(Integer albumID) { this.albumID = albumID;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public Integer getYear() {return year;}
	public void setYear(Integer year) { this.year = year; }
}

class ArtistDetailed extends Artist {
	
	private static final long serialVersionUID = 5714502075216842135L;
	
	private Set<AlbumUndetailed> albums;
	private Set<ArtistURL> urls;
	
	public void setAlbums(Set<AlbumUndetailed> albums) {
		this.albums = albums;
	}
	
	@JsonProperty("albums")
	public Set<AlbumUndetailed> getAlbums() {
		return albums;
	}
	
	public void setURLs(Set<ArtistURL> urls) {
		this.urls = urls;
	}
	
	@JsonProperty("urls")
	public Set<ArtistURL> getURLs() {
		return urls;
	}
}

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
    	Artist A = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));
    	
    	ArtistDetailed B = new ArtistDetailed();
    	B.setArtistID(A.getArtistID());
    	B.setArtistName(A.getArtistName());
    	B.setArtistType(A.getArtistType());
    	B.setBirthDate(A.getBirthDate());
    	B.setCountry(A.getCountry());
    	B.setFirstName(A.getFirstName());
    	B.setLastName(A.getLastName());
    	
    	Set<AlbumUndetailed> albums = new HashSet<AlbumUndetailed>();
    	for(Song song : A.getSongs()) {
    		Set<SongAlbum> sas = songAlbumRepository.findAlbumsWithSong(song.getSongID());
    		for(SongAlbum sa : sas) {
    			SongAlbumKey id = sa.getId();
    			Album album = id.getAlbum();
    			AlbumUndetailed au = new AlbumUndetailed();
    			au.setAlbumID(album.getAlbumID());
    			au.setTitle(album.getTitle());
    			au.setYear(album.getYear());
    			if(!albums.contains(au)) {
    				albums.add(au);
    			}
    		}
    		
    	}
    	B.setAlbums(albums);
    	
    	Set<ArtistURL> urls = artistURLRepository.findAllByArtist(A.getArtistID());
    	B.setURLs(urls);
    	
        return B;
    }

    @PostMapping("/artists")
    @Secured("ROLE_ADMIN")
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
    public Artist updateArtist(@PathVariable(value = "id") Integer artistID,
                                           @Valid Artist modified) {

        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artist.setArtistName(modified.getArtistName());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @DeleteMapping("/artists/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteArtist(@PathVariable(value = "id") Integer artistID) {
        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistID));

        artistRepository.delete(artist);

        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/artists/{artistID}/{songID}")
    @Secured("ROLE_ADMIN")
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