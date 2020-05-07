package com.chrisaraneo.mwl.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.chrisaraneo.mwl.keys.ArtistURLKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "artist_urls")
@EntityListeners(AuditingEntityListener.class)

public class ArtistURL {
	@EmbeddedId
    private ArtistURLKey artistURLID;

	public ArtistURL(ArtistURLKey ID) {
		this.artistURLID = ID;
	}
	
	public Artist getArtist() {
		return artistURLID.getArtist();
	}

//	public void setArtist(Artist artist) {
//		this.artist = artist;
//	}

	public String getUrl() {
		return artistURLID.getUrl();
	}

//	public void setUrl(String url) {
//		this.url = url;
//	}
}
