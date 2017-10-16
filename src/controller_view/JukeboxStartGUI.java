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
  
	// TextFields for User input
	private TextField name_input;
	private PasswordField pswrd_input;
	// Buttons for the Login Screen
	private Button login_button;
	private Button logout_button;
	private Button song1_button;
	private Button song2_button;
	private Label login_response;
	// List of Users 
	private ArrayList<User> userList;
	// flag for if a User is logged in
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
		BorderPane all = new BorderPane();
		GridPane leftSide = new GridPane();
		GridPane rightSide = new GridPane();
		GridPane topBar = new GridPane();
		currentUser = null;
		
		
		song1_button = new Button("Select song 1");
		song2_button = new Button("Select song 2");
		topBar.setAlignment(Pos.CENTER);
		topBar.setHgap(10);
		topBar.add(song1_button, 0, 0);
		topBar.add(song2_button, 1, 0);
		
		
		Label acct_name = new Label("Account Name");
		Label acct_pswrd = new Label("Password");
		leftSide.setVgap(12);
		leftSide.add(acct_name, 0, 1);
		leftSide.add(acct_pswrd, 0, 3);
	
		
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
	//	searchBar.textProperty().addListener(new SearchBarListener());
		login_button.setOnAction(new LoginButtonListener());
		logout_button.setOnAction(new LogoutButtonListener());
		song1_button.setOnAction(new SongButtonListener());
		song2_button.setOnAction(new SongButtonListener());
	}
	
	// function used in LoginButtonListener to verify the user exists in the UserList
	private User verifyUser(String username, String password) {
		  
		
		 
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
			System.out.println("ERROR " + e);
		}
		
		return null;
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LoginButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			
			if (acct_name_input.equals("") || acct_password_input.equals("")){
				login_response.setText("Missing name/password.");
				System.out.println(acct_name_input);
				System.out.println(acct_password_input);
				currentUser = verifyUser(acct_name_input, acct_password_input);
			}
			else {
				if (currentUser != null){
					login_response.setText("Hello! " + acct_name_input);
					num_login_attempts = 0;
					clearInputs();
					System.out.println("***** Num. times logged in:  " + currentUser.getNumLogins() + "  *****");
				}
				else {
					num_login_attempts++;
					login_response.setText("Incorrect. " + (3-num_login_attempts) + " attempt(s) left");
	//				System.out.println("***** Num. attempts left:  " + (3-num_login_attempts) + "  *****");
				}
			}
			
		}
		// clears Inputs
		private void clearInputs(){
			name_input.clear();
			pswrd_input.clear();
		}
	}
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LogoutButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			if (currentUser != null){
				login_response.setText("Good-bye! " + currentUser.getID());
			}
			else {
				login_response.setText("Please login first.");
			}
		}
	}
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class SongButtonListener implements EventHandler<ActionEvent> {
		Song song1 = new Song("SwingCheese.mp3");
		Song song2 = new Song("Capture.mp3");
		File file1 = new File(song1.getFilePath());
		File file2 = new File(song2.getFilePath());
		URI uri1 = file1.toURI();
		URI uri2 = file2.toURI();
		Media media1 = new Media(uri1.toString());
		Media media2 = new Media(uri2.toString());
		MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
		
		@Override
		public void handle(ActionEvent event) {
			
			if (event.getSource().toString().contains("Select song 1")) {
				System.out.println("Song 1 selected");
				mediaPlayer1 = new MediaPlayer(media1);
				mediaPlayer1.play();
				mediaPlayer1.setOnEndOfMedia(new EndOfSongHandler());
			}
			else if (event.getSource().toString().contains("Select song 2")) {
				System.out.println("Song 2 selected");
				mediaPlayer1 = new MediaPlayer(media2);
				mediaPlayer1.play();
				mediaPlayer1.setOnEndOfMedia(new EndOfSongHandler());
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