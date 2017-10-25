package model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
/**
 * File:  Song.java
 * 
 * Holds essential information about a song and provides functionality to
 * manage number of song plays.
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class Song implements Serializable {
	
	private static final long serialVersionUID = 3690602724841979159L;
	private String title;
	private String artist;
	private int length;		//In seconds
	private String durationInMins;	//String version of length with format MM:SS
	private String filePath;
	private int numTimesPlayed;
	private LocalDate mostRecentPlay;
	
	/*
	 *   functionality:	Constructor for Song
	 *   Parameters:		title, artist, length, file path
	 */
	public Song (String title, String artist, int length, String filePath) {
		
		this.title = title;
		this.artist = artist;
		this.length = length;
		this.durationInMins = this.toMinutes();
		this.filePath = "songfiles/" + filePath;
		this.numTimesPlayed = 0;
		this.mostRecentPlay = LocalDate.now();
	}
	
	/*
	 *   functionality:	Getters and Setters of Song Values
	 *   Parameters:		
	 */
	public String getTitle() {
		return this.title;
	}
	public String getArtist() {				return this.artist;		}
	public int getSongLength() {				return this.length;		}
	public String getFilePath() {			return this.filePath;	}
	public int getNumTimesPlayed() {			return this.numTimesPlayed;	}
	public LocalDate getMostRecentPlay() {	return this.mostRecentPlay;	}
	public String getDurationInMins() {		return this.durationInMins;	}
	public String getPlayableSource() {		return (new File(getFilePath())).toURI().toString();	}
	
	public void setMostRecentPlay(LocalDate date) {	this.mostRecentPlay = date;	}
	public void setNumTimesPlayed(int n) {			this.numTimesPlayed = n;		}
	
	//Adds one to number of song plays
	public void incrementNumPlays() {	this.numTimesPlayed++;	}
	//Resets number of song plays to zero.
	public void resetNumPlays() {	this.numTimesPlayed = 0;		}
	
	
	/*
	 *   functionality:	converts song length to a String formatted time MM:SS
	 *   Parameters:		~
	 */
	public String toMinutes() {
		
		int minutes = length / 60;
		int seconds = length % 60;
		
		String secStr = String.format("%02d", seconds);
		
		return  minutes + ":" + secStr;
	}
}