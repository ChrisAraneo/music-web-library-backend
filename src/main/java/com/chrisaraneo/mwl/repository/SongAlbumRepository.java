package com.chrisaraneo.mwl.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.model.SongAlbum;

@Repository
public interface SongAlbumRepository extends JpaRepository<SongAlbum, SongAlbumKey> {
	
	@Query("SELECT s FROM SongAlbum s WHERE album_id = ?1")
	Set<SongAlbum> findAllSongsInAlbum(Integer albumID);
	
	@Query("SELECT s FROM SongAlbum s WHERE song_id = ?1")
	Set<SongAlbum> findAlbumsWithSong(Integer songID);
	
}