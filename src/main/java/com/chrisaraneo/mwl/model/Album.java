package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="albums")
@NamedQuery(name="Album.findAll", query="SELECT a FROM Album a")
public class Album implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="album_id", unique=true, nullable=false)
	private Integer albumID;

	@Column(nullable=false, length=255)
	@NotBlank
	private String title;

	private int year;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cover_id")
	private Cover cover;

	@OneToMany(mappedBy="album")
	private Set<Review> reviews;

	@ManyToMany
	@JoinTable(
		name="songs_album",
		joinColumns = @JoinColumn(name = "album_id"),
		inverseJoinColumns = @JoinColumn(name = "song_id"))
	private Set<Song> songs = new HashSet<Song>();

	
	public Album() { }

	public Integer getAlbumID() {
		return this.albumID;
	}

	public void setAlbumID(Integer albumID) {
		this.albumID = albumID;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Cover getCover() {
		return this.cover;
	}

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setAlbum(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setAlbum(null);

		return review;
	}

	public Set<Song> getSongs() {
		return this.songs;
	}

	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}

}