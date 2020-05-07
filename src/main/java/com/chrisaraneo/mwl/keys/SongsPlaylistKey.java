package com.chrisaraneo.mwl.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.chrisaraneo.mwl.model.Playlist;

@Embeddable
public class SongsPlaylistKey implements Serializable {
	private static final long serialVersionUID = -5338902479381796058L;

	@JoinColumn(name = "playlistID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Playlist playlist;
	
	@NotBlank
	private Long track;

	
    public SongsPlaylistKey(Playlist playlist, Long track) {
        this.playlist = playlist;
        this.track = track;
    }

    public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
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

        SongsPlaylistKey that = (SongsPlaylistKey) o;

        if (!playlist.getPlaylistID().equals(that.getPlaylist().getPlaylistID())) {
        	return false;
        }
        return track.equals(that.track);
    }

    @Override
    public int hashCode() {
    	return Objects.hash(playlist.getPlaylistID(), track);
    }
}