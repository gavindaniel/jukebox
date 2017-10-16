package DoIt;

import java.time.LocalDate;

public class DoIt {

	private LocalDate mostRecentDoIt;
	private int doIts;
	public final static int MAX = 2;
	
	
	public DoIt() {
		mostRecentDoIt = LocalDate.now().minusYears(40);
		doIts = 0;
	}
	public boolean canDoIt() {
		// TODO Auto-generated method stub
		return doIts < MAX;
	}

	public int timesDone() {
		// TODO Auto-generated method stub
		return doIts;
	}

	public void doIt() {
		// TODO Auto-generated method stub
		if (!canDoIt())
			return;
		else {
			LocalDate today = LocalDate.now();
			if (mostRecentDoIt.compareTo(today) < 0) {
				doIts = 1;
				mostRecentDoIt = today;
			}
			else {
				doIts++;
			}
		}
	}

}
