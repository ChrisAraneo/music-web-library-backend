package com.chrisaraneo.mwl.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chrisaraneo.mwl.keys.SongPlaylistKey;
import com.chrisaraneo.mwl.model.extended.SongUndetailed;

import java.io.Serializable;

@Entity
@Table(name = "songs_playlist")
public class SongPlaylist implements Serializable {
	
	private static final long serialVersionUID = -8180912412011572953L;

	@EmbeddedId
    private SongPlaylistKey id;

    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="song_id")
	private Song song;

    public SongPlaylist() { }

    public SongPlaylist(SongPlaylistKey id, Song song) {
        this.id = id;
        this.song = song;
    }
    
    public SongPlaylist(SongPlaylist songPlaylist) {
    	this.setId(songPlaylist.getId());
    	this.setSong(songPlaylist.getSong());
    }

	public SongPlaylistKey getId() {
		return id;
	}

	public void setId(SongPlaylistKey id) {
		this.id = id;
	}

	public Song getSong() {
		return new SongUndetailed(song);
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
}