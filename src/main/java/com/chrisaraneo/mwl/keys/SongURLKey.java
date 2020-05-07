package com.chrisaraneo.mwl.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.chrisaraneo.mwl.model.Song;

@Embeddable
public class SongURLKey implements Serializable {
	private static final long serialVersionUID = -8700605298081829849L;

	@JoinColumn(name = "songID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Song song;
	
	@NotBlank
	private String url;

	
    public SongURLKey(Song song, String url) {
        this.song = song;
        this.url = url;
    }

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
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

        SongURLKey that = (SongURLKey) o;

        if (!song.getSongID().equals(that.getSong().getSongID())) {
        	return false;
        }
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
    	return Objects.hash(song.getSongID(), url);
    }
}