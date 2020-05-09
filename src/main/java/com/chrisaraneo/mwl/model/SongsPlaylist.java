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

import com.chrisaraneo.mwl.keys.SongsPlaylistKey;

@Entity
@Table(name = "songs_playlists")
@EntityListeners(AuditingEntityListener.class)

public class SongsPlaylist {
	
	@EmbeddedId
    private SongsPlaylistKey songsPlaylistID; // key: playlist + track
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "songID", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@NotNull
    private Song song;

	
	public SongsPlaylist(SongsPlaylistKey ID, Song song) {
		this.songsPlaylistID = ID;
		this.song = song;
	}
	
	public Playlist getPlaylist() {
		return songsPlaylistID.getPlaylist();
	}
	
	public Long getTrack() {
		return songsPlaylistID.getTrack();
	}
	
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
}
