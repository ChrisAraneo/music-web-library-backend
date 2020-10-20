package com.chrisaraneo.mwl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
	
	@Query("SELECT p FROM Playlist p WHERE user_id = ?1")
	List<Playlist> findAllUserPlaylists(Long userID);
	
	@Query("SELECT p FROM Playlist p WHERE user_id = ?1 AND playlist_id = ?2")
	Playlist findUserPlaylist(Long userID, Integer playlistID);
	
}