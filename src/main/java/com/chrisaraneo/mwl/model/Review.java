package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chrisaraneo.mwl.model.extended.AlbumUndetailed;
import com.chrisaraneo.mwl.model.extended.UserUndetailed;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="reviews")
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="review_id", unique=true, nullable=false)
	private Integer reviewID;

	@Lob
	@Column(nullable=false)
	@NotBlank
	private String content;

	@Column(nullable=false, length=255)
	@NotBlank
	private String title;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="album_id", nullable=false)
	private Album album;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public Review() { }
	
	public Review(Review review) {
		this.setAlbum(review.getAlbum());
		this.setContent(review.getContent());
		this.setReviewID(review.getReviewID());
		this.setTitle(review.getTitle());
		this.setUser(review.getUser());
	}

	public Integer getReviewID() {
		return this.reviewID;
	}

	public void setReviewID(Integer reviewID) {
		this.reviewID = reviewID;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Album getAlbum() {
		AlbumUndetailed album = new AlbumUndetailed(this.album);
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public User getUser() {
		UserUndetailed uu = new UserUndetailed(user);
		return uu;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void removeAlbum(Album album) {
		if(this.album != null) {
			if(this.album.getAlbumID() == album.getAlbumID()) {
				this.album = null;
			}
		}
	}

}