package controller_view;

import java.util.Optional;

// Given by Rick
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Song;
import model.SongLibrary;
import model.SongPlayer;
import model.SongQueue;
import model.User;
import model.UserCollection;
import model.UserPersistence;

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
	private Button addSong_button;
	// Song Library & Song Queue
	private static SongLibrary songLibrary = new SongLibrary();
	// ObservableLists
	private ListView<String> songListView;
	private ObservableList<String> songsInQueue;
	private UserCollection users;
	// login tracker(s)
	private User currentUser;
	private int num_login_attempts;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeListOfUsers();
		layoutGUI_setupModel(primaryStage);
	}
	
	/**
	 * Sets up the list of user accounts to be used in jukebox.
	 * User list can be assigned default values 
	 * or may retain previous values through persistance.
	 */
	private void initializeListOfUsers() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Start Up Option");
		alert.setHeaderText("Press ok to read persistent object(s)");
		alert.setContentText("Press cancel while system testing.");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			users = new UserCollection(UserPersistence.readPersistedObject());
		} else {
			users = new UserCollection();
		}
	}
	  
	//Place components
	private void layoutGUI_setupModel(Stage stage) {
		all = new BorderPane();
		leftSide = new GridPane();
		rightSide = new GridPane();
		buttonBox = new VBox();
		bottomBox = new HBox();
		currentUser = null;
		
		setupLoginView();
		
		Scene scene = new Scene(all, 700, 500);
		stage.setScene(scene);
		stage.show();
		
		// Prompts user with option to save the current state of users as a persistent object.
	    stage.setOnCloseRequest(new WritePersistentObject());
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
	
	// called from the start to register login listeners to the model
	private void registerLoginListeners() {
		login_button.setOnAction(new LoginButtonListener());
		logout_button.setOnAction(new LogoutButtonListener());
	}
	
	// constructs song List
	private void populateSongLibrary() {
		ObservableList<Song> songPlayList = FXCollections.observableArrayList(currentUser.getSongPlayList());
		songLibrary.setItems(songPlayList);
	}
	
	//Sets up song queue ListView as well as song selection buttons. Appears once user login.
	private void setupUserDashboard() {

		//Set up ListView for song queue
		songsInQueue = FXCollections.observableArrayList();
		songListView = new ListView<String>();
		//Add any songs previously present in user playlist to queue
		for (Song song : currentUser.getSongQueue().getQueueOfSongs()) {
			songsInQueue.add( song.getTitle());
		}
		songListView.setItems(songsInQueue);
		
		// initialize Song Library
		populateSongLibrary();
		
		//Set up song selection buttons
		addSong_button = new Button("<");
		buttonBox.getChildren().add(addSong_button);
		buttonBox.setSpacing(15);
		
		//Register handlers for buttons
		addSong_button.setOnAction(new SongButtonListener());
		
		//Add list and button to bottomBox

		bottomBox.getChildren().add(songListView);
		bottomBox.getChildren().add(buttonBox);
		bottomBox.getChildren().add(songLibrary);
		bottomBox.setSpacing(30);
		bottomBox.setPadding(new Insets(40,0,0,0));
		
		//Add Box to bottom of borderPane
		all.setBottom(bottomBox);
		
		//Play any existing songs from old playlist
		startPlayingQueue();
	}
	
	private void startPlayingQueue() {
		
		Song nextSong = currentUser.getSongQueue().serveNextSong();
		
		if (nextSong != null) {
			SongPlayer songPlayer = new SongPlayer(nextSong);
			songPlayer.playSong();
			songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
    		}	
	}
	
	private void removeUserQueue() {
		all.setBottom(null);
		bottomBox.getChildren().clear();
		buttonBox.getChildren().clear();
	}
	
	private void newAlertMessage(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	// function to add the Add/Drop buttons for when an admin logs-in.
	private void addAdminButtons() {
		Button addButton = new Button("   Add User   ");
		addButton.setOnAction(new AddButtonListener());
		Button removeButton = new Button("Remove User");
		removeButton.setOnAction(new RemoveButtonListener());
		leftSide.add(addButton, 3, 1);
		leftSide.add(removeButton, 3, 3);
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
	
	
/**********************   Button Listeners   *****************************/	
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LoginButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String username_input = name_input.getText();
			String password_input = pswrd_input.getText();
			// check if the user filled in both input fields
			if (username_input.equals("") || password_input.equals("")){
				newAlertMessage("Failed", "Missing username/password.");
				return;
			}
			// check if someone is already logged in before trying to log-in
			if (currentUser == null) {
				currentUser = users.findUser(username_input, password_input);
				if (currentUser != null) 
					setupUserDashboard();
			}
			else {
				newAlertMessage("Failed", "logout first -> (" + currentUser.getID() + ")");
				return;
			}
			// if no one is logged in, check if the user attempting to login actually exists
			if (currentUser != null){
				login_response.setText("Hello! " + username_input);
				num_login_attempts = 0;
				clearInputs();
				if (currentUser.getAdminAccess())
					addAdminButtons();
			}
			else {
				num_login_attempts++;
				newAlertMessage("Failed", "Incorrect. " + (3-num_login_attempts) + " attempt(s) left");
			}	
		}
	}
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LogoutButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			if (currentUser != null){
				login_response.setText("Good-bye! " + currentUser.getID());
				if (currentUser.getAdminAccess()) 
					removeAdminButtons();
				
				currentUser = null;
				removeUserQueue();
			}
			else 
				newAlertMessage("Failed","Please login first.");
			
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class AddButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String username_input = name_input.getText();
			String password_input = pswrd_input.getText();
			User current = null;
			
			if (username_input.equals("") || password_input.equals("")){
				newAlertMessage("Failed", "Missing username/password.");
				return; 
			}
			if ( users.checkUsernameTaken(username_input) ) {
				newAlertMessage("Failed", "Username (" + username_input + ") already taken.");
				return;
			}
			
			current = users.findUser(username_input, password_input);
			if (current == null){ // User does not exist
				users.add(username_input, password_input);
				newAlertMessage("Success", "User (" + username_input + ") successfully added.");
				clearInputs();
			}
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class RemoveButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String username_input = name_input.getText();
			
			if (username_input.equals("")){
				newAlertMessage("Failed", "Missing username.");
				return; 
			}
			
			if ( users.checkUsernameTaken(username_input) ) {
				users.remove(username_input);
				newAlertMessage("Success", "User (" + username_input + ") removed.");
			}
			else
				newAlertMessage("Failed", "User (" + username_input + ") Does Not Exist!");

			clearInputs();
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class SongButtonListener implements EventHandler<ActionEvent> {
		
		private Song song;
		
		@Override
		public void handle(ActionEvent event) {
			
			try {
				song = songLibrary.getSelectionModel().getSelectedItem();
				
				SongQueue currentQueue = currentUser.getSongQueue();
				String addStatus = currentQueue.addSong(song);
				
				if (addStatus.compareTo("Success") == 0) {

					songsInQueue.add(song.getTitle());

					if (currentQueue.getQueueOfSongs().size() == 1) {
						SongPlayer songPlayer = new SongPlayer(song);
						songPlayer.playSong();
						songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
					}
				}
				
				else {
					newAlertMessage("Failed", addStatus);
				}
				songLibrary.refresh();
				songListView.refresh();
			}
			
			catch (NullPointerException e){
				System.out.println("NullPointerException caught");
				newAlertMessage("Failed", "Select a Song first");
			}	
		}
	}
	
	
	private class EndOfSongHandler implements Runnable {
	    @Override
	    public void run() {
		    	
		    	//If-Else Handles case where user logs out during song playback
		    	if (currentUser != null) {
		    		currentUser.getSongQueue().removeLastPlayedSong();
		    		songsInQueue.remove(0);
		    		playNextSong();
		    	}
		    	
		    	songLibrary.refresh();
		    	songListView.refresh();
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
	
	/**
	 * Writes the current list of users, along with all user data, to file.
	 * 
	 * @author Abdullah Asaad and Gavin Daniel
	 *
	 */
	private class WritePersistentObject implements EventHandler<WindowEvent> {

	    @Override
	    public void handle(WindowEvent event) {
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("Shut Down Option");
	      alert.setHeaderText("Press ok to write persistent object(s)");
	      alert.setContentText("Press cancel while system testing.");
	      Optional<ButtonType> result = alert.showAndWait();

	      if (result.get() == ButtonType.OK) {
	        UserPersistence.writePersistedObject(users.getUserList());
	      }
	    }
	  }
	
	
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}