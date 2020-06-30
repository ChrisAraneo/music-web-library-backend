package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="artisturls")
@NamedQuery(name="ArtistURL.findAll", query="SELECT a FROM ArtistURL a")
public class ArtistURL implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="artist_url_id", unique=true, nullable=false)
	private Integer artistURLID;

	@Column(nullable=false, length=255)
	@NotBlank
	private String URL;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="artist_id", nullable=false)
	@NotNull
	private Artist artist;

	public ArtistURL() { }
	
	public ArtistURL(ArtistURL artistURL) {
		this.setArtistURLID(artistURL.getArtistUrlID());
		this.setArtist(artistURL.getArtist());
		this.setURL(artistURL.getURL());
	}

	public Integer getArtistUrlID() {
		return this.artistURLID;
	}

	public void setArtistURLID(int artistURLID) {
		this.artistURLID = artistURLID;
	}

	public String getURL() {
		return this.URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	@JsonIgnore
	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}