package com.chrisaraneo.mwl.controller;

import com.chrisaraneo.mwl.exception.ResourceNotFoundException;
import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Review;
import com.chrisaraneo.mwl.model.RoleName;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.repository.AlbumRepository;
import com.chrisaraneo.mwl.repository.ReviewRepository;
import com.chrisaraneo.mwl.repository.UserRepository;
import com.chrisaraneo.mwl.security.CurrentUser;
import com.chrisaraneo.mwl.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
        return reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
    }
    
//    @PostMapping("/playlists")
//    
//    public Playlist createPlaylist(
//    		@Valid Playlist playlist,
//    		@CurrentUser UserPrincipal currentUser) {
//    	
//    	String title = playlist.getTitle();
//    	if(title != null && !title.isEmpty()) {
//    		Optional<User> user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getPassword());
//	    	if(user.isPresent()) {
//	    		playlist.setUser(user.get());
//	    		return playlistRepository.save(playlist);
//	    	}
//    	}
//    	
//        return null;
//    }
    
    @PostMapping("/reviews")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public Review createReview(
    		@Valid Review review,
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam("album") Integer albumID,
    		@RequestParam("user") Integer userID) throws ResourceNotFoundException {
    	
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
    public Review updateReview(
    		@CurrentUser UserPrincipal currentUser,
    		@PathVariable(value = "id") Integer reviewID,
    		@Valid @ModelAttribute Review modified) {

    	Review review = reviewRepository.findById(reviewID)
        		.orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));

        String email = currentUser.getEmail();
    	String username = currentUser.getUsername();
    	
    	Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
    	if(user.isPresent()) {
    		if(review.getUser().getID() == user.get().getID()) {
    			review.setUser(user.get());
	    		review.setAlbum(modified.getAlbum());
	    		review.setContent(modified.getContent());
	    		review.setReviewID(modified.getReviewID());
	    		review.setTitle(modified.getTitle());
	    		return reviewRepository.save(review);
    		}
    	}
    	
    	return null;
    }

//    @DeleteMapping("/reviews/{id}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") Integer reviewID) {
//        Review review = reviewRepository.findById(reviewID)
//                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewID));
//
//        reviewRepository.delete(review);
//
//        return ResponseEntity.ok().build();
//    }
    
    @DeleteMapping("/reviews/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteReview(
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
    		return ResponseEntity.ok().build();
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
    			    		return ResponseEntity.ok().build();
    					} else {
    						System.out.println("NOT EQUALS");
    					}
    				}
    			}
    		}
    	}
    	
    	// TODO
    	return ResponseEntity.badRequest().build();
    }
}