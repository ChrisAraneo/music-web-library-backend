package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

}