package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {
	
	@Test
	public void newPosition() {
		Position position = new Position(3, 4);
		// '3' refers to row number (0 at 8-th rank). 3 becomes the 5-th rank)
		// '4' refers to the column. 4 becomes e (from index of 'e' in "abcdefgh")
		Assertions.assertEquals(position.toString(), "e5");
	}

}
