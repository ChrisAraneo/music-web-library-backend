package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Set;


@Entity
@Table(name="playlists")
@NamedQuery(name="Playlist.findAll", query="SELECT p FROM Playlist p")
public class Playlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="playlist_id", unique=true, nullable=false)
	private Integer playlistID;

	@Column(nullable=false, length=255)
	@NotBlank
	private String title;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	@NotNull
	private User user;

	@OneToMany(mappedBy="playlist")
	private Set<SongsPlaylist> songsPlaylists;

	public Playlist() { }

	public Integer getPlaylistID() {
		return this.playlistID;
	}

	public void setPlaylistID(Integer playlistID) {
		this.playlistID = playlistID;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<SongsPlaylist> getSongsPlaylists() {
		return this.songsPlaylists;
	}

	public void setSongsPlaylists(Set<SongsPlaylist> songsPlaylists) {
		this.songsPlaylists = songsPlaylists;
	}

	public SongsPlaylist addSongsPlaylist(SongsPlaylist songsPlaylist) {
		getSongsPlaylists().add(songsPlaylist);
		songsPlaylist.setPlaylist(this);

		return songsPlaylist;
	}

	public SongsPlaylist removeSongsPlaylist(SongsPlaylist songsPlaylist) {
		getSongsPlaylists().remove(songsPlaylist);
		songsPlaylist.setPlaylist(null);

		return songsPlaylist;
	}

}