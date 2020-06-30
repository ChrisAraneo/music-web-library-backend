package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Review;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.ReviewRepository;
import com.chrisaraneo.mwl.repository.UserRepository;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;
    
    @Autowired
    AlbumRepository albumRepository;
    
    @Autowired
    UserRepository userRepository;

    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
    
    @GetMapping("/reviews/{id}")
    public Review getReviewByID(@PathVariable(value = "id") Integer reviewID) {
        return reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
    }
    
//    @PostMapping("/reviews")
//    public Review createReview(@Valid Review review,
//    		@RequestParam("album") Integer albumID,
//    		@RequestParam("user") Integer userID) throws ResourceNotFoundException {
//    	
//    	Album album = albumRepository.findById(albumID)
//    		.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
//    	
//    	User user = userRepository.findById(userID)
//        		.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
//    	
//    	Set<Review> reviews = album.getReviews();
//    	reviews.add(review);
//    	album.setReviews(reviews);
//    	
//    	review.setAlbum(album);
//    	review.setUser(user);
//    	
//    	albumRepository.save(album);
//        return reviewRepository.save(review);
//    }

    @PutMapping("/reviews/{id}")
    @Secured("ROLE_ADMIN")
    public Review updateReview(@PathVariable(value = "id") Integer reviewID,
                                           @Valid @ModelAttribute Review modified) {

        Review review = reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));

        review.setTitle(modified.getTitle());
        review.setContent(modified.getContent());

        return reviewRepository.save(review);
    }

    @DeleteMapping("/reviews/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") Integer reviewID) {
        Review review = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));

        reviewRepository.delete(review);

        return ResponseEntity.ok().build();
    }
}