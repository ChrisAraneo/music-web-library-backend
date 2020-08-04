package com.chrisaraneo.mwl.model.extended;

import java.util.HashSet;
import java.util.Set;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtistDetailed extends Artist {
	
	private static final long serialVersionUID = 5714502075216842135L;
	
	private Set<AlbumUndetailed> albums;
	private Set<ArtistURL> urls;
	
	public ArtistDetailed(Artist artist, Set<AlbumUndetailed> albums, Set<ArtistURL> urls) {
		super(artist);
		this.albums = albums;
		this.urls = urls;
	}
	
	public void setAlbums(Set<AlbumUndetailed> albums) {
		this.albums = albums;
	}
	
	@JsonProperty("albums")
	public Set<AlbumUndetailed> getAlbums() {
		return albums;
	}
	
	public void setURLs(Set<ArtistURL> urls) {
		this.urls = urls;
	}
	
	@JsonProperty("urls")
	public Set<ArtistURL> getURLs() {
		Set<ArtistURL> uu = new HashSet<ArtistURL>();
		for(ArtistURL url : urls) {
			uu.add(new ArtistURLUndetailed(url));
		}
		return uu;
	}
	
}
