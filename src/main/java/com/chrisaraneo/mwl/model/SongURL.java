package com.chrisaraneo.mwl.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SongURL {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "songID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Song song;
	
	@NotBlank
	private String url;

	
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
