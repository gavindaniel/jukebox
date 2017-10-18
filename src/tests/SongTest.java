package tests;


import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.Song;
import model.SongQueue;

public class SongTest {


	/**
	 * Testing daily reset of user play counts
	 */
	@Test
	public void testNumberofSongsPerUser() {

		SongQueue queue = new SongQueue();

		Song song1 = new Song("Loping Sting", "Kevin MacLeod", 5, "LopingSting.wav");
		Song song2 = new Song("Pokemon Capture", "Pikachu", 5, "Capture.wav");

		queue.addSong(song1);
		queue.addSong(song2);
		queue.addSong(song1);

		String output = queue.addSong(song2);
		assertEquals(3, queue.getNumSongsSelected());
		assertNotEquals("Success", output);

		queue.setMostRecentSelection(LocalDate.now().minusDays(1));

		output = queue.addSong(song2);

		assertEquals(1, queue.getNumSongsSelected());
		assertEquals("Success", output);
	}

	/**
	 * Testing daily reset of song play counts
	 */
	@Test
	public void testNumberofPlaysPerSong() {

		SongQueue queue = new SongQueue();

		Song song1 = new Song("Loping Sting", "Kevin MacLeod", 5, "LopingSting.wav");

		queue.addSong(song1);
		queue.addSong(song1);
		queue.addSong(song1);

		String output = queue.addSong(song1);
		assertEquals(3, song1.getNumTimesPlayed());
		assertNotEquals("Success", output);

		song1.setMostRecentPlay(LocalDate.now().minusDays(1));

		output = queue.addSong(song1);

		assertEquals(0, song1.getNumTimesPlayed());
		assertNotEquals("Success", output);

		queue.setMostRecentSelection(LocalDate.now().minusDays(1));

		output = queue.addSong(song1);

		assertEquals(1, song1.getNumTimesPlayed());
		assertEquals("Success", output);
	}

	/**
	 * Testing daily reset of song play counts
	 */
	@Test
	public void testTimeRemaining() {

		SongQueue queue = new SongQueue();

		Song song1 = new Song("Loping Sting", "Kevin MacLeod", 5, "LopingSting.wav");
		Song song2 = new Song("Pokemon Capture", "Pikachu", 5, "Capture.wav");

		queue.addSong(song1);
		queue.addSong(song2);
		queue.addSong(song1);

		int playedDuration = 2*song1.getSongLength() + song2.getSongLength();
		assertEquals((1500*60) - playedDuration, queue.getSecondsRemaining());

		queue.setMostRecentSelection(LocalDate.now().minusDays(1));

		queue.addSong(song2);
		queue.addSong(song2);

		playedDuration += 2*song2.getSongLength();

		assertEquals((1500*60) - playedDuration, queue.getSecondsRemaining());

	}

}