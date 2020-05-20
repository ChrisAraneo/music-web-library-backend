package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="artisttypes")
@NamedQuery(name="ArtistType.findAll", query="SELECT a FROM ArtistType a")
public class ArtistType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="artist_type_id", unique=true, nullable=false)
	private Integer artistTypeID;

	@Column(nullable=false, length=255)
	@NotBlank
	private String name;

	public ArtistType() {}

	public Integer getArtistTypeID() {
		return this.artistTypeID;
	}

	public void setArtistTypeID(Integer artistTypeID) {
		this.artistTypeID = artistTypeID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}