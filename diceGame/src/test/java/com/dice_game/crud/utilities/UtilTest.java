package com.dice_game.crud.utilities;

import static com.dice_game.crud.utilities.Util.encryptMatches;
import static com.dice_game.crud.utilities.Util.encryptPassword;
import static com.dice_game.crud.utilities.Util.isNullOrEmpty;
import static com.dice_game.crud.utilities.Util.isNullOrLessThanOne;
import static com.dice_game.crud.utilities.Util.msgError;
import static com.dice_game.crud.utilities.Util.notNullOrEmpty;
import static com.dice_game.crud.utilities.Util.notNullOrLessThanOne;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UtilTest {
	
	@Test
	@DisplayName("Standard Error Msg with Input Details")
	void testMsgError() {
		String input = "create Player";

		assertDoesNotThrow(() -> msgError(input), assertError("Does Not Throw 1"));

		String msgError = msgError(input);

		assertTrue(msgError.contains(input), assertError("True 1"));
	}

	@Test
	@DisplayName("If string is null or empty")
	void testIsEmptyString() {
		String msgTest1 = null;
		String msgTest2 = "";
		String msgTest3 = "Everything is OK!";
		
		assertAll(
				() -> assertDoesNotThrow(() -> isNullOrEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> isNullOrEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> isNullOrEmpty(msgTest3), assertError("Does Not Throw 3"))
				);
		
		Boolean test1 = isNullOrEmpty(msgTest1);
		Boolean test2 = isNullOrEmpty(msgTest2);
		Boolean test3 = isNullOrEmpty(msgTest3);
		
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
				() -> assertDoesNotThrow(() -> isNullOrLessThanOne(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> isNullOrLessThanOne(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> isNullOrLessThanOne(msgTest3), assertError("Does Not Throw 3")),
				() -> assertDoesNotThrow(() -> isNullOrLessThanOne(msgTest4), assertError("Does Not Throw 4")),
				() -> assertDoesNotThrow(() -> isNullOrLessThanOne(msgTest5), assertError("Does Not Throw 5"))
				);
		
		Boolean test1 = isNullOrLessThanOne(msgTest1);
		Boolean test2 = isNullOrLessThanOne(msgTest2);
		Boolean test3 = isNullOrLessThanOne(msgTest3);
		Boolean test4 = isNullOrLessThanOne(msgTest4);
		Boolean test5 = isNullOrLessThanOne(msgTest5);
		
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
				() -> assertDoesNotThrow(() -> notNullOrEmpty(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> notNullOrEmpty(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> notNullOrEmpty(msgTest3), assertError("Does Not Throw 3"))
				);
		
		Boolean test1 = notNullOrEmpty(msgTest1);
		Boolean test2 = notNullOrEmpty(msgTest2);
		Boolean test3 = notNullOrEmpty(msgTest3);
		
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
				() -> assertDoesNotThrow(() -> notNullOrLessThanOne(msgTest1), assertError("Does Not Throw 1")),
				() -> assertDoesNotThrow(() -> notNullOrLessThanOne(msgTest2), assertError("Does Not Throw 2")),
				() -> assertDoesNotThrow(() -> notNullOrLessThanOne(msgTest3), assertError("Does Not Throw 3")),
				() -> assertDoesNotThrow(() -> notNullOrLessThanOne(msgTest4), assertError("Does Not Throw 4")),
				() -> assertDoesNotThrow(() -> notNullOrLessThanOne(msgTest5), assertError("Does Not Throw 5"))
				);
		
		Boolean test1 = notNullOrLessThanOne(msgTest1);
		Boolean test2 = notNullOrLessThanOne(msgTest2);
		Boolean test3 = notNullOrLessThanOne(msgTest3);
		Boolean test4 = notNullOrLessThanOne(msgTest4);
		Boolean test5 = notNullOrLessThanOne(msgTest5);
		
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
				() -> assertFalse(Util.isValidEmail(""), assertError("False 4")),
				() -> assertFalse(Util.isValidEmail(null), assertError("False 5")),
				() -> assertFalse(Util.isValidEmail(".@somewhere.com"), assertError("False 6"))
				);

	}
	
	String assertError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
