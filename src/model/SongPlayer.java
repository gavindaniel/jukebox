package model;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SongPlayer {
	
	private Song song;
	private Media songMedia;
	private static MediaPlayer mediaPlayer;
	
	public SongPlayer(Song song) {			
		this.song = song;
		this.songMedia = new Media(this.song.getPlayableSource());
		SongPlayer.mediaPlayer = new MediaPlayer(songMedia);
	}
	
	public MediaPlayer getMediaPlayer() {
		return SongPlayer.mediaPlayer;
	}
	
	public void playSong() {
		SongPlayer.mediaPlayer.play();
	}
}
