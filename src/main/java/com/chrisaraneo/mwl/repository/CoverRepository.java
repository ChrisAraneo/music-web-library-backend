package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Cover;

@Repository
public interface CoverRepository extends JpaRepository<Cover, Integer> {

}