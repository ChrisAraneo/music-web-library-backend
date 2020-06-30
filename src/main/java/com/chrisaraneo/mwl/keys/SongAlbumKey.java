package com.chrisaraneo.mwl.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.chrisaraneo.mwl.model.Album;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongAlbumKey implements Serializable {
	
	private static final long serialVersionUID = 6511556497977098375L;

	@Column(name = "track_number")
    private Integer trackNumber;
	
	@JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="album_id")
	private Album album;

    public SongAlbumKey() {
    }

    public SongAlbumKey(Integer trackNumber, Album album) {
        this.trackNumber = trackNumber;
        this.album = album;
    }

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongAlbumKey that = (SongAlbumKey) o;
        return trackNumber.equals(that.trackNumber) &&
                (album.getAlbumID() == that.getAlbum().getAlbumID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackNumber, album);
    }
	
}