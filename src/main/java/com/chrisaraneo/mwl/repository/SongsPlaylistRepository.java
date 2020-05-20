package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.SongsPlaylist;

@Repository
public interface SongsPlaylistRepository extends JpaRepository<SongsPlaylist, Integer> {

}