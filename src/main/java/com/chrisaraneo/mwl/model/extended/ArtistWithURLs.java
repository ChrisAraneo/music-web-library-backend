package com.chrisaraneo.mwl.model.extended;

import java.util.HashSet;
import java.util.Set;

import com.chrisaraneo.mwl.model.Artist;
import com.chrisaraneo.mwl.model.ArtistURL;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtistWithURLs extends Artist {

	private static final long serialVersionUID = 5714502075216842135L;

	private Set<ArtistURL> urls;

	public ArtistWithURLs(Artist artist, Set<ArtistURL> urls) {
		super(artist);
		this.urls = urls;
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
