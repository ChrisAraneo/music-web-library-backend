package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.SongAlbum;

public class AlbumWithSongs extends Album {
	
	private static final long serialVersionUID = 8608268471424455861L;
	
	private Set<SongAlbum> songs;
	
	public AlbumWithSongs(Album album, Set<SongAlbum> songs) {
		super(album);
		this.songs = songs;
	}
	
	public void setSongs(Set<SongAlbum> songs) {
		this.songs = songs;
	}
	
	public Set<SongAlbum> getSongs() {
		return songs;
	}
}