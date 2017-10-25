package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * File: SongPlayer.java
 * 
 * Holds information related to the MediaPlayer and songs to be played
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class SongPlayer {

	private Song song;
	private Media songMedia;
	private static MediaPlayer mediaPlayer;

	// SongPlayer constructor
	public SongPlayer(Song song) {
		this.song = song;
		this.songMedia = new Media(this.song.getPlayableSource());
		SongPlayer.mediaPlayer = new MediaPlayer(songMedia);
	}

	// gets MediaPlayer
	public MediaPlayer getMediaPlayer() {
		return SongPlayer.mediaPlayer;
	}

	// plays song in MediaPlayer
	public void playSong() {
		SongPlayer.mediaPlayer.play();
	}
}
