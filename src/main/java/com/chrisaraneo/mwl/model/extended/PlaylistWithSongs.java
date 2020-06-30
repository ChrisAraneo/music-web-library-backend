package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Playlist;
import com.chrisaraneo.mwl.model.SongAlbum;
import com.chrisaraneo.mwl.model.SongPlaylist;

public class PlaylistWithSongs extends Playlist {
	
	private static final long serialVersionUID = 8608268471424455861L;
	
	private Set<SongPlaylist> songs;
	
	public PlaylistWithSongs(Playlist playlist, Set<SongPlaylist> songs) {
		super(playlist);
		this.songs = songs;
	}
	
	public void setSongs(Set<SongPlaylist> songs) {
		this.songs = songs;
	}
	
	public Set<SongPlaylist> getSongs() {
		return songs;
	}
}