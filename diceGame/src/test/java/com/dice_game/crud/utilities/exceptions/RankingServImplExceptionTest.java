package com.dice_game.crud.utilities.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankingServImplExceptionTest {

	@Test
	@DisplayName("Exception is throw")
	void testThrowsUp() {
		assertThrows(RankingServImplException.class, () -> RankingServImplException.throwsUp("test"));
	}

}
