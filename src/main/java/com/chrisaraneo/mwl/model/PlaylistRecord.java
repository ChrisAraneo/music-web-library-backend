package com.chrisaraneo.mwl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="playlist_records")
@NamedQuery(name="PlaylistRecord.findAll", query="SELECT s FROM PlaylistRecord s")
public class PlaylistRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="playlist_record_id", unique=true, nullable=false)
	private Integer playlistRecordID;
	
	@ManyToOne
	@JoinColumn(name="playlist_id", nullable=false)
	@NotNull
	private Playlist playlist;
	
	@ManyToOne
	@JoinColumn(name="song_id", nullable=false)
	@NotNull
	private Song song;
	
	@Column(name="order")
	private Integer order;

	public Integer getPlaylistRecordID() {
		return playlistRecordID;
	}

	public void setPlaylistRecordID(Integer playlistRecordID) {
		this.playlistRecordID = playlistRecordID;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}