package com.chrisaraneo.mwl.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.chrisaraneo.mwl.model.Artist;

@Embeddable
public class ArtistURLKey implements Serializable {
	private static final long serialVersionUID = -5338902479381796058L;

	@JoinColumn(name = "artistID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Artist artist;
	
	@NotBlank
	private String url;

	
    public ArtistURLKey(Artist artist, String url) {
        this.artist = artist;
        this.url = url;
    }

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistURLKey that = (ArtistURLKey) o;

        if (!artist.getArtistID().equals(that.getArtist().getArtistID())) {
        	return false;
        }
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
    	return Objects.hash(artist.getArtistID(), url);
    }
}