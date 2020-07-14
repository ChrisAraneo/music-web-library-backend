package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Album;
import com.chrisaraneo.mwl.model.Cover;
import com.chrisaraneo.mwl.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AlbumUndetailed extends Album {

	private static final long serialVersionUID = 703298006676795761L;

	public AlbumUndetailed(Album album) {
		super(album);
	}
	
	@Override
	@JsonIgnore
	public Cover getCover() {
		return super.getCover();
	}

	@Override
	@JsonIgnore
	public Set<Review> getReviews() {
		return super.getReviews();
	}
	
}
