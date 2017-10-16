package DoIt;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoItTest {

	@Test
	public void testToMaxDoits() {
		DoIt logins = new DoIt();
		assertTrue(logins.canDoIt());
		assertEquals(0, logins.timesDone());
		logins.doIt();
		assertTrue(logins.canDoIt());
		assertEquals(1, logins.timesDone());
		logins.doIt();
		assertFalse(logins.canDoIt());
		assertEquals(2, logins.timesDone());
	}
	
	@Test
	public void testToMaxDitForTomorrow() {
		DoIt logins = new DoIt();
		logins.doIt();
		logins.doIt();
		assertFalse(logins.canDoIt());
		logins.pretendItsTomorrow();
		logins.doIt();
	}
}
