package model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * File: SongLibrary.java
 * 
 * Holds the library of songs to be picked from and queued
 * 
 * @author Abdullah Asaad, Gavin Daniel
 *
 */
public class SongLibrary extends TableView<Song> {

	/*
	 * functionality: Constructor for SongLibrary Parameters: ~
	 */
	@SuppressWarnings("unchecked")
	public SongLibrary() {
		// create table columns and add data
		// Number of Plays Column
		TableColumn<Song, Integer> playsColumn = new TableColumn<>("Plays");
		playsColumn.setMaxWidth(50);
		playsColumn.setMinWidth(50);
		playsColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("numTimesPlayed"));
		// Title Column
		TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMaxWidth(150);
		titleColumn.setMinWidth(150);
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		// Artist column
		TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
		artistColumn.setMaxWidth(150);
		artistColumn.setMinWidth(150);
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		// Time column
		TableColumn<Song, String> timeColumn = new TableColumn<>("Time");
		timeColumn.setMaxWidth(60);
		timeColumn.setMinWidth(60);
		timeColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("durationInMins"));
		// add columns to table
		this.getColumns().addAll(playsColumn, titleColumn, artistColumn, timeColumn);
		this.setMaxWidth(412);
	}
}
