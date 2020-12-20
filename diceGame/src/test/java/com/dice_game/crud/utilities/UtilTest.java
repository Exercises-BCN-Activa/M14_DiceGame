package com.dice_game.crud.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UtilTest {
	
	private HashMap<String, Object> map;

	@Test
	@DisplayName("Standard Error Msg with Input Details")
	void testMsgError() {
		String input = "create Player";
		String msgError = Util.msgError(input);
		String shouldBe = "Something went wrong trying to create Player : ";
		
		assertAll(
				() -> assertNotNull(msgError, msgError("Not Null 1")),
				() -> assertTrue(msgError.contains(input), msgError("True 1")),
				() -> assertEquals(shouldBe, msgError, msgError("Equals 1"))
				);
	}

	@Test
	@DisplayName("New Map with Success Message and Object")
	void testSuccessMap() {
		Boolean testObject = true;
		String msgTest = "Everything is OK!";
		map = Util.successMap(msgTest, testObject);
		assertAll(
				() -> assertNotNull(map, msgError("Not Null 1")),
				() -> assertTrue(map.size() == 3, msgError("True 1 - Size should be 3")),
				() -> assertEquals(true, map.get("success"), msgError("Equals 1 - Should be True")),
				() -> assertEquals(msgTest, map.get("message"), msgError("Equals 2 - Should be: " + msgTest)),
				() -> assertEquals(testObject, map.get("content"), msgError("Equals 3 - Should be: " + testObject))
				);
	}

	@Test
	@DisplayName("New Map with Error Message")
	void testErrorMap() {
		String msgTest = "Everything is NOT OK!";
		map = Util.errorMap(msgTest);
		assertAll(
				() -> assertNotNull(map, msgError("Not Null 1")),
				() -> assertTrue(map.size() == 2, msgError("True 1 - Size should be 2")),
				() -> assertEquals(false, map.get("success"), msgError("Equals 1 - Should be True")),
				() -> assertEquals(msgTest, map.get("message"), msgError("Equals 2 - Should be: " + msgTest))
				);
	}

	@Test
	@DisplayName("If string is null or empty")
	void testIsEmpty() {
		String msgTest1 = null;
		String msgTest2 = "";
		String msgTest3 = "Everything is OK!";
		Boolean test1 = Util.isEmpty(msgTest1);
		Boolean test2 = Util.isEmpty(msgTest2);
		Boolean test3 = Util.isEmpty(msgTest3);
		assertAll(
				() -> assertNotNull(test1, msgError("Not Null 1")),
				() -> assertNotNull(test2, msgError("Not Null 2")),
				() -> assertNotNull(test3, msgError("Not Null 3")),
				() -> assertTrue(test1, msgError("True 1")),
				() -> assertTrue(test2, msgError("True 2")),
				() -> assertFalse(test3, msgError("False 1"))
				);
	}

	@Test
	@DisplayName("If string is NOT null and NOT empty")
	void testNoEmpty() {
		String msgTest1 = null;
		String msgTest2 = "";
		String msgTest3 = "Everything is OK!";
		Boolean test1 = Util.noEmpty(msgTest1);
		Boolean test2 = Util.noEmpty(msgTest2);
		Boolean test3 = Util.noEmpty(msgTest3);
		assertAll(
				() -> assertNotNull(test1, msgError("Not Null 1")),
				() -> assertNotNull(test2, msgError("Not Null 2")),
				() -> assertNotNull(test3, msgError("Not Null 3")),
				() -> assertFalse(test1, msgError("False 1")),
				() -> assertFalse(test2, msgError("False 2")),
				() -> assertTrue(test3, msgError("True 1"))
				);
	}

	@Test
	@DisplayName("Title Case To Strings")
	void testTitleCase() {
		String input = "   TEST tEsT, (TEST.TEST), <<teSt<<, teSt_tEst,   mc.beTh, hU-zIn-xun,   d'marcO'aires, tes!te, tESt.. tEsT!! TEST??? 'test' `test` 'test' \"test\"   ";
		String test = Util.TitleCase(input);
		String toCompare = "Test Test, (Test.Test), <<Test<<, Test_Test, Mc.Beth, Hu-Zin-Xun, D'Marco'Aires, Tes!Te, Test.. Test!! Test??? 'Test' `Test` 'Test' \"Test\"";
		assertAll(
				() -> assertNotNull(test, msgError("Not Null 1")),
				() -> assertNotEquals(test, input, msgError("Not Equals 1")),
				() -> assertEquals(toCompare, test, msgError("Equals 1"))
				);
	}

	@Test
	@DisplayName("Encriptation of Passwords")
	void testEncrypt() {
		String password = "senha2020";
		String passwordToTeste = Util.encrypt(password);
		BCryptPasswordEncoder encrypter = new BCryptPasswordEncoder();
		assertAll(
				() -> assertNotNull(passwordToTeste, msgError("Not Null 1")),
				() -> assertNotEquals(passwordToTeste, password, msgError("Not Equals 1")),
				() -> assertTrue(encrypter.matches(password, passwordToTeste), msgError("True 1"))
				);
	}

	@Test
	@DisplayName("Validation of Emails")
	void testIsEmail() {
		assertAll(
				() -> assertTrue(Util.isValidEmail("john@somewhere.com"), msgError("True 1")),
				() -> assertTrue(Util.isValidEmail("john.foo@somewhere.com"), msgError("True 2")),
				() -> assertTrue(Util.isValidEmail("john.foo+label@somewhere.com"), msgError("True 3")),
				() -> assertTrue(Util.isValidEmail("john@192.168.1.10"), msgError("True 4")),
				() -> assertTrue(Util.isValidEmail("john+label@192.168.1.10"), msgError("True 5")),
				() -> assertTrue(Util.isValidEmail("john.foo@someserver"), msgError("True 6")),
				() -> assertTrue(Util.isValidEmail("JOHN.FOO@somewhere.com"), msgError("True 7")),
				() -> assertFalse(Util.isValidEmail("@someserver"), msgError("False 1")),
				() -> assertFalse(Util.isValidEmail("@someserver.com"), msgError("False 2")),
				() -> assertFalse(Util.isValidEmail("john@."), msgError("False 3")),
				() -> assertFalse(Util.isValidEmail(".@somewhere.com"), msgError("False 4"))
				);

	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
