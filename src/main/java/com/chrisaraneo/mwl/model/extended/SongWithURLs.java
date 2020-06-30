package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Song;
import com.chrisaraneo.mwl.model.SongURL;

public class SongWithURLs extends Song {
	
	private static final long serialVersionUID = -9208154876526531998L;
	
	private Set<SongURL> songURLs;

	public SongWithURLs(Song song, Set<SongURL> urls) {		
		super(song);
		this.songURLs = urls;
	}
	
	public Set<SongURL> getSongURLs() {
		return songURLs;
	}
	
	public void setSongURLs(Set<SongURL> songURLs) {
		this.songURLs = songURLs;
	}
	
}