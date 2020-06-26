package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="songs")
@NamedQuery(name="Song.findAll", query="SELECT s FROM Song s")
public class Song implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="song_id", unique=true, nullable=false)
	private Integer songID;

	private Integer bpm;

	@Column(length=255)
	private String comment;

	@Column(length=255)
	private String genre;

	@Column(length=255)
	private String language;

	private Integer length;

	@Column(name="main_key", length=255)
	private String mainKey;

	@Column(length=255)
	private String publisher;

	@Column(length=255)
	private String terms;

	@Column(nullable=false, length=255)
	private String title;

	@Column(length=255)
	private String website;

	private Integer year;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToMany(mappedBy = "songs")
    private Set<Album> albums;

	@ManyToMany
	@JoinTable(
		name="songs_artist",
		joinColumns = @JoinColumn(name = "song_id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private Set<Artist> artists = new HashSet<Artist>();

//	@OneToMany(mappedBy="song")
//	private Set<PlaylistRecord> playlistRecords;

//	@OneToMany(mappedBy="song")
//	private Set<SongURL> songURLs;

	public Song() { }

	public Integer getSongID() {
		return this.songID;
	}

	public void setSongID(Integer songID) {
		this.songID = songID;
	}

	public Integer getBpm() {
		return this.bpm;
	}

	public void setBpm(Integer bpm) {
		this.bpm = bpm;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getMainKey() {
		return this.mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTerms() {
		return this.terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Set<Album> getAlbums() {
		return this.albums;
	}

	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}

	public Set<Artist> getArtists() {
		return this.artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}

//	public Set<SongsPlaylist> getSongsPlaylists() {
//		return this.songsPlaylists;
//	}
//
//	public void setSongsPlaylists(Set<SongsPlaylist> songsPlaylists) {
//		this.songsPlaylists = songsPlaylists;
//	}

//	public SongsPlaylist addSongsPlaylist(SongsPlaylist songsPlaylist) {
//		getSongsPlaylists().add(songsPlaylist);
//		songsPlaylist.setSong(this);
//
//		return songsPlaylist;
//	}
//
//	public SongsPlaylist removeSongsPlaylist(SongsPlaylist songsPlaylist) {
//		getSongsPlaylists().remove(songsPlaylist);
//		songsPlaylist.setSong(null);
//
//		return songsPlaylist;
//	}

//	public Set<SongURL> getSongURLs() {
//		return this.songURLs;
//	}
//
//	public void setSongURLs(Set<SongURL> songURLs) {
//		this.songURLs = songURLs;
//	}

//	public SongURL addSongURL(SongURL songURL) {
//		getSongURLs().add(songURL);
//		songURL.setSong(this);
//
//		return songURL;
//	}
//
//	public SongURL removeSongURL(SongURL songURL) {
//		getSongURLs().remove(songURL);
//		songURL.setSong(null);
//
//		return songURL;
//	}

}