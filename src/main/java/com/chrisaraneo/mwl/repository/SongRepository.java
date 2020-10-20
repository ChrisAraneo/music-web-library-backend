package com.chrisaraneo.mwl.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.Song;

interface SongList {
    String getSongID();
    String getTitle();
    Set<Artist> getArtists();
}

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
	
	List<SongList> findAllBy();
	
}