package controller_view;


import java.util.ArrayList;

/**
 * This program is a functional spike to determine the interactions are 
 * actually working. It is an event-driven program with a graphical user
 * interface to affirm the functionality all Iteration 1 tasks have been 
 * completed and are working correctly. This program will be used to 
 * test your code for the first 100 points of the JukeBox project.
 */

// Given by Rick
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Song;
import model.SongQueue;
// Added by Gavin
import model.User;

/**
 * Provides GUI to view and control a jukebox.
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class JukeboxStartGUI extends Application {
  
	// BorderPane, GridPanes, and HBox
	private BorderPane all;
	private VBox buttonBox;
	private GridPane leftSide;
	private GridPane rightSide;
	private HBox bottomBox;
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
	// ListView for song queue
	private ListView<String> songListView;
	// List of Users 
	private ArrayList<User> userList;
	// login tracker(s)
	private User currentUser;
	private User previousUser;
	private int num_login_attempts;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		populateUserList();
		layoutGUI_setupModel(primaryStage);
	}
	  
	//Place components
	private void layoutGUI_setupModel(Stage stage) {
		all = new BorderPane();
		leftSide = new GridPane();
		rightSide = new GridPane();
		buttonBox = new VBox();
		bottomBox = new HBox();
		currentUser = null;
		previousUser = null;
		
		setupLoginView();
		
		Scene scene = new Scene(all, 600, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	private void setupLoginView() {

		name_input = new TextField();
		pswrd_input = new PasswordField();
		login_button = new Button("Login");
		login_response = new Label("Please login first.");
		logout_button = new Button("Logout");
		acct_name = new Label("Account Name");
		acct_pswrd = new Label("\tPassword");
		
		leftSide.setVgap(12);
		leftSide.setHgap(20);
		leftSide.add(acct_name, 1, 1);
		leftSide.add(acct_pswrd, 1, 3);
		leftSide.add(name_input, 2, 1);
		leftSide.add(pswrd_input, 2, 3);
	
		rightSide.setVgap(11);
		rightSide.add(login_button, 0, 1);
		rightSide.add(login_response, 0, 2);
		rightSide.add(logout_button, 0, 3);
		rightSide.setPadding(new Insets(0,40,0,0));
		
		all.setLeft(leftSide);
		all.setRight(rightSide);
		
		registerLoginListeners();
	}
	
	//Sets up song queue ListView as well as song selection buttons. Appears once user login.
	private void setupUserPlaylist() {

		//Set up ListView for song queue
		songListView = new ListView<>();
		//Add any songs previously present in user playlist to queue
		for (Song song : currentUser.getSongQueue().getQueueOfSongs()) {
			songListView.getItems().add(song.getTitle() + "\t\t" + song.toMinutes());
		}
		
		//Set up song selection buttons
		song1_button = new Button("Loping Sting");
		song2_button = new Button("Pokemon Capture");
		buttonBox.getChildren().add(song1_button);
		buttonBox.getChildren().add(song2_button);
		buttonBox.setSpacing(15);
		
		//Setting width of buttons to be equal
		for (Node node : buttonBox.getChildren()) {
			Button button = (Button) node;
			button.setMaxWidth(500);
		}
		
		//Register handlers for buttons
		registerButtonListeners();
		
		//Add list and button to bottomBox
		bottomBox.getChildren().add(songListView);
		bottomBox.getChildren().add(buttonBox);
		bottomBox.setSpacing(30);
		bottomBox.setPadding(new Insets(40,0,0,0));
		
		//Add Box to bottom of borderPane
		all.setBottom(bottomBox);
		
		//Play any existing songs from old playlist
		startPlaylist();

	}
	
	private void startPlaylist() {
		
		Song nextSong = currentUser.getSongQueue().serveNextSong();
		
		if (nextSong != null) {
    		SongPlayer songPlayer = new SongPlayer(nextSong);
			songPlayer.playSong();
			songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
    	}
		
	}
	
	private void removeUserPlaylist() {
		
		all.setBottom(null);
		bottomBox.getChildren().clear();
		buttonBox.getChildren().clear();
	}
		
	//Called from the start to populates the UserList 
	private void populateUserList() {
		userList = new ArrayList<User>();
		userList.add(new User("Chris",1,false));
		userList.add(new User("Devon",22,false));
		userList.add(new User("River",333,false));
		userList.add(new User("Ryan",4444,false));
		userList.add(new User("Merlin",7777777,true));
	}
	
	// called from the start to register login listeners to the model
	private void registerLoginListeners() {
		login_button.setOnAction(new LoginButtonListener());
		logout_button.setOnAction(new LogoutButtonListener());
	}

	// called from the start to register song button listeners to the model
	private void registerButtonListeners() {
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
		leftSide.add(name_input, 2, 1);
		leftSide.add(pswrd_input, 2, 3);
		all.setLeft(leftSide);
	}
	// clears Inputs
	private void clearInputs(){
		name_input.clear();
		pswrd_input.clear();
	}
	// hi
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
				if (currentUser != null) setupUserPlaylist();
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
				previousUser = currentUser;
				currentUser = null;
				removeUserPlaylist();
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
	// hi :-)
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
		
		private Song song;
		
		@Override
		public void handle(ActionEvent event) {
			
			if (event.getSource().toString().contains("Loping Sting")) {
				
				song = new Song("Loping Sting", "Kevin MacLeod", 5, "LopingSting.mp3");
				
				//Add song to queue and play immediately if queue is empty
				SongQueue currentQueue = currentUser.getSongQueue();
				String addStatus = currentQueue.addSong(song);
				
				if (addStatus.compareTo("Success") == 0) {
					
					songListView.getItems().add(song.getTitle() + "\t\t" + song.toMinutes());
					
					if (currentQueue.getQueueOfSongs().size() == 1) {
						
						SongPlayer songPlayer = new SongPlayer(song);
						songPlayer.playSong();
						songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
					}
				}
				
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Not Allowed");
					alert.setHeaderText(addStatus);
					alert.showAndWait();
				}
				
			}
			
			else if (event.getSource().toString().contains("Pokemon Capture")) {

				song = new Song("Pokemon Capture", "Pikachu", 5, "Capture.mp3");

				//Add song to queue and play immediately if queue is empty
				SongQueue currentQueue = currentUser.getSongQueue();
				String addStatus = currentQueue.addSong(song);

				if (addStatus.compareTo("Success") == 0) {

					songListView.getItems().add(song.getTitle() + "\t\t" + song.toMinutes());

					if (currentQueue.getQueueOfSongs().size() == 1) {

						SongPlayer songPlayer = new SongPlayer(song);
						songPlayer.playSong();
						songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
					}

				}

				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Not Allowed");
					alert.setHeaderText(addStatus);
					alert.showAndWait();
				}
			}
			
		}
	}
	
	private class EndOfSongHandler implements Runnable {
	    @Override
	    public void run() {
	    	System.out.println("Song ended");
	    	
	    	//If-Else Handles case where user logs out during song playback
	    	if (currentUser != null) {
	    		currentUser.getSongQueue().removeLastPlayedSong();
	    		songListView.getItems().remove(0);
	    		playNextSong();
	    	}
	    	
	    	else {
	    		previousUser.getSongQueue().removeLastPlayedSong();
	    	}
	    }
	    
	    private void playNextSong() {
	    	
	    	Song nextSong = currentUser.getSongQueue().serveNextSong();
	    	
	    	if (nextSong != null) {
	    		SongPlayer songPlayer = new SongPlayer(nextSong);
				songPlayer.playSong();
				songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
	    	}
	    	
	    }
	  }
	
	private static class SongPlayer {
		
		private Song song;
		private Media songMedia;
		private static MediaPlayer mediaPlayer;
		
		public SongPlayer(Song song) {			
			this.song = song;
			this.songMedia = new Media(this.song.getPlayableSource());
			SongPlayer.mediaPlayer = new MediaPlayer(songMedia);
		}
		
		public MediaPlayer getMediaPlayer() {
			return SongPlayer.mediaPlayer;
		}
		
		public void playSong() {
			SongPlayer.mediaPlayer.play();
		}
	}
	
	
	
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}