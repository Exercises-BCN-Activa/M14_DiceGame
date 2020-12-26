package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.encryptMatches;
import static com.dice_game.crud.utilities.Util.isEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;
import static com.dice_game.crud.utilities.exceptions.PlayerServImplException.throwsUp;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

final class PlayerServiceValidations {
	
	static User newValidUserBy(Player player) {
		return new User(player.getEmail(), player.getPassword(), listAuthorities(player));
	}

	static Set<GrantedAuthority> listAuthorities(Player player) {
		return Arrays.stream(arrayRoles(player))
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x))
				.collect(Collectors.toSet());
	}
	
	static String[] arrayRoles(Player player) {
		return player.getType().replaceAll("( )+", "").split(",");
	}
	
	static void ifPasswordsNotMachesThrowException(String rawPassword, String encodedPassword)
			throws PlayerServImplException {

		if (!encryptMatches(rawPassword, encodedPassword))
			throwsUp("This email is already registered!");
	}

	static void ifNotHaveIdAndEmailThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		if (isEmpty(playerJson.getId()) && isEmpty(playerJson.getEmail()))
			throwsUp("Do not have email and ID for consultation!");
	}
	
	static void ifIsInvalidNumberIdThrowException(Long id) 
			throws PlayerServImplException {
		if (isEmpty(id))
			throwsUp("This ID does not have a valid format!");
	}
	
	static void ifIsInvalidEmailThrowException(String email) 
			throws PlayerServImplException {
		if (!isValidEmail(email))
			throwsUp("This email does not have a valid format!");
	}
	
	static void ifPlayerIsNotPresentThrowException(Optional<Player> player) 
			throws PlayerServImplException {
		if (!player.isPresent())
			throwsUp("This player does not exist!");
	}

}
