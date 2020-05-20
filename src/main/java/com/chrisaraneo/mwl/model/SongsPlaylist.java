package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="songsplaylist")
@NamedQuery(name="SongsPlaylist.findAll", query="SELECT s FROM SongsPlaylist s")
public class SongsPlaylist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="song_playlist_id", unique=true, nullable=false)
	private Integer songsPlaylistID;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="playlist_id", nullable=false)
	@NotNull
	private Playlist playlist;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="song_id", nullable=false)
	@NotNull
	private Song song;
	
	@Column(name="order")
	private Integer order;
	
	public SongsPlaylist() {}

	public Integer getSongsPlaylistID() {
		return this.songsPlaylistID;
	}

	public void setSongsPlaylistID(Integer ID) {
		this.songsPlaylistID = ID;
	}

	public Playlist getPlaylist() {
		return this.playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Song getSong() {
		return this.song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
	public Integer getOrder() {
		return this.order;
	}
	
	public void setOrder(Integer order) {
		this.order = order;
	}

}