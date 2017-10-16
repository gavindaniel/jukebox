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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
// Added by Gavin
import model.User;

public class JukeboxStartGUI extends Application {
  
	// TextFields for User input
	private TextField name_input;
	private TextField pswrd_input;
	// Buttons for the Login Screen
	private Button login_button;
	private Button logout_button;
	private Button song1_button;
	private Button song2_button;
	// List of Users 
	private ArrayList<User> userList;
	
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
		
		song1_button = new Button("Select song 1");
		song2_button = new Button("Select song 2");
		topBar.setAlignment(Pos.CENTER);
		topBar.add(song1_button, 0, 0);
		topBar.add(song2_button, 1, 0);
		
		Label acct_name = new Label("Account Name");
		Label acct_pswrd = new Label("Password");
		leftSide.add(acct_name, 0, 0);
		leftSide.add(acct_pswrd, 0, 1);
	//	acct_name.setAlignment(Pos.CENTER_RIGHT);
	//	leftSide.setHgap(5);
	//	leftSide.setAlignment(Pos.CENTER_RIGHT);
		
		name_input = new TextField();
		pswrd_input = new TextField();
		login_button = new Button("Login");
		Label login_response = new Label("Hello!");
		logout_button = new Button("Logout");
		rightSide.add(name_input, 0, 0);
		rightSide.add(pswrd_input, 0, 1);
		rightSide.add(login_button, 0, 2);
		rightSide.add(login_response, 0, 3);
		rightSide.add(logout_button, 0, 4);
		
		all.setTop(topBar);
		all.setLeft(leftSide);
		all.setRight(rightSide);
		
		Scene scene = new Scene(all, 300, 200);
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
		userList.add(new User("Merlin",77777,true));
	}
	
	// called from the start to add all listeners to the model
	private void registerListeners() {
	//	searchBar.textProperty().addListener(new SearchBarListener());
		login_button.setOnAction(new LoginButtonListener());
	}
	
	// function used in LoginButtonListener to verify the user exists in the UserList
	private boolean verifyUser(String username, String password) {
		  
		int pswrd = Integer.parseInt(password);
		  
		for (int i = 0; i < userList.size(); i++){
			if ((userList.get(i).getID().equals(username)) && (userList.get(i).getPassword() == pswrd))
				return true;
			else 
				return false;	  
		}
		return false;
	}
	
	// Button Listener for Login button and calls verifyUser to see if the User exists in the ArrayList
	private class LoginButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String acct_name_input = name_input.getText();
			String acct_password_input = pswrd_input.getText();
			
			if (verifyUser(acct_name_input, acct_password_input)){
				System.out.println("User -> " + acct_name_input + " EXISTS");
			}
			else {
				System.out.println("Incorrect username or password. Try again.");
			}
			System.out.println("*************************");
		}
	}
	/*************** END of CLASS : JukeBoxStartGUI ****************/  
}