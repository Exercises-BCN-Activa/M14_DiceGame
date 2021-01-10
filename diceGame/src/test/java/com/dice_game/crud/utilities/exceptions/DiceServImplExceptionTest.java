package com.dice_game.crud.utilities.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiceServImplExceptionTest {

	@Test
	@DisplayName("Exception is throw")
	void testThrowsUp() {
		assertThrows(DiceServImplException.class, () -> DiceServImplException.throwsUp("test"));
	}

}
