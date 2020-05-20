package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.ArtistURL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistURLRepository extends JpaRepository<ArtistURL, Integer> {

}