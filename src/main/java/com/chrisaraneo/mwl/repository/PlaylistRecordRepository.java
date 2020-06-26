package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.PlaylistRecord;

@Repository
public interface PlaylistRecordRepository extends JpaRepository<PlaylistRecord, Integer> {

}