package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for all user account and associated methods to add, find, and
 * remove user accounts.
 * 
 * @author Abdullah Asaad and Gavin Daniel
 *
 */
public class UserCollection {

	private List<User> theUsers;

	/**
	 * Default Constructor. Initialize user list with original accounts
	 */
	public UserCollection() {
		theUsers = new ArrayList<User>();
		setUpDefaultList();
	}

	/**
	 * Alternative constructor initializes user list with given parameter. To be
	 * used with persistence.
	 */
	public UserCollection(List<User> userList) {
		this.theUsers = new ArrayList<>(userList);
	}

	/**
	 * Initializes the list with seven (7) hard-coded students
	 */
	private void setUpDefaultList() {
		theUsers.add(new User("Chris", 1, false));
		theUsers.add(new User("Devon", 22, false));
		theUsers.add(new User("River", 333, false));
		theUsers.add(new User("Ryan", 4444, false));
		theUsers.add(new User("Merlin", 7777777, true));
	}

	/**
	 * FIns user by index
	 * 
	 * @param index
	 *            desired index in collection
	 * @return User at specified index
	 */
	public User get(int index) {
		return theUsers.get(index);
	}

	public List<User> getUserList() {
		return this.theUsers;
	}

	public int size() {
		return theUsers.size();
	}

	/**
	 * Adds new user to collection of user accounts.
	 * 
	 * @param username
	 *            name of user account
	 * @param password
	 *            password associated with specified account
	 */
	public void add(String username, String password) {
		try {
			int pswrd = Integer.parseInt(password);
			User newUser = new User(username, pswrd, false);
			theUsers.add(newUser);
		} catch (Exception e) {
			// login_response.setText("Enter a valid password");
		}
	}

	/**
	 * Removes user account.
	 * 
	 * @param username
	 *            name of account to remove
	 */
	public void remove(String username) {
		for (int i = 0; i < theUsers.size(); i++) {
			if ((theUsers.get(i).getID().equals(username))) {
				if (theUsers.get(i).getAdminAccess() == false)
					theUsers.remove(i);

				return;
			}
		}
	}

	/**
	 * Searches for user with specified login info.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUser(String username, String password) {

		try {
			int pswrd = Integer.parseInt(password);
			for (int i = 0; i < this.size(); i++) {
				if ((this.get(i).getID().equals(username)) && (this.get(i).getPassword() == pswrd)) {
					this.get(i).setNumLogins(this.get(i).getNumLogins() + 1);
					return this.get(i);
				}
			}
		} catch (Exception e) {
			// newAlertMessage("Failed","Enter a valid username/password.");
		}
		return null;
	}

	/**
	 * function to check if Username is already taken
	 * 
	 * @param username
	 *            user name to check
	 * @return true if username already exists, false otherwise
	 */
	public boolean checkUsernameTaken(String username) {
		for (int i = 0; i < theUsers.size(); i++) {
			if ((theUsers.get(i).getID().equals(username)))
				return true;
		}
		return false;
	}
}
