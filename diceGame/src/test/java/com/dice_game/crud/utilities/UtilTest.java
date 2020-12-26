package com.dice_game.crud.utilities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import static com.dice_game.crud.utilities.Util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UtilTest {
	
	private HashMap<String, Object> map;

	@Test
	@DisplayName("Standard Error Msg with Input Details")
	void testMsgError() {
		String input = "create Player";

		assertDoesNotThrow(() -> msgError(input), assertError("Does Not Throw 1"));

		String msgError = msgError(input);

		assertTrue(msgError.contains(input), assertError("True 1"));
	}

	@Test
	@DisplayName("New Map with Success Message and Object")
	void testSuccessMap() {
		Boolean testObject = true;
		String msgTest = "Everything is OK!";
		
		assertDoesNotThrow(() -> successMap(msgTest, testObject), assertError("Does Not Throw 1"));
		
		map = successMap(msgTest, testObject);
		
		assertAll(
				() -> assertNotNull(map, assertError("Not Null 1")),
				() -> assertTrue(map.size() == 3, assertError("True 1 - Size should be 3")),
				() -> assertEquals(true, map.get("success"), assertError("Equals 1 - Should be True")),
				() -> assertEquals(msgTest, map.get("message"), assertError("Equals 2 - Should be: " + msgTest)),
				() -> assertEquals(testObject, map.get("content"), assertError("Equals 3 - Should be: " + testObject))
				);
	}

	@Test
	@DisplayName("New Map with Error Message")
	void testErrorMap() {
		String msgTest = "Everything is NOT OK!";
		
		assertDoesNotThrow(() -> errorMap(msgTest), assertError("Does Not Throw 1"));
		
		map = errorMap(msgTest);
		
		assertAll(
				() -> assertNotNull(map, assertError("Not Null 1")),
				() -> assertTrue(map.size() == 2, assertError("True 1 - Size should be 2")),
				() -> assertEquals(false, map.get("success"), assertError("Equals 1 - Should be True")),
				() -> assertEquals(msgTest, map.get("message"), assertError("Equals 2 - Should be: " + msgTest))
				);
	}

	@Test
	@DisplayName("If string is null or empty")
	void testIsEmptyString() {
		String msgTest1 = null;
		String msgTest2 = "";
		String msgTest3 = "Everything is OK!";
		
		assertAll(
				() -> assertDoesNotThrow(() -> isEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest3), assertError("Does Not Throw 3"))
				);
		
		Boolean test1 = isEmpty(msgTest1);
		Boolean test2 = isEmpty(msgTest2);
		Boolean test3 = isEmpty(msgTest3);
		
		assertAll(
				() -> assertTrue(test1, assertError("True 1")),
				() -> assertTrue(test2, assertError("True 2")),
				() -> assertFalse(test3, assertError("False 1"))
				);
	}
	
	@Test
	@DisplayName("If string is null or empty")
	void testIsEmptyNumber() {
		Long msgTest1 = null;
		Long msgTest2 = 0l;
		Long msgTest3 = 1l;
		long msgTest4 = 0;
		long msgTest5 = 1;

		assertAll(
				() -> assertDoesNotThrow(() -> isEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest3), assertError("Does Not Throw 3")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest4), assertError("Does Not Throw 4")),
				() -> assertDoesNotThrow(() -> isEmpty(msgTest5), assertError("Does Not Throw 5"))
				);
		
		Boolean test1 = isEmpty(msgTest1);
		Boolean test2 = isEmpty(msgTest2);
		Boolean test3 = isEmpty(msgTest3);
		Boolean test4 = isEmpty(msgTest4);
		Boolean test5 = isEmpty(msgTest5);
		
		assertAll(
				() -> assertFalse(test3, assertError("False 1")),
				() -> assertFalse(test5, assertError("False 2")),
				() -> assertTrue(test1, assertError("True 1")),
				() -> assertTrue(test2, assertError("True 2")),
				() -> assertTrue(test4, assertError("True 3"))
				);
	}

	@Test
	@DisplayName("If string is NOT null and NOT empty")
	void testNoEmptyString() {
		String msgTest1 = null;
		String msgTest2 = "";
		String msgTest3 = "Everything is OK!";

		assertAll(
				() -> assertDoesNotThrow(() -> noEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest3), assertError("Does Not Throw 3"))
				);
		
		Boolean test1 = noEmpty(msgTest1);
		Boolean test2 = noEmpty(msgTest2);
		Boolean test3 = noEmpty(msgTest3);
		
		assertAll(
				() -> assertNotNull(test1, assertError("Not Null 1")),
				() -> assertNotNull(test2, assertError("Not Null 2")),
				() -> assertNotNull(test3, assertError("Not Null 3")),
				() -> assertFalse(test1, assertError("False 1")),
				() -> assertFalse(test2, assertError("False 2")),
				() -> assertTrue(test3, assertError("True 1"))
				);
	}
	
	@Test
	@DisplayName("If string is NOT null and NOT empty")
	void testNoEmptyNumber() {
		Long msgTest1 = null;
		Long msgTest2 = 0l;
		Long msgTest3 = 1l;
		long msgTest4 = 0;
		long msgTest5 = 1;

		assertAll(
				() -> assertDoesNotThrow(() -> noEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest3), assertError("Does Not Throw 3")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest4), assertError("Does Not Throw 4")),
				() -> assertDoesNotThrow(() -> noEmpty(msgTest5), assertError("Does Not Throw 5"))
				);
		
		Boolean test1 = noEmpty(msgTest1);
		Boolean test2 = noEmpty(msgTest2);
		Boolean test3 = noEmpty(msgTest3);
		Boolean test4 = noEmpty(msgTest4);
		Boolean test5 = noEmpty(msgTest5);
		
		assertAll(
				() -> assertTrue(test3, assertError("True 1")),
				() -> assertTrue(test5, assertError("True 2")),
				() -> assertFalse(test1, assertError("False 1")),
				() -> assertFalse(test2, assertError("False 2")),
				() -> assertFalse(test4, assertError("False 3"))
				);
	}

	@Test
	@DisplayName("Title Case To Strings")
	void testTitleCase() {
		String input = "   TEST tEsT, (TEST.TEST), <<teSt<<, teSt_tEst,   mc.beTh, hU-zIn-xun,   d'marcO'aires, tes!te, tESt.. tEsT!! TEST??? 'test' `test` 'test' \"test\"   ";
		String test = Util.TitleCase(input);
		String toCompare = "Test Test, (Test.Test), <<Test<<, Test_Test, Mc.Beth, Hu-Zin-Xun, D'Marco'Aires, Tes!Te, Test.. Test!! Test??? 'Test' `Test` 'Test' \"Test\"";
		assertAll(
				() -> assertNotNull(test, assertError("Not Null 1")),
				() -> assertNotEquals(test, input, assertError("Not Equals 1")),
				() -> assertEquals(toCompare, test, assertError("Equals 1"))
				);
	}

	@Test
	@DisplayName("Encriptation of Passwords")
	void testEncryptPassword() {
		String password = "senha2020";
		BCryptPasswordEncoder encrypter = new BCryptPasswordEncoder();
		
		assertDoesNotThrow(() -> encryptPassword(password), assertError("Does Not throw 1"));
		
		String encryptPassword = encryptPassword(password);
		
		assertAll(
				() -> assertNotNull(encryptPassword, assertError("Not Null 1")),
				() -> assertNotEquals(encryptPassword, password, assertError("Not Equals 1")),
				() -> assertTrue(encrypter.matches(password, encryptPassword), assertError("True 1")),
				() -> assertFalse(encrypter.matches("senha444", encryptPassword), assertError("True 1"))
				);
	}
	
	@Test
	@DisplayName("If Matches Passwords Raw and Encoded")
	void testEncryptMatches() {
		BCryptPasswordEncoder encrypter = new BCryptPasswordEncoder();
		String password = "senha2020";
		String passwordToTeste = encrypter.encode(password);
		
		assertAll(
				() -> assertDoesNotThrow(() -> encryptMatches(password, passwordToTeste), assertError("Does Not Throw 1")),
				() -> assertTrue(encryptMatches(password, passwordToTeste), assertError("True 1")),
				() -> assertFalse(encryptMatches("senha4444", passwordToTeste), assertError("False 1"))
				);
	}

	@Test
	@DisplayName("Validation of Emails")
	void testIsEmail() {
		assertAll(
				() -> assertTrue(Util.isValidEmail("john@somewhere.com"), assertError("True 1")),
				() -> assertTrue(Util.isValidEmail("john.foo@somewhere.com"), assertError("True 2")),
				() -> assertTrue(Util.isValidEmail("john.foo+label@somewhere.com"), assertError("True 3")),
				() -> assertTrue(Util.isValidEmail("john@192.168.1.10"), assertError("True 4")),
				() -> assertTrue(Util.isValidEmail("john+label@192.168.1.10"), assertError("True 5")),
				() -> assertTrue(Util.isValidEmail("john.foo@someserver"), assertError("True 6")),
				() -> assertTrue(Util.isValidEmail("JOHN.FOO@somewhere.com"), assertError("True 7")),
				() -> assertFalse(Util.isValidEmail("@someserver"), assertError("False 1")),
				() -> assertFalse(Util.isValidEmail("@someserver.com"), assertError("False 2")),
				() -> assertFalse(Util.isValidEmail("john@."), assertError("False 3")),
				() -> assertFalse(Util.isValidEmail(".@somewhere.com"), assertError("False 4"))
				);

	}
	
	String assertError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
