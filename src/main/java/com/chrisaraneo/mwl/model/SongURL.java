package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity@Table(name="songurls")
@NamedQuery(name="SongURL.findAll", query="SELECT s FROM SongURL s")
public class SongURL implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="song_url_id", unique=true, nullable=false)
	private Integer songURLID;

	@Column(nullable=false, length=255)
	@NotBlank
	private String URL;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="song_id", nullable=false)
	@NotNull
	private Song song;

	public SongURL() { }

	public Integer getSongURLID() {
		return this.songURLID;
	}

	public void setSongURLID(Integer songURLID) {
		this.songURLID = songURLID;
	}

	public String getURL() {
		return this.URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public Song getSong() {
		return this.song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

}