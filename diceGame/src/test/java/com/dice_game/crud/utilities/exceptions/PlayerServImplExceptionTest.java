package com.dice_game.crud.utilities.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerServImplExceptionTest {

	@Test
	@DisplayName("Exception is thrown")
	void testThrowsUp() {
		assertThrows(PlayerServImplException.class, () -> PlayerServImplException.throwsUp("teste"));
	}

}
