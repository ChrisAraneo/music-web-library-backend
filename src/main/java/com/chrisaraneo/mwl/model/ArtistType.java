package com.chrisaraneo.mwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "artist_type")
@EntityListeners(AuditingEntityListener.class)

public class ArtistType {
	@Id
	@Column(name = "artistTypeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistTypeID;
	
	@NotBlank
	private String name;

	
	public Long getArtistTypeID() {
		return artistTypeID;
	}

	public void setArtistTypeID(Long artistTypeID) {
		this.artistTypeID = artistTypeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
