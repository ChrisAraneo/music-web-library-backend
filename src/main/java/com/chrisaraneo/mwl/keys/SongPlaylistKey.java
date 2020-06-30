package com.chrisaraneo.mwl.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.chrisaraneo.mwl.model.Playlist;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongPlaylistKey implements Serializable {
	
	private static final long serialVersionUID = 6511556497977098375L;

	@Column(name = "track_number")
    private Integer trackNumber;
	
	@JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="playlist_id")
	private Playlist playlist;

    public SongPlaylistKey() {
    }

    public SongPlaylistKey(Integer trackNumber, Playlist playlist) {
        this.trackNumber = trackNumber;
        this.playlist = playlist;
    }

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongPlaylistKey that = (SongPlaylistKey) o;
        return trackNumber.equals(that.trackNumber) &&
                (playlist.getPlaylistID() == that.getPlaylist().getPlaylistID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackNumber, playlist);
    }
	
}