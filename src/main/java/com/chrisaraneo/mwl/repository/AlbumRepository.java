package com.chrisaraneo.mwl.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.SongAlbum;



@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}