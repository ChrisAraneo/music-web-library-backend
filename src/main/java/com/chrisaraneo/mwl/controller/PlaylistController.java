package com.chrisaraneo.mwl.controller;

import java.util.List;
import java.util.Set;

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
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongsPlaylist;
import com.chrisaraneo.mwl.repository.PlaylistRepository;
import com.chrisaraneo.mwl.repository.SongRepository;
import com.chrisaraneo.mwl.repository.SongsPlaylistRepository;

@RestController
@RequestMapping("/api")
public class PlaylistController {

    @Autowired
    PlaylistRepository playlistRepository;
    
    @Autowired
    SongRepository songRepository;
    
    @Autowired
    SongsPlaylistRepository songsPlaylistRepository;

    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
    
    @GetMapping("/playlists/{id}")
    public Playlist getPlaylistById(@PathVariable(value = "id") Integer playlistID) {
        return playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
    }

    @PostMapping("/playlists")
    public Playlist createPlaylist(@Valid Playlist playlist) {
        return playlistRepository.save(playlist);
    }
    
    @PostMapping("/playlists/{playlistID}/{songID}")
    public Playlist addSongToPlaylist(
    		@PathVariable(value = "playlistID") Integer playlistID,
    		@PathVariable(value = "songID") Integer songID) {
    	
    	Playlist playlist = playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));
    	
    	Song song = songRepository.findById(songID)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songID));
    	
    	SongsPlaylist record = new SongsPlaylist();
    	record.setPlaylist(playlist);
    	record.setSong(song);
    	record.setOrder(new Integer(0));
    	
    	Set<SongsPlaylist> set = playlist.getSongsPlaylists();
    	set.add(record);
    	playlist.setSongsPlaylists(set);
    	
    	songsPlaylistRepository.save(record);
        return playlistRepository.save(playlist);
    }

    @PutMapping("/playlist/{id}")
    public Playlist updatePlaylist(@PathVariable(value = "id") Integer playlistID,
                                           @Valid Playlist modified) {

        Playlist playlist = playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));

        playlist.setTitle(modified.getTitle());

        return playlistRepository.save(playlist);
    }
    
    @PutMapping("/songsplaylist/{id}")
    public SongsPlaylist updateSongPlaylist(
    		@PathVariable(value = "id") Integer songsPlaylistID,
    		@RequestParam(name = "order") Integer order) {
        
        SongsPlaylist item = songsPlaylistRepository.findById(songsPlaylistID)
                .orElseThrow(() -> new ResourceNotFoundException("SongsPlaylist", "id", songsPlaylistID));

        item.setOrder(order);
        
        return songsPlaylistRepository.save(item);
    }

    @DeleteMapping("/playlist/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable(value = "id") Integer playlistID) {
        Playlist playlist = playlistRepository.findById(playlistID)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "id", playlistID));

        playlistRepository.delete(playlist);

        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/songsplaylist/{id}")
    public ResponseEntity<?> deleteSongInPlaylist(@PathVariable(value = "id") Integer ID) {
        SongsPlaylist item = songsPlaylistRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("SongPlaylist", "id", ID));

        songsPlaylistRepository.delete(item);

        return ResponseEntity.ok().build();
    }
}