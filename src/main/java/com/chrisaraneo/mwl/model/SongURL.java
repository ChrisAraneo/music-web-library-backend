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
import com.chrisaraneo.mwl.keys.SongURLKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "songs_urls")
@EntityListeners(AuditingEntityListener.class)

public class SongURL {
	@EmbeddedId
    private SongURLKey songURLID;

	public SongURL(SongURLKey ID) {
		this.songURLID = ID;
	}
	
	public Song getSong() {
		return songURLID.getSong();
	}

//	public void setSong(Song song) {
//		this.song = song;
//	}

	public String getUrl() {
		return songURLID.getUrl();
	}

//	public void setUrl(String url) {
//		this.url = url;
//	}
}
