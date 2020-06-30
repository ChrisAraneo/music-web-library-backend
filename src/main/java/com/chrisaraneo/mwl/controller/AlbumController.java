package com.chrisaraneo.mwl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.Cover;
import com.chrisaraneo.mwl.model.Review;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.extended.AlbumWithArtists;
import com.chrisaraneo.mwl.model.extended.AlbumWithSongs;
import com.chrisaraneo.mwl.model.extended.ArtistUndetailed;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.SongAlbumRepository;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//class ArtistUndetailed {
//
//	private Integer artistID;
//	private String artistName;
//	
//	public Integer getArtistID() { return artistID; }
//	public void setArtistID(Integer artistID) { this.artistID = artistID;}
//	public String getArtistName() {return artistName;}
//	public void setArtistName(String artistName) {this.artistName = artistName;}
//	
//}





@RestController
@RequestMapping("/api")
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongAlbumRepository songAlbumRepository;


    @GetMapping("/albums")
    public List<AlbumWithArtists> getAllAlbums() {
    	List<Album> As = albumRepository.findAll();
    	List<AlbumWithArtists> Bs = new ArrayList<AlbumWithArtists>();
    	
    	for(Album A : As) {
        	
        	Set<ArtistUndetailed> artists = new HashSet<ArtistUndetailed>();
    		AlbumWithSongs album = getAlbumById(A.getAlbumID());	
    		for(SongAlbum item : album.getSongs()) {
    			Song song = item.getSong();
    			for(Artist artist : song.getArtists()) {
    				ArtistUndetailed au = new ArtistUndetailed(artist);
    				if(!artists.contains(au)) {
    					artists.add(au);
    				}
    			}
    		}
    		
    		AlbumWithArtists B = new AlbumWithArtists(A, artists);
    		Bs.add(B);
    	}
    	
        return Bs;
    }
    
    @GetMapping("/albums/{id}")
    public AlbumWithSongs getAlbumById(@PathVariable(value = "id") Integer albumID) {
    	Album A = albumRepository.findById(albumID)
    			.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	Set<SongAlbum> songs = songAlbumRepository.findSongsInAlbum(albumID);
    	AlbumWithSongs B = new AlbumWithSongs(A, songs);
    	
        return B;
    }

    @PostMapping("/albums")
    @Secured("ROLE_ADMIN")
    public Album createAlbum(@Valid Album album) {
        return albumRepository.save(album);
    }
    
//    @PostMapping("/albums/{albumID}/{songID}")
//    @Secured("ROLE_ADMIN")
//    public Album addSongToAlbum(
//    		@PathVariable(value = "albumID") Integer albumID,
//    		@PathVariable(value = "songID") Integer songID) {
//    	
//    	Album album = albumRepository.findById(albumID)
//                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
//    	
//    	Song song = songRepository.findById(songID)
//                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
//    	
//    	album.getSongs().add(song);
//    	song.getAlbums().add(album);
//    	
//    	songRepository.save(song);
//        return albumRepository.save(album);
//    }

    @PutMapping("/album/{id}")
    @Secured("ROLE_ADMIN")
    public Album updateAlbum(@PathVariable(value = "id") Integer albumID,
                                           @Valid Album modified) {

        Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        album.setTitle(modified.getTitle());
        album.setYear(modified.getYear());

        return albumRepository.save(album);
    }

    @DeleteMapping("/album/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteAlbum(@PathVariable(value = "id") Integer albumID) {
        Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        albumRepository.delete(album);

        return ResponseEntity.ok().build();
    }
    
//    @DeleteMapping("/album/{albumID}/{songID}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<?> deleteSongFromAlbum(
//    		@PathVariable(value = "albumID") Integer albumID,
//    		@PathVariable(value = "songID") Integer songID) {
//    	
//    	Album album = albumRepository.findById(albumID)
//                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
//    	
//    	Song song = songRepository.findById(songID)
//                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
//    	
//    	album.getSongs().remove(song);
//    	song.getAlbums().remove(album);
//
//        return ResponseEntity.ok().build();
//    }
    
}