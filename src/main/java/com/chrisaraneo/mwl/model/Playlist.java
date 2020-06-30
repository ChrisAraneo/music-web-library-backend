package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chrisaraneo.mwl.model.extended.UserUndetailed;

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
//	@NotNull
	private User user;
	
//	@OneToMany(mappedBy="id")
//	private Set<SongPlaylist> songsInPlaylist;
	

	public Playlist() { }
	
	public Playlist(Playlist playlist) {
		this.setPlaylistID(playlist.getPlaylistID());
		this.setTitle(playlist.getTitle());
		this.setUser(playlist.getUser());
	}

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
		UserUndetailed uu = new UserUndetailed(user);
		return uu;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public Set<SongPlaylist> getSongs() {
//		return this.songsInPlaylist;
//	}
//
//	public void setSongs(Set<SongPlaylist> songs) {
//		this.songsInPlaylist = songs;
//	}

}