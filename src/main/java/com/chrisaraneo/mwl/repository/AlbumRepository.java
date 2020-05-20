package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}