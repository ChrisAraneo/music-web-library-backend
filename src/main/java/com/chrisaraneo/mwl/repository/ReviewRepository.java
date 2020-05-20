package com.chrisaraneo.mwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisaraneo.mwl.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}