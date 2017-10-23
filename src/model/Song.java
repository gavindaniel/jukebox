package model;
// Added to allow package model to exist on GitHub

import java.io.File;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds essential information about a song and provides functionality to
 * manage number of song plays.
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class Song {
	
	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty artist = new SimpleStringProperty();
	private final IntegerProperty length_s = new SimpleIntegerProperty();		//In seconds
	private final StringProperty length = new SimpleStringProperty();			// String version
	private final StringProperty filePath = new SimpleStringProperty();
	private final IntegerProperty numTimesPlayed = new SimpleIntegerProperty();;
	private LocalDate mostRecentPlay;
	
	public Song (String title, String artist, int length_s, String filePath) {
		
		this.title.set(title);
		this.artist.set(artist);
		this.length_s.set(length_s);
		this.length.set(toMinutes(length_s));
		this.filePath.set("songfiles/" + filePath);
		this.numTimesPlayed.set(0);
		this.mostRecentPlay = LocalDate.now();
	}
	
	// Getters and Setters
	public String getTitle() {
		return this.title.get();
	}
	
	public String getArtist() {
		return this.artist.get();
	}
	
	//returns Seconds
	public int getSongLength() {
		return this.length_s.get();
	}
	
	public String getFilePath() {
		return this.filePath.get();
	}
	public int getNumTimesPlayed() {
		return this.numTimesPlayed.get();
	}
	public LocalDate getMostRecentPlay() {
		return this.mostRecentPlay;
	}
	
	public void setMostRecentPlay(LocalDate date) {
		this.mostRecentPlay = date;
	}
	
	public void setNumTimesPlayed(int n) {
		this.numTimesPlayed.set(n);
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
		this.numTimesPlayed.set(this.numTimesPlayed.get()+1);
	}
	//Resets number of song plays to zero.
	public void resetNumPlays() {
		this.numTimesPlayed.set(0);
	}
	
	// Returns a string used to initialize a Media object to play song
	public String getPlayableSource() {
		 return (new File(getFilePath())).toURI().toString();
	}
}