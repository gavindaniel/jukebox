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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Song;
import model.SongLibrary;
import model.SongPlayer;
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
	private Button addSong_button;
	// Song Library
	private static SongLibrary song_library = new SongLibrary();
	// ListView for song queue
//	private TableView<Song> song_library;
	private ListView<String> song_queue;
	private ObservableList<Song> songs_in_library;
	private ObservableList<String> songs_in_queue;
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
		
		Scene scene = new Scene(all, 700, 500);
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
	
	// called from the start to register login listeners to the model
	private void registerLoginListeners() {
		login_button.setOnAction(new LoginButtonListener());
		logout_button.setOnAction(new LogoutButtonListener());
	}
	
	// constructs song List
	private void populateSongLibrary() {
			songs_in_library = FXCollections.observableArrayList(
				new Song("Pokemon Capture","Pikachu",5,"Capture.mp3"),
				new Song("Danse Macabre","Kevin MacLeod",34,"DanseMacabreViolinHook.mp3"),
				new Song("Determined Tumbao","FreePlay Music",20,"DeterminedTumbao.mp3"),
				new Song("Loping Sting","Kevin MacLeod",5,"LopingSting.mp3"),
				new Song("Swing Cheese","FreePlay Music",15,"SwingCheese.mp3"),
				new Song("The Curtain Rises","Kevin Macleod",28,"TheCurtainRises.mp3"),
				new Song("Untameable Fire","Pierre Langer",262,"UntabeableFire.mp3"));
			
			song_library.setItems(songs_in_library);
	}
	
	//Sets up song queue ListView as well as song selection buttons. Appears once user login.
	private void setupUserQueue() {

		//Set up ListView for song queue
		songs_in_queue = FXCollections.observableArrayList();
		song_queue = new ListView<String>();
		//Add any songs previously present in user playlist to queue
		for (Song song : currentUser.getSongQueue().getQueueOfSongs()) {
			songs_in_queue.add( song.getTitle() + "\t" + song.toMinutes( song.getSongLength() ) );
		}
		song_queue.setItems(songs_in_queue);
		
		// initialize Song Library
		populateSongLibrary();
		
		//Set up song selection buttons
		addSong_button = new Button("<");
		buttonBox.getChildren().add(addSong_button);
		buttonBox.setSpacing(15);
		
		//Setting width of buttons to be equal
		for (Node node : buttonBox.getChildren()) {
			Button button = (Button) node;
			button.setMaxWidth(500);
		}
		
		//Register handlers for buttons
		registerButtonListeners();
		
		//Add list and button to bottomBox
		bottomBox.getChildren().add(song_queue);
		bottomBox.getChildren().add(buttonBox);
		bottomBox.getChildren().add(song_library);
		bottomBox.setSpacing(30);
		bottomBox.setPadding(new Insets(40,0,0,0));
		
		//Add Box to bottom of borderPane
		all.setBottom(bottomBox);
		
		//Play any existing songs from old playlist
		startQueue();

	}
	
	private void startQueue() {
		
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
		
	//Called from the start to populates the UserList 
	private void populateUserList() {
		userList = new ArrayList<User>();
		userList.add(new User("Chris",1,false));
		userList.add(new User("Devon",22,false));
		userList.add(new User("River",333,false));
		userList.add(new User("Ryan",4444,false));
		userList.add(new User("Merlin",7777777,true));
	}
	
	

	// called from the start to register song button listeners to the model
	private void registerButtonListeners() {
		addSong_button.setOnAction(new SongButtonListener());
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
				return true;
		}
		return false;
	}
	
	// function to check if Username is already taken
	private void addUser(String username, String password) {
		try {
			int pswrd = Integer.parseInt(password);
			User newUser = new User(username,pswrd,false);
			userList.add(newUser);
			login_response.setText("User (" + username + ") added.");
		}
		catch (Exception e){
			System.out.println("*** EXCEPTION THROWN: " + e.getMessage());
			login_response.setText("Enter a valid password");
		}
		
	}
		
	// function to check if Username is already taken
	private void removeUser(String username) {
		for (int i = 0; i < userList.size(); i++){
			if ((userList.get(i).getID().equals(username)))
			{
				if (userList.get(i).getAdminAccess() == false) {
					userList.remove(i);
					newAlertMessage("Success", "User (" + username + ") removed.");
				}
				else
					newAlertMessage("Failed", "Cannot remove admin (" + username + ")");
				
				return;
			}
		}
	}
		
	private void newAlertMessage(String allowed, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(allowed);
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
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			// check if the user filled in both input fields
			if (acct_name_input.equals("") || acct_password_input.equals("")){
				newAlertMessage("Failed", "Missing username/password.");
				return;
			}
			// check if someone is already logged in before trying to log-in
			if (currentUser == null) {
				currentUser = findUser(acct_name_input, acct_password_input);
				if (currentUser != null) 
					setupUserQueue();
			}
			else {
				newAlertMessage("Failed", "logout first -> (" + currentUser.getID() + ")");
				return;
			}
			// if no one is logged in, check if the user attempting to login actually exists
			if (currentUser != null){
				login_response.setText("Hello! " + acct_name_input);
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
				
				previousUser = currentUser;
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
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			User current = null;
			
			if (acct_name_input.equals("") || acct_password_input.equals("")){
				newAlertMessage("Failed", "Missing username/password.");
				return; 
			}
			
			boolean usernameTaken = checkUsernameTaken(acct_name_input);
			
			if (usernameTaken) 
				newAlertMessage("Failed", "Username (" + acct_name_input + ") already taken.");
			else 
				current = findUser(acct_name_input, acct_password_input);
			
			if (current == null){ // User does not exist
				addUser(acct_name_input, acct_password_input);
				newAlertMessage("Success", "User (" + acct_name_input + ") successfully added.");
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
				newAlertMessage("Failed", "Missing username.");
				return; // EXIT : ERROR
			}
			
			boolean userExists = checkUsernameTaken(acct_name_input);
			
			if (userExists)
				removeUser(acct_name_input);
			else
				newAlertMessage("Failed", "User (" + acct_name_input + ") Does Not Exist!");

			clearInputs();
		}
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class SongButtonListener implements EventHandler<ActionEvent> {
		
		private Song song;
		
		@Override
		public void handle(ActionEvent event) {
			
			try {
				song = song_library.getSelectionModel().getSelectedItem();
				
				SongQueue currentQueue = currentUser.getSongQueue();
				String addStatus = currentQueue.addSong(song);
				
				if (addStatus.compareTo("Success") == 0) {
					
					if (song.getTitle().length() > 16)
						songs_in_queue.add(song.getTitle() + "\t" + song.toMinutes(song.getSongLength()));
					
					else if (song.getTitle().length() < 17 && song.getTitle().length() > 12)
						songs_in_queue.add(song.getTitle() + "\t\t" + song.toMinutes(song.getSongLength()));
					
					else if (song.getTitle().length() < 13 && song.getTitle().length() > 7)
						songs_in_queue.add(song.getTitle() + "\t\t\t" + song.toMinutes(song.getSongLength()));
					
					if (currentQueue.getQueueOfSongs().size() == 1) {
						SongPlayer songPlayer = new SongPlayer(song);
						songPlayer.playSong();
						songPlayer.getMediaPlayer().setOnEndOfMedia(new EndOfSongHandler());
					}
				}
				
				else {
					newAlertMessage("Failed", addStatus);
				}
				song_queue.refresh();
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
		    		Song s = currentUser.getSongQueue().removeLastPlayedSong();
		    		s.incrementNumPlays();
		    		songs_in_queue.remove(0);
		    		playNextSong();
		    	}
		    	
		    	else {
		    		previousUser.getSongQueue().removeLastPlayedSong();
		    	}
		    	
		    	song_library.refresh();
		    	song_queue.refresh();
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
	
	
	
	
	
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}