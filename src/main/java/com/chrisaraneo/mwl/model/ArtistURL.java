package com.chrisaraneo.mwl.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArtistURL {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artistID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Artist artist;
	
	@NotBlank
	private String url;
}
