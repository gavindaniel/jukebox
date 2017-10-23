package model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SongLibrary extends TableView<Song> {

//	song_library.setEditable(false);
	// create table columns
	@SuppressWarnings("unchecked")
	public SongLibrary() {
		TableColumn <Song, Integer> playsColumn = new TableColumn<>("Plays");
			playsColumn.setMaxWidth(50);
			playsColumn.setMinWidth(50);
			playsColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("numTimesPlayed"));
		TableColumn <Song, String> titleColumn = new TableColumn<>("Title");
			titleColumn.setMaxWidth(150);
			titleColumn.setMinWidth(150);
			titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		TableColumn <Song, String> artistColumn = new TableColumn<>("Artist");
			artistColumn.setMaxWidth(150);
			artistColumn.setMinWidth(150);
			artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		TableColumn <Song, String> timeColumn = new TableColumn<>("Time");
			timeColumn.setMaxWidth(60);
			timeColumn.setMinWidth(60);
			timeColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));
		// add columns to table
		this.getColumns().addAll(playsColumn,titleColumn,artistColumn,timeColumn);
	}
}
