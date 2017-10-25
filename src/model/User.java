package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores and manages user account information and privileges.
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -2311068485430113718L;
	private String ID;
	private int password;
	private boolean admin_access;
	private int num_logins;
	private SongQueue mySongQueue;
	private ArrayList<Song> songPlayList;

	/**
	 * Default Constructor
	 */
	public User() {
		this.ID = "z";
		this.password = -9999;
		this.admin_access = false;
		this.num_logins = 0;
		this.mySongQueue = new SongQueue(false);
		this.songPlayList = new ArrayList<>(7);
		initializeSongPlayListDefault();
	}

	/**
	 * User-Defined Constructor
	 * 
	 * @param id
	 *            String containing User ID
	 * @param pswrd
	 *            password to be associated with user ID
	 * @param admin
	 *            true to grant admin access, false otherwise.
	 */
	public User(String id, int pswrd, boolean admin) {
		this.ID = id;
		this.password = pswrd;
		this.admin_access = admin;
		this.num_logins = 0;
		this.mySongQueue = new SongQueue(admin);
		this.songPlayList = new ArrayList<>(7);
		initializeSongPlayListDefault();
	}

	public String getID() {
		return this.ID;
	}

	public int getPassword() {
		return this.password;
	}

	public boolean getAdminAccess() {
		return this.admin_access;
	}

	public int getNumLogins() {
		return this.num_logins;
	}

	public SongQueue getSongQueue() {
		return mySongQueue;
	}

	public void setNumLogins(int n) {
		this.num_logins = n;
	}

	public ArrayList<Song> getSongPlayList() {
		return this.songPlayList;
	}

	/**
	 * Adds new song to user playlist.
	 * 
	 * @param song
	 *            Song object to be added
	 * @return true if add operation is successful.
	 */
	public boolean addToSongPlayList(Song song) {
		return this.songPlayList.add(song);
	}

	/**
	 * Initializes a user playlist with the provided list of songs.
	 */
	private void initializeSongPlayListDefault() {

		addToSongPlayList(new Song("Pokemon Capture", "Pikachu", 5, "Capture.mp3"));
		addToSongPlayList(new Song("Danse Macabre", "Kevin MacLeod", 34, "DanseMacabreViolinHook.mp3"));
		addToSongPlayList(new Song("Determined Tumbao", "FreePlay Music", 20, "DeterminedTumbao.mp3"));
		addToSongPlayList(new Song("Loping Sting", "Kevin MacLeod", 5, "LopingSting.mp3"));
		addToSongPlayList(new Song("Swing Cheese", "FreePlay Music", 15, "SwingCheese.mp3"));
		addToSongPlayList(new Song("The Curtain Rises", "Kevin Macleod", 28, "TheCurtainRises.mp3"));
		addToSongPlayList(new Song("Untameable Fire", "Pierre Langer", 262, "UntabeableFire.mp3"));
	}
}
