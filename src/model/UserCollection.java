package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;

public class UserCollection implements Serializable {

	private List<User> theUsers;
	
	public UserCollection() {
	    theUsers = new ArrayList<User>();
	    setUpDefaultList();
	  }

	  /**
	   * Initializes the list with seven (7) hard-coded students
	   */
	  private void setUpDefaultList() {
		  theUsers.add(new User("Chris",1,false));
		  theUsers.add(new User("Devon",22,false));
		  theUsers.add(new User("River",333,false));
		  theUsers.add(new User("Ryan",4444,false));
		  theUsers.add(new User("Merlin",7777777,true));
	  }
	  
	public int size() {
		return theUsers.size();
	}
	
	public void add(String username, String password) {
		try {
			int pswrd = Integer.parseInt(password);
			User newUser = new User(username,pswrd,false);
			theUsers.add(newUser);
			//newAlertMessage("Success","User (" + username + ") added.");
		}
		catch (Exception e){
			//login_response.setText("Enter a valid password");
		}	
	}

	public User get(int index) {
		return theUsers.get(index);
	}
	
	// function to check if Username is already taken
	public void remove(String username) {
		for (int i = 0; i < theUsers.size(); i++){
			if ((theUsers.get(i).getID().equals(username)))
			{
				if (theUsers.get(i).getAdminAccess() == false) {
					theUsers.remove(i);
//					newAlertMessage("Success", "User (" + username + ") removed.");
				}
				else
//					newAlertMessage("Failed", "Cannot remove admin (" + username + ")");
				
				return;
			}
		}
	}
	
	// function used in LoginButtonListener to verify the user exists in the UserList
	public User findUser(String username, String password) {
		  
		try {
			int pswrd = Integer.parseInt(password);
			for (int i = 0; i < this.size(); i++){
				if ((this.get(i).getID().equals(username)) && (this.get(i).getPassword() == pswrd))
				{
					this.get(i).setNumLogins(this.get(i).getNumLogins()+1);
					return this.get(i); 
				}
			}
		}
		catch (Exception e){
			//newAlertMessage("Failed","Enter a valid username/password.");
		}
		return null;
	}
	

	// function to check if Username is already taken
	public boolean checkUsernameTaken(String username) {
		for (int i = 0; i < theUsers.size(); i++){
			if ((theUsers.get(i).getID().equals(username)))
				return true;
		}
		return false;
	}
}
