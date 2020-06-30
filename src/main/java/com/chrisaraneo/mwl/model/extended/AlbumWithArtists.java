package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Cover;
import com.chrisaraneo.mwl.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumWithArtists extends Album {
	
	private static final long serialVersionUID = 5714502075216842135L;
	
	private Set<ArtistUndetailed> artists;
	
	public AlbumWithArtists(Album album, Set<ArtistUndetailed> artists) {
		super(album);
		this.artists = artists;
	}
	
	public void setArtist(Set<ArtistUndetailed> artists) {
		this.artists = artists;
	}
	
	@JsonProperty("artists")
	public Set<ArtistUndetailed> getArtist() {
		return artists;
	}
	
	@JsonIgnore
	@Override
	public Cover getCover() {
		return super.getCover();
	}
	
	@JsonIgnore
	@Override
	public Set<Review> getReviews() {
		return super.getReviews();
	}

}