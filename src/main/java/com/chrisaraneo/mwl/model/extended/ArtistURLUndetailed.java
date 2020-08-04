package com.chrisaraneo.mwl.model.extended;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArtistURLUndetailed extends ArtistURL {

	private static final long serialVersionUID = 3956618838466595042L;

	public ArtistURLUndetailed(ArtistURL url) {
		super(url);
	}
	
	@Override
	@JsonIgnore
	public Artist getArtist() {
		return super.getArtist();
	}

}
