package model;

/**
 * Stores and manages user account information and privileges.
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class User {
	private String ID;
	private int password;
	private boolean admin_access;
	private int num_logins;
	private SongQueue mySongQueue;
	
	// default constructor
	public User() {
		this.ID = "z";
		this.password = -9999;
		this.admin_access = false;
		this.num_logins = 0;
		mySongQueue = new SongQueue();
	}
	// pre-defined constructor
	public User(String id, int pswrd, boolean admin){
		this.ID = id;
		this.password = pswrd;
		this.admin_access = admin;
		this.num_logins = 0;
		mySongQueue = new SongQueue();
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
	
	
}
