package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.ArtistURL;
import com.chrisaraneo.mwl.model.SongAlbum;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistURLRepository extends JpaRepository<ArtistURL, Integer> {
	@Query("SELECT s FROM ArtistURL s WHERE artist_id = ?1")
	Set<ArtistURL> findAllByArtist(Integer artistID);
}