package model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a queue of songs chosen by the user. Provides methods for validating, adding, and 
 * removing a given song.
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class SongQueue {

	private Queue<Song> queueOfSongs;
	private int NumSongsSelected;
	private int timeRemaining;	//Seconds
	private LocalDate mostRecentSelection;
	public int MAX;

	//Constructor instantiates an LinkedList object implementing the Queue
	public SongQueue(boolean unlimited) {
		this.queueOfSongs = new LinkedList<>();
		this.NumSongsSelected = 0;
		this.timeRemaining = 1500*60;
		this.mostRecentSelection = LocalDate.now();
		
		if (unlimited)
			MAX = 9999;
		else 
			MAX = 3;
	}
	
	// Getters and Setters
	public Queue<Song> getQueueOfSongs() {
		return this.queueOfSongs;
	}
	public int getNumSongsSelected() {
		return this.NumSongsSelected;
	}
	public int getMinsRemaining() {
		return this.timeRemaining/60;
	}
	public int getSecondsRemaining() {
		return this.timeRemaining;
	}
	public LocalDate getMostRecentSelection() {
		return this.mostRecentSelection;
	}
	
	public void setMostRecentSelection(LocalDate date) {
		this.mostRecentSelection = date;
	}
	
	/**
	 * If song passes validation, it is added to the song queue to be played.
	 * Otherwise an error message is returned.
	 * 
	 * @param song to be added
	 * @param minsRemaining minutes remaining on user account
	 * @return message to user.
	 */
	public String addSong (Song song) {
		
		int valid = validateSong(song);
		
		if (valid == 0) {
			queueOfSongs.add(song);
			NumSongsSelected++;
			timeRemaining -= song.getSongLength();
//			song.incrementNumPlays();
			
			return "Success";
		}
		
		else {
			if (valid == 1) return "Can not add more songs. Maximum song limit has been reached.";
			
			else if (valid == 2) return "Song has already been played maximum amount of times.";
			
			else return timeRemaining + " minutes left in account.\nSong is too long to add to queue";	
		}
	}

	/**
	 * Validates songs according to the following criteria:
	 * - A song can be played a maximum of 3 times per day.
	 * - A maximum number 3 songs per day may be played.
	 * - The user must have enough minutes on account to complete a chosen song.
	 * 
	 * @param song Song object to be added
	 * @param minsRemaining minutes remaining on user account
	 * @return int representing success (0) or error code (1,2,3).
	 */
	private int validateSong(Song song) {

		//Check user selection date
		if (mostRecentSelection.compareTo(LocalDate.now()) < 0) {
			NumSongsSelected = 0;
			mostRecentSelection = LocalDate.now();
		}
		
		//Check song date
		if (song.getMostRecentPlay().compareTo(LocalDate.now()) < 0) {
			song.setMostRecentPlay(LocalDate.now());
			song.setNumTimesPlayed(0);
		}
		
		if (NumSongsSelected >= MAX) return 1;

		if (song.getNumTimesPlayed() >= MAX) return 2;

		if (timeRemaining < song.getSongLength()) return 3;

		return 0;
	}
	
	//Returns Song from head of queue. Returns null if queue is empty.
	public Song serveNextSong() {
		return queueOfSongs.peek();
	}
	//Returns and removes Song from head of queue. Returns null if queue is empty.
	public Song removeLastPlayedSong() {
		return queueOfSongs.poll();
	}

}
