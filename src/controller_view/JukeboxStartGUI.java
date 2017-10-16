package controller_view;


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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
	//	VBox songButtons =  new VBox();
		currentUser = null;
		
//		songButtons.setSpacing(5);
		song1_button = new Button("Select song 1");
		song2_button = new Button("Select song 2");
		topBar.setAlignment(Pos.CENTER);
		topBar.setHgap(10);
//		songButtons.getChildren().setAll(song1_button,song2_button);
		topBar.add(song1_button, 0, 0);
		topBar.add(song2_button, 1, 0);
		
		Label acct_name = new Label("Account Name");
		Label acct_pswrd = new Label("Password");
//		GridPane.setHalignment(acct_name, HPos.RIGHT);
		//	leftSide.setHgap(5);
		//	leftSide.setAlignment(Pos.CENTER_RIGHT);
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
//		all.setTop(songButtons);
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
	}
	
	// function used in LoginButtonListener to verify the user exists in the UserList
	private User verifyUser(String username, String password) {
		  
		int pswrd = Integer.parseInt(password);
		  
		for (int i = 0; i < userList.size(); i++){
			if ((userList.get(i).getID().equals(username)) && (userList.get(i).getPassword() == pswrd))
				userList.get(i).setNumLogins(userList.get(i).getNumLogins()+1);
				return userList.get(i); 
		}
		return null;
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LoginButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			
			currentUser = verifyUser(acct_name_input, acct_password_input);
			if (currentUser != null){
				login_response.setText("Hello! " + acct_name_input);
				num_login_attempts = 0;
				clearInputs();
				System.out.println("***** Num. times logged in:  " + currentUser.getNumLogins() + "  *****");
			}
			else {
				num_login_attempts++;
				login_response.setText("Incorrect username or password. Try again.");
				System.out.println("***** Num. attempts left:  " + (3-num_login_attempts) + "  *****");
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
	
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}