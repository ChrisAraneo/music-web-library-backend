package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.ArtistType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistTypeRepository extends JpaRepository<ArtistType, Integer> {

}