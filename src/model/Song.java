package model;
// Added to allow package model to exist on GitHub
public class Song {
	private String file_path;
	private int num_times_played;
	
	public Song () {
		file_path =  "";
		num_times_played = 0;
	}
	public Song (String fp) {
		file_path = "songfiles/" + fp;
		num_times_played = 0;
	}
	
	public String getFilePath() {
		return this.file_path;
	}
	public int getNumTimesPlayed() {
		return this.num_times_played;
	}
}