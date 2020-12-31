package com.dice_game.crud.utilities;

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
	
	public static boolean isEmpty(Number something) {
		return something == null || something.longValue() <= 0;
	}
	
	public static boolean noEmpty(String something) {
		return !StringUtils.isEmpty(something);
	}
	
	public static boolean noEmpty(Number something) {
		return something != null && something.longValue() > 0;
	}
	
	public static String TitleCase(String toConvert) {
		return TitleCase.all(toConvert);
	}

	public static String encryptPassword(String toEncrypt) {
		return new BCryptPasswordEncoder().encode(toEncrypt);
	}
	
	public static boolean encryptMatches(String rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}
	
	public static boolean isValidEmail(String email) {
		String front = "[\\p{L}\\p{N}!#$%&'*+/=?^_`{|}~-]+";
		String back = "[\\p{L}\\p{N}](?:[a-z0-9-]*[\\p{L}\\p{N}]";
		String domin = String.format("(?:%s)?.)+%s)?", back, back);
		String regex = String.format("%s(?:.%s)*@%s", front, front, domin);
		return noEmpty(email) && email.matches(regex);
	}
	private Util() {
	}

}
