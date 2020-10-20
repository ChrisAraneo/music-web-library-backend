package com.chrisaraneo.mwl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Album;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
	
	@Query("SELECT p FROM Album p WHERE cover_id = ?1")
	List<Album> findAllAlbumsWithCover(Integer coverID);

}