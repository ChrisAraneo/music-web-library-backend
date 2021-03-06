package com.chrisaraneo.mwl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.Cover;
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.Review;
import com.chrisaraneo.mwl.model.RoleName;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.SongPlaylist;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.model.extended.AlbumWithArtists;
import com.chrisaraneo.mwl.model.extended.AlbumWithSongs;
import com.chrisaraneo.mwl.model.extended.ArtistUndetailed;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.SongAlbumRepository;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.security.CurrentUser;
import com.chrisaraneo.mwl.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


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
    	
    	Set<SongAlbum> songs = songAlbumRepository.findAllSongsInAlbum(albumID);
    	AlbumWithSongs B = new AlbumWithSongs(A, songs);
    	
        return B;
    }

    @PostMapping("/albums")
    @Secured("ROLE_ADMIN")
    public Album createAlbum(@Valid @RequestBody Album album) {
        return albumRepository.save(album);
    }
    
    @PutMapping("/albums/{id}")
    @Secured("ROLE_ADMIN")
    public Album updateAlbum(@PathVariable(value = "id") Integer albumID,
                             @Valid @RequestBody Album modified) {

        Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        album.setTitle(modified.getTitle());
        album.setYear(modified.getYear());

        return albumRepository.save(album);
    }
    
    private void removeAllSongsFromAlbum(Album album) {
    	Integer albumID = album.getAlbumID();
    	Set<SongAlbum> sa = songAlbumRepository.findAllSongsInAlbum(albumID);
    	for(SongAlbum song : sa) {
    		Integer songID = song.getSong().getSongID();
    		Integer track = song.getId().getTrackNumber();
    		SongAlbumKey id = new SongAlbumKey(track, album);
    		songAlbumRepository.deleteById(id);
    	}
    	songAlbumRepository.flush();
    	albumRepository.flush();
    }
    
    @DeleteMapping("/albums/{id}")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmptyJson> deleteAlbum(@PathVariable(value = "id") Integer albumID) {
    	
    	Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	removeAllSongsFromAlbum(album);
    	albumRepository.delete(album);
    	albumRepository.flush();
    	return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    }
    
}