package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.Artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

interface ArtistList {
    String getArtistID();
    String getArtistName();
}

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	List<ArtistList> findAllBy(); 
}