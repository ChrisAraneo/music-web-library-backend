package com.chrisaraneo.mwl.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.SongPlaylist;

@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylistKey> {
	@Query("SELECT s FROM SongPlaylist s WHERE playlist_id = ?1")
	Set<SongPlaylist> findAllSongsInPlaylist(Integer playlistID);
	
	@Query("SELECT s FROM SongPlaylist s WHERE track_number = ?1 AND playlist_id = ?2")
	SongPlaylist findSongByTrackInPlaylist(Integer track, Integer playlistID);
}