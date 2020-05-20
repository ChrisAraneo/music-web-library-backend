package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id", unique=true, nullable=false)
	private Integer userID;

	@Column(nullable=false)
	private Boolean banned;

	@Column(name="CURRENT_CONNECTIONS", nullable=false)
	private BigInteger currentConnections;

	@Column(nullable=false, length=255)
	private String email;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false, length=255)
	private String password;

	@Column(name="reset_code", length=255)
	private String resetCode;

	@Temporal(TemporalType.DATE)
	@Column(name="reset_date")
	private Date resetDate;

	@Column(name="super_user", nullable=false)
	private Boolean superUser;

	@Column(name="TOTAL_CONNECTIONS", nullable=false)
	private BigInteger totalConnections;

	@Column(length=32)
	private String user;

//	@OneToMany(mappedBy="user")
//	private Set<Playlist> playlists;

//	@OneToMany(mappedBy="user")
//	private Set<Review> reviews;

	public User() { }

	public Integer getUserID() {
		return this.userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public BigInteger getCurrentConnections() {
		return this.currentConnections;
	}

	public void setCurrentConnections(BigInteger currentConnections) {
		this.currentConnections = currentConnections;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetCode() {
		return this.resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public Date getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public Boolean getSuperUser() {
		return this.superUser;
	}

	public void setSuperUser(Boolean superUser) {
		this.superUser = superUser;
	}

	public BigInteger getTotalConnections() {
		return this.totalConnections;
	}

	public void setTotalConnections(BigInteger totalConnections) {
		this.totalConnections = totalConnections;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

//	public Set<Playlist> getPlaylists() {
//		return this.playlists;
//	}
//
//	public void setPlaylists(Set<Playlist> playlists) {
//		this.playlists = playlists;
//	}

//	public Playlist addPlaylist(Playlist playlist) {
//		getPlaylists().add(playlist);
//		playlist.setUser(this);
//
//		return playlist;
//	}
//
//	public Playlist removePlaylist(Playlist playlist) {
//		getPlaylists().remove(playlist);
//		playlist.setUser(null);
//
//		return playlist;
//	}
//
//	public Set<Review> getReviews() {
//		return this.reviews;
//	}

//	public void setReviews(Set<Review> reviews) {
//		this.reviews = reviews;
//	}
//
//	public Review addReview(Review review) {
//		getReviews().add(review);
//		review.setUser(this);
//
//		return review;
//	}
//
//	public Review removeReview(Review review) {
//		getReviews().remove(review);
//		review.setUser(null);
//
//		return review;
//	}

}