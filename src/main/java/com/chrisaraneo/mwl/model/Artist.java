package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name="artists")
@NamedQuery(name="Artist.findAll", query="SELECT a FROM Artist a")
public class Artist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="artist_id", unique=true, nullable=false)
	private Integer artistID;

	@Column(name="artist_name", nullable=false, length=255)
	@NotBlank
	private String artistName;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Column(length=255)
	private String country;

	@Column(name="first_name", length=255)
	private String firstName;

	@Column(name="last_name", length=255)
	private String lastName;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="artist_type_id")
	private ArtistType artistType;

	@ManyToMany(mappedBy="artists")
	private Set<Song> songs;

	public Artist() {}

	public Integer getArtistID() {
		return this.artistID;
	}

	public void setArtistID(Integer artistID) {
		this.artistID = artistID;
	}

	public String getArtistName() {
		return this.artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ArtistType getArtistType() {
		return this.artistType;
	}

	public void setArtistType(ArtistType artistType) {
		this.artistType = artistType;
	}

//	public Set<ArtistURL> getArtistURLs() {
//		return this.artistURLs;
//	}
//
//	public void setArtistURLs(Set<ArtistURL> artistURLs) {
//		this.artistURLs = artistURLs;
//	}

//	public ArtistURL addArtistURL(ArtistURL artistURL) {
//		getArtistURLs().add(artistURL);
//		artistURL.setArtist(this);
//
//		return artistURL;
//	}
//
//	public ArtistURL removeArtistURL(ArtistURL artistURL) {
//		getArtistURLs().remove(artistURL);
//		artistURL.setArtist(null);
//
//		return artistURL;
//	}

	public Set<Song> getSongs() {
		return this.songs;
	}

	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}

}