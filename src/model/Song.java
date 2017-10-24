package model;
// Added to allow package model to exist on GitHub

import java.io.File;
import java.time.LocalDate;


/**
 * Holds essential information about a song and provides functionality to
 * manage number of song plays.
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class Song {
	
	private String title;
	private String artist;
	private int length_s;		//In seconds
	private String length;			// String version
	private String filePath;
	private int numTimesPlayed;
	private LocalDate mostRecentPlay;
	
	public Song (String title, String artist, int length_s, String filePath) {
		
		this.title = title;
		this.artist = artist;
		this.length_s = length_s;
		this.length = toMinutes(length_s);
		this.filePath = "songfiles/" + filePath;
		this.numTimesPlayed = 0;
		this.mostRecentPlay = LocalDate.now();
	}
	
	// Getters and Setters
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	//returns Seconds
	public int getSongLength() {
		return this.length_s;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	public int getNumTimesPlayed() {
		return this.numTimesPlayed;
	}
	public LocalDate getMostRecentPlay() {
		return this.mostRecentPlay;
	}
	
	public void setMostRecentPlay(LocalDate date) {
		this.mostRecentPlay = date;
	}
	
	public void setNumTimesPlayed(int n) {
		this.numTimesPlayed = n;
	}
	
	public String toMinutes(int s) {
		
		int minutes = (s / 60);
		int seconds = s % 60;
		String min = "" + minutes;
		String sec = "" + seconds;
		
		if (minutes < 10)
			min = "0" + minutes;
		if (seconds < 10)
			sec = "0" + seconds;
		
		return  min + ":" + sec;
		//return minutes + ":" + seconds;
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