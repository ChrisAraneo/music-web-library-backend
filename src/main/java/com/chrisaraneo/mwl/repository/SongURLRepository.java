package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.SongURL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongURLRepository extends JpaRepository<SongURL, Integer> {

}