package com.chrisaraneo.mwl.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chrisaraneo.mwl.keys.SongAlbumKey;
import com.chrisaraneo.mwl.model.extended.ArtistUndetailed;
import com.chrisaraneo.mwl.model.extended.SongUndetailed;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "songs_album")
public class SongAlbum implements Serializable {
	
	private static final long serialVersionUID = -8180912412011572953L;

	@EmbeddedId
    private SongAlbumKey id;

    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="song_id")
	private Song song;
    
    
    public SongAlbum() { }

    public SongAlbum(SongAlbumKey id, Song song) {
        this.id = id;
        this.song = song;
    }
    
    public SongAlbum(SongAlbum songAlbum) {
    	this.setId(songAlbum.getId());
    	this.setSong(songAlbum.getSong());
    }

	public SongAlbumKey getId() {
		return id;
	}

	public void setId(SongAlbumKey id) {
		this.id = id;
	}

	public Song getSong() {
		return new SongUndetailed(song);
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
}