package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Enables object persistence for users by writing to file on disk.
 * 
 * @author Abdullah Asaad
 *
 */
public class UserPersistence {

	private static String filePath = "listOfUsers";

	/**
	 * Reads persistent objects stored in file.
	 * 
	 * @return List of users from file
	 */
	@SuppressWarnings("unchecked")
	public static List<User> readPersistedObject() {
		List<User> userList = null;

		try {
			FileInputStream inBytes = new FileInputStream(filePath);
			ObjectInputStream inStream = new ObjectInputStream(inBytes);
			userList = (List<User>) inStream.readObject();
			inStream.close();
		} catch (FileNotFoundException fileExc) {
			System.out.println("File not found when reading persistent objects");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * Writes a list of users to file for persistence.
	 * 
	 * @param userList
	 *            List of users to be stored
	 */
	public static void writePersistedObject(List<User> userList) {

		try {
			FileOutputStream outBytes = new FileOutputStream(filePath);
			ObjectOutputStream outFile = new ObjectOutputStream(outBytes);
			outFile.writeObject(userList);
			outFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found when writing a persistent object");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}