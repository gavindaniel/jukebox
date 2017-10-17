package model;
// Added to allow package model to exist on GitHub

import java.io.File;

/**
 * Holds essential information about a song and provides functionality to
 * manage number of song plays.
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class Song {
	
	private String title;
	private String artist;
	private int length;		//In seconds
	private String filePath;
	private int numTimesPlayed;
	
	public Song (String title, String artist, int length, String filePath) {
		
		this.title = title;
		this.artist = artist;
		this.length = length;
		this.filePath = "songfiles/" + filePath;
		this.numTimesPlayed = 0;
	}
	
	// Getters and Setters
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public int getSongLength() {
		return this.length;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	public int getNumTimesPlayed() {
		return this.numTimesPlayed;
	}
	
	public void setNumTimesPlayed(int n) {
		this.numTimesPlayed = n;
	}
	
	//Adds one to number of song plays
	public void incrementNumPlays() {
		this.numTimesPlayed++;
	}
	//Resets number of song plays to zero.
	public void resetNumPlays() {
		this.numTimesPlayed = 0;
	}
	
	// Returns a string used to initialize a Media object to play song
	public String getPlayableSource() {
		 return (new File(getFilePath())).toURI().toString();
	}
}