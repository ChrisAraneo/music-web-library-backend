package com.chrisaraneo.mwl.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.chrisaraneo.mwl.keys.SongsAlbumKey;

@Entity
@Table(name = "songs_albums")
@EntityListeners(AuditingEntityListener.class)

public class SongsAlbum {
	@EmbeddedId
    private SongsAlbumKey songsAlbumID; // key: album + track
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "songID", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@NotNull
    private Song song;

	public SongsAlbum(SongsAlbumKey ID, Song song) {
		this.songsAlbumID = ID;
		this.song = song;
	}
	
	public Album getAlbum() {
		return this.songsAlbumID.getAlbum();
	}

//	public void setAlbum(Album album) {
//		this.album = album;
//	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Long getTrack() {
		return this.songsAlbumID.getTrack();
	}

//	public void setTrack(Short track) {
//		this.track = track;
//	}
}
