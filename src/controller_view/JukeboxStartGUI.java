package controller_view;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This program is a functional spike to determine the interactions are 
 * actually working. It is an event-driven program with a graphical user
 * interface to affirm the functionality all Iteration 1 tasks have been 
 * completed and are working correctly. This program will be used to 
 * test your code for the first 100 points of the JukeBox project.
 */

// Given by Rick
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Song;
// Added by Gavin
import model.User;

public class JukeboxStartGUI extends Application {
  
	// BorderPane and GridPanes
	private BorderPane all;
	private GridPane topBar;
	private GridPane leftSide;
	private GridPane rightSide;
	// Labels 
	private Label acct_name;
	private Label acct_pswrd;
	private Label login_response;
	// TextFields for User input
	private TextField name_input;
	private PasswordField pswrd_input;
	// Buttons for the Login Screen
	private Button login_button;
	private Button logout_button;
	private Button song1_button;
	private Button song2_button;
	// List of Users 
	private ArrayList<User> userList;
	// login tracker(s)
	private User currentUser;
	private int num_login_attempts;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		populateUserList();
		layoutGUI_setupModel(primaryStage);
		registerListeners();
	}
	  
	//Place components
	private void layoutGUI_setupModel(Stage stage) {
		all = new BorderPane();
		leftSide = new GridPane();
		rightSide = new GridPane();
		topBar = new GridPane();
		currentUser = null;
		
		
		song1_button = new Button("Select song 1");
		song2_button = new Button("Select song 2");
		topBar.setAlignment(Pos.CENTER);
		topBar.setHgap(10);
		topBar.add(song1_button, 0, 0);
		topBar.add(song2_button, 1, 0);
		
		
		acct_name = new Label("Account Name");
		acct_pswrd = new Label("\tPassword");
		leftSide.setVgap(12);
		leftSide.setHgap(20);
		leftSide.add(acct_name, 1, 1);
		leftSide.add(acct_pswrd, 1, 3);
	
		
		name_input = new TextField();
		pswrd_input = new PasswordField();
		login_button = new Button("Login");
		login_response = new Label("Please login first.");
		logout_button = new Button("Logout");
		rightSide.setVgap(11);
		rightSide.add(name_input, 0, 1);
		rightSide.add(pswrd_input, 0, 2);
		rightSide.add(login_button, 0, 3);
		rightSide.add(login_response, 0, 4);
		rightSide.add(logout_button, 0, 5);
		
		all.setTop(topBar);
		all.setLeft(leftSide);
		all.setRight(rightSide);
		
		Scene scene = new Scene(all, 300, 220);
		stage.setScene(scene);
		stage.show();
	}
	// called from the start to populates the UserList 
	private void populateUserList() {
		userList = new ArrayList<User>();
		userList.add(new User("Chris",1,false));
		userList.add(new User("Devon",22,false));
		userList.add(new User("River",333,false));
		userList.add(new User("Ryan",4444,false));
		userList.add(new User("Merlin",7777777,true));
	}
	
	// called from the start to add all listeners to the model
	private void registerListeners() {
		login_button.setOnAction(new LoginButtonListener());
		logout_button.setOnAction(new LogoutButtonListener());
		song1_button.setOnAction(new SongButtonListener());
		song2_button.setOnAction(new SongButtonListener());
	}
	
	// function used in LoginButtonListener to verify the user exists in the UserList
	private User findUser(String username, String password) {
		  
		try {
			int pswrd = Integer.parseInt(password);
			for (int i = 0; i < userList.size(); i++){
				if ((userList.get(i).getID().equals(username)) && (userList.get(i).getPassword() == pswrd))
				{
					userList.get(i).setNumLogins(userList.get(i).getNumLogins()+1);
					return userList.get(i); 
				}
			}
		}
		catch (Exception e){
			System.out.println("*** EXCEPTION THROWN: " + e.getMessage());
		}
		
		return null;
	}
	
	// function to check if Username is already taken
	private boolean checkUsernameTaken(String username) {
		for (int i = 0; i < userList.size(); i++){
			if ((userList.get(i).getID().equals(username)))
			{
				return true;
			}
		}
		return false;
	}
	
	// function to check if Username is already taken
	private void addUser(String username, int password) {
		User newUser = new User(username,password,false);
		userList.add(newUser);
	}
		
	// function to check if Username is already taken
	private void removeUser(String username) {
		for (int i = 0; i < userList.size(); i++){
			if ((userList.get(i).getID().equals(username)))
			{
				if (userList.get(i).getAdminAccess() == false) {
					userList.remove(i);
					login_response.setText("User (" + username + ") removed.");
				}
				else 
					login_response.setText("Cannot remove admin (" + username + ")");
			}
		}
	}
		
	// function to add the Add/Drop buttons for when an admin logs-in.
	private void addAdminButtons() {
		Button addButton = new Button("Add");
		addButton.setOnAction(new AddButtonListener());
		Button removeButton = new Button("Remove");
		removeButton.setOnAction(new RemoveButtonListener());
		leftSide.add(addButton, 1, 5);
		leftSide.add(removeButton, 1, 6);
	}
	
	// function to remove the Add/Drop buttons for when an admin logs-out.
	private void removeAdminButtons() {
		all.setLeft(null);
		leftSide = new GridPane();
		leftSide.setVgap(12);
		leftSide.setHgap(20);
		leftSide.add(acct_name, 1, 1);
		leftSide.add(acct_pswrd, 1, 3);
		all.setLeft(leftSide);
	}
	// clears Inputs
	private void clearInputs(){
		name_input.clear();
		pswrd_input.clear();
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LoginButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			// check if the user filled in both input fields
			if (acct_name_input.equals("") || acct_password_input.equals("")){
				login_response.setText("Missing name/password.");
				System.out.println(acct_name_input);
				System.out.println(acct_password_input);
				return; // EXIT : ERROR
			}
			// check if someone is already logged in before trying to log-in
			if (currentUser == null) {
				currentUser = findUser(acct_name_input, acct_password_input);
			}
			else {
				login_response.setText("logout first -> (" + currentUser.getID() + ")");
				return;
			}
			// if no one is logged in, check if the user attempting to login actually exists
			if (currentUser != null){
				login_response.setText("Hello! " + acct_name_input);
				num_login_attempts = 0;
				clearInputs();
				System.out.println("***** Num. times logged in:  " + currentUser.getNumLogins() + "  *****");
				if (currentUser.getAdminAccess()) {
					addAdminButtons();
				}
			}
			else {
				num_login_attempts++;
				login_response.setText("Incorrect. " + (3-num_login_attempts) + " attempt(s) left");
			}	
		}
	}
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LogoutButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			if (currentUser != null){
				login_response.setText("Good-bye! " + currentUser.getID());
				if (currentUser.getAdminAccess()) {
					removeAdminButtons();
				}
				currentUser = null;
			}
			else {
				login_response.setText("Please login first.");
			}
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class AddButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			
			if (acct_name_input.equals("") || acct_password_input.equals("")){
				login_response.setText("Missing name/password.");
				System.out.println(acct_name_input);
				System.out.println(acct_password_input);
				return; // EXIT : ERROR
			}
			
			boolean userTaken = checkUsernameTaken(acct_name_input);
			if (userTaken) {
				login_response.setText("Username (" + acct_name_input + ") taken.");
			}
			else {
				currentUser = findUser(acct_name_input, acct_password_input);
			}
			
			
			if (currentUser == null){ // User does not exist
				addUser(acct_name_input, Integer.parseInt(acct_password_input));
				login_response.setText("User (" + acct_name_input + ") added.");
				clearInputs();
			}
			else {
				login_response.setText("User (" + currentUser.getID() + ") exists");
				clearInputs();
			}
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class RemoveButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			
			if (acct_name_input.equals("")){
				login_response.setText("Missing username.");
				System.out.println(acct_name_input);
				return; // EXIT : ERROR
			}
			
			boolean userExists = checkUsernameTaken(acct_name_input);
			
			if (userExists) {
				removeUser(acct_name_input);
				clearInputs();
			}
			else {
				login_response.setText("User (" + acct_name_input + ") DNE.");
				clearInputs();
			}
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class SongButtonListener implements EventHandler<ActionEvent> {
		Song song1 = new Song("LopingSting.mp3");
		Song song2 = new Song("Capture.mp3");
		File file1 = new File(song1.getFilePath());
		File file2 = new File(song2.getFilePath());
		URI uri1 = file1.toURI();
		URI uri2 = file2.toURI();
		Media media1 = new Media(uri1.toString());
		Media media2 = new Media(uri2.toString());
		MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
		MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
//		mediaPlayer1.setOnEndOfMedia(new EndOfSongHandler());
		
		@Override
		public void handle(ActionEvent event) {
//			mediaPlayer1.setOnEndOfMedia(new EndOfSongHandler());
			if (event.getSource().toString().contains("Select song 1")) {
				System.out.println("Song 1 selected");
				mediaPlayer1 = new MediaPlayer(media1);
				mediaPlayer1.play();
				mediaPlayer1.setOnEndOfMedia(new EndOfSongHandler());
				song1.setNumTimesPlayed(song1.getNumTimesPlayed()+1);
				System.out.println("Media Player 1 playCount: " + song1.getNumTimesPlayed());
			}
			else if (event.getSource().toString().contains("Select song 2")) {
				System.out.println("Song 2 selected");
				mediaPlayer2 = new MediaPlayer(media2);
				mediaPlayer2.setOnEndOfMedia(new EndOfSongHandler());
				mediaPlayer2.play();
				song2.setNumTimesPlayed(song2.getNumTimesPlayed()+1);
				System.out.println("Media Player 2 playCount: " + song2.getNumTimesPlayed());
			}
			
		}
	}
	
	private class EndOfSongHandler implements Runnable {
	    @Override
	    public void run() {
//	    	System.out.println(this.toString());
	    	System.out.println("Song ended");
	    	
	//      songsPlayed++;
	//      Alert alert = new Alert(AlertType.INFORMATION);
	//      alert.setTitle("Message");
	//      alert.setHeaderText("Song ended, can now play song #" + songsPlayed);
	//      alert.showAndWait();
	      
	    }
	  }
	
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}