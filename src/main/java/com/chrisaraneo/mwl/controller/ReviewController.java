package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.BadRequestException;
import com.chrisaraneo.mwl.exception.ForbiddenException;
import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Review;
import com.chrisaraneo.mwl.model.RoleName;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.ReviewRepository;
import com.chrisaraneo.mwl.repository.UserRepository;
import com.chrisaraneo.mwl.security.CurrentUser;
import com.chrisaraneo.mwl.security.RequiresCaptcha;
import com.chrisaraneo.mwl.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
        Review review = reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
        
        return review;
    }
    
    @PostMapping("/reviews/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    @RequiresCaptcha
    public Review createReview(
    		@Valid @RequestBody Review review,
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer albumID) throws ResourceNotFoundException {
    	
    	Album album = albumRepository.findById(albumID)
    		.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	String username = currentUser.getUsername();
    	String email = currentUser.getEmail();
    	User user = userRepository.findByUsernameOrEmail(username, email)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "usernameOrEmail", username+" "+email));
    	
    	album.addReview(review);
    	
    	review.setAlbum(album);
    	review.setUser(user);
    	
    	albumRepository.save(album);
        return reviewRepository.save(review);
    }

    @PutMapping("/reviews/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    @RequiresCaptcha
    public Review updateReview(
    		@Valid @RequestBody Review modified,
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer reviewID) {

    	Review review = reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
    	
    	Integer albumID = modified.getAlbum().getAlbumID();
    	Album album = albumRepository.findById(albumID)
        		.orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));

        String email = currentUser.getEmail();
    	String username = currentUser.getUsername();
    	
    	Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    	if(user.isPresent()) {
    		if(review.getUser().getID() == user.get().getID()) {
    			review.setUser(user.get());
	    		review.setAlbum(album);
	    		review.setContent(modified.getContent());
	    		review.setTitle(modified.getTitle());
	    		return reviewRepository.save(review);
    		} else {
    			throw new ForbiddenException("This user is not the author of this review");
    		}
    	} else {
    		throw new BadRequestException("User info not provided in request");
    	}
    }
    
    @DeleteMapping("/reviews/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmptyJson> deleteReview(
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer reviewID) {
    	
    	Review review = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
    	
    	Integer albumID = review.getAlbum().getAlbumID();
    	Album album = albumRepository.findById(albumID)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumID));
    	
    	if(currentUser.hasRole((RoleName.ROLE_ADMIN).toString())) {
    		album.removeReview(review);
    		review.removeAlbum(album);
    		reviewRepository.deleteById(reviewID);
    		return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    	} else {
    		String email = currentUser.getEmail();
    		String username = currentUser.getUsername();
    		Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    		if(user.isPresent()) {
    			User u = review.getUser();
    			if(u != null) {
    				Optional<User> creator = userRepository.findByUsernameOrEmail(u.getUsername(), u.getEmail());
    				if(creator.isPresent()) {
    					if(creator.get().equals(user.get())) {
    						album.removeReview(review);
    			    		review.removeAlbum(album);
    			    		reviewRepository.deleteById(reviewID);
    			    		return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.OK);
    					} else {
    						throw new ForbiddenException("This user is not an owner of this review");
    					}
    				} else {
    					throw new ForbiddenException("This review is corrupted and doesn't contain information about author.");
    				}
    			}
    		} else {
    			throw new BadRequestException("User information is not provided in request");
    		}
    	}
    	
    	return new ResponseEntity<EmptyJson>(new EmptyJson(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}