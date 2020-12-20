package com.dice_game.crud.utilities;

import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

public final class Util {
	
	/**
	 * "Something went wrong trying to {@code input} : "
	 * 
	 * @param input string to complete the message
	 * @return string complete message
	 */
	public static String msgError(String input) {
		return String.format("Something went wrong trying to %s : ", input);
	}

	public static boolean isEmpty(String something) {
		return StringUtils.isEmpty(something);
	}
	
	public static boolean noEmpty(String something) {
		return !StringUtils.isEmpty(something);
	}
	
	public static String TitleCase(String toConvert) {
		return TitleCase.all(toConvert);
	}

	public static String encrypt(String toEncrypt) {
		return new BCryptPasswordEncoder().encode(toEncrypt);
	}
	
	public static boolean isValidEmail(String email) {
		String front = "[\\p{L}\\p{N}!#$%&'*+/=?^_`{|}~-]+";
		String back = "[\\p{L}\\p{N}](?:[a-z0-9-]*[\\p{L}\\p{N}]";
		String domin = String.format("(?:%s)?.)+%s)?", back, back);
		String regex = String.format("%s(?:.%s)*@%s", front, front, domin);
		return email.matches(regex);
	}

	public static HashMap<String, Object> successMap(String message, Object content) {
		map = new HashMap<>();
		map.put("success", true);
		map.put("message", message);
		map.put("content", content);
		return map;
	}

	public static HashMap<String, Object> errorMap(String message) {
		map = new HashMap<>();
		map.put("success", false);
		map.put("message", message);
		return map;
	}

	private Util() {
	}

	private static HashMap<String, Object> map;

}
