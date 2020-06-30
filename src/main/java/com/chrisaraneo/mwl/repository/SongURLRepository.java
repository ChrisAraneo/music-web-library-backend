package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.SongURL;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongURLRepository extends JpaRepository<SongURL, Integer> {
	@Query("SELECT s FROM SongURL s WHERE song_id = ?1")
	Set<SongURL> findAllBySong(Integer songID);
}