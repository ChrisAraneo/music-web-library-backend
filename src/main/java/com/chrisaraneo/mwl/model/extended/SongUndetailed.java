package com.chrisaraneo.mwl.model.extended;

import java.util.HashSet;
import java.util.Set;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SongUndetailed extends Song {
	
	private static final long serialVersionUID = 3603771682303916190L;
	
	public SongUndetailed(Song song) { 
		super();
		this.setArtists(song.getArtists());
		this.setTitle(song.getTitle());
		this.setSongID(song.getSongID());
	}
	
	@Override
	@JsonIgnore
	public Integer getBpm() {
		return super.getBpm();
	}
	
	@Override
	@JsonIgnore
	public String getComment() {
		return super.getComment();
	}
	
	@Override
	@JsonIgnore
	public String getGenre() {
		return super.getGenre();
	}
	
	@Override
	@JsonIgnore
	public String getLanguage() {
		return super.getLanguage();
	}
	
	@Override
	@JsonIgnore
	public Integer getLength() {
		return super.getLength();
	}
	
	@Override
	@JsonIgnore
	public String getMainKey() {
		return super.getMainKey();
	}
	
	@Override
	@JsonIgnore
	public String getPublisher() {
		return super.getPublisher();
	}
	
	@Override
	@JsonIgnore
	public String getTerms() {
		return super.getTerms();
	}
	
	@Override
	@JsonIgnore
	public String getWebsite() {
		return super.getWebsite();
	}
	
	@Override
	@JsonIgnore
	public Integer getYear() {
		return super.getYear();
	}
	
	@Override
	public Set<Artist> getArtists() {
		Set<Artist> aus = new HashSet<Artist>();
		for(Artist artist : super.getArtists()) {
			aus.add(new ArtistUndetailed(artist));
		}
		return aus;
	}
}