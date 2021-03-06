package com.dice_game.crud.utilities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dice_game.crud.model.dto.PlayerJson;

class ResponseTest {
	
	private Response response;
	private String message;
	private final String CONTENT_ERROR = "This Response have no content!";
	private final String MESSAGE_ERROR = "Sorry, this Response has no message!";

	@Test
	@DisplayName("Error Response with Message")
	void test1_ResponseError() {
		message = "Something went wrong trying to Creating Player";
		response = Response.error(message);
		
		assertAll(
				() -> assertNotNull(response, msgError("Not Null 1")),
				() -> assertEquals(message, response.getMessage(), msgError("Equals 1")),
				() -> assertEquals(CONTENT_ERROR, response.getContent(), msgError("Equals 2")),
				() -> assertFalse(response.isSuccess(), msgError("False 1"))
				);
		
	}
	
	@Test
	@DisplayName("Error Response with Message")
	void test2_ResponseError() {
		response = Response.error(null);
		
		assertAll(
				() -> assertNotNull(response, msgError("Not Null 1")),
				() -> assertEquals(MESSAGE_ERROR, response.getMessage(), msgError("Equals 1")),
				() -> assertEquals(CONTENT_ERROR, response.getContent(), msgError("Equals 2")),
				() -> assertFalse(response.isSuccess(), msgError("False 1"))
				);
		
	}
	
	@Test
	@DisplayName("Success Response with Message")
	void testResponseSuccess() {
		message = "Everything went well to Creating Player";
		PlayerJson playerJson = new PlayerJson();
		response = Response.success(message, playerJson);
		
		assertAll(
				() -> assertNotNull(response, msgError("Not Null 1")),
				() -> assertEquals(message, response.getMessage(), msgError("Equals 1")),
				() -> assertEquals(playerJson, response.getContent(), msgError("Equals 2")),
				() -> assertTrue(response.isSuccess(), msgError("True 1"))
				);
	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
