package com.chrisaraneo.mwl.model.extended;

import java.util.Date;
import java.util.Set;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistType;
import com.chrisaraneo.mwl.model.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArtistUndetailed extends Artist {
	
	private static final long serialVersionUID = 4228433394300988781L;

	public ArtistUndetailed(Artist artist) {
		super(artist);
	}

	@Override
	@JsonIgnore
	public Date getBirthDate() {
		// TODO Auto-generated method stub
		return super.getBirthDate();
	}

	@Override
	@JsonIgnore
	public String getCountry() {
		// TODO Auto-generated method stub
		return super.getCountry();
	}

	@Override
	@JsonIgnore
	public String getFirstName() {
		// TODO Auto-generated method stub
		return super.getFirstName();
	}

	@Override
	@JsonIgnore
	public String getLastName() {
		// TODO Auto-generated method stub
		return super.getLastName();
	}

	@Override
	@JsonIgnore
	public ArtistType getArtistType() {
		// TODO Auto-generated method stub
		return super.getArtistType();
	}

	@Override
	@JsonIgnore
	public Set<Song> getSongs() {
		// TODO Auto-generated method stub
		return super.getSongs();
	}
	
}