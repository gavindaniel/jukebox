package model;

public class User {
	private String ID;
	private int password;
	private boolean admin_access;
	
	// default constructor
	public User() {
		this.ID = "z";
		this.password = -9999;
		this.admin_access = false;
	}
	// pre-defined constructor
	public User(String id, int pswrd, boolean admin){
		this.ID = id;
		this.password = pswrd;
		this.admin_access = admin;
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
}
