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

	/**
	 * 
	 */
	private static final long serialVersionUID = -2311068485430113718L;
	private String ID;
	private int password;
	private boolean admin_access;
	private int num_logins;
	private SongQueue mySongQueue;
	private ArrayList<Song> songPlayList;
	
	// default constructor
	public User() {
		this.ID = "z";
		this.password = -9999;
		this.admin_access = false;
		this.num_logins = 0;
		this.mySongQueue = new SongQueue(false);
		this.songPlayList = new ArrayList<>(7);
		initializeSongPlayListDefault();
	}
	// pre-defined constructor
	public User(String id, int pswrd, boolean admin){
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
	public boolean addToSongPlayList(Song song) {
		return this.songPlayList.add(song);
	}
	private void initializeSongPlayListDefault() {
		
		addToSongPlayList(new Song("Pokemon Capture","Pikachu",5,"Capture.wav"));
		addToSongPlayList(new Song("Danse Macabre","Kevin MacLeod",34,"DanseMacabreViolinHook.wav"));
		addToSongPlayList(new Song("Determined Tumbao","FreePlay Music",20,"DeterminedTumbao.wav"));
		addToSongPlayList(new Song("Loping Sting","Kevin MacLeod",5,"LopingSting.wav"));
		addToSongPlayList(new Song("Swing Cheese","FreePlay Music",15,"SwingCheese.wav"));
		addToSongPlayList(new Song("The Curtain Rises","Kevin Macleod",28,"TheCurtainRises.wav"));
		addToSongPlayList(new Song("Untameable Fire","Pierre Langer",262,"UntabeableFire.wav"));
	}
}
