package com.chrisaraneo.mwl.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.chrisaraneo.mwl.model.Album;

@Embeddable
public class SongsAlbumKey implements Serializable {
	private static final long serialVersionUID = 30063342056840743L;

	@ManyToOne
	@JoinColumn(name = "albumID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Album album;
	
	@NotBlank
	private Long track;

	
    public SongsAlbumKey(Album album, Long track) {
        this.album = album;
        this.track = track;
    }

    public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Long getTrack() {
		return track;
	}

	public void setTrack(Long track) {
		this.track = track;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongsAlbumKey that = (SongsAlbumKey) o;

        if (!album.getAlbumID().equals(that.getAlbum().getAlbumID())) {
        	return false;
        }
        return track.equals(that.track);
    }

    @Override
    public int hashCode() {
    	return Objects.hash(album.getAlbumID(), track);
    }
}