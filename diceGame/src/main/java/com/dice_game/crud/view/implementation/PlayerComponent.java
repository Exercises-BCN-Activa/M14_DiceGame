package com.dice_game.crud.view.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import static com.dice_game.crud.utilities.Util.*;

import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@Service
final class PlayerComponent {

	@Autowired
	private static PlayerDAO DAO;
	
	static Player findPlayerByEmail(String email) throws PlayerServImplException {
		Optional<Player> player = DAO.findByEmail(email);
		if (!player.isPresent())
			PlayerServImplException.throwsUp("This player does not exist!");
		return player.get();
	}
	
	static Player findPlayerByID(Long id) throws PlayerServImplException {
		Optional<Player> player = DAO.findById(id);
		if (!player.isPresent())
			PlayerServImplException.throwsUp("This player does not exist!");
		return player.get();
	}

	static boolean exists(PlayerJson playerJson) {
		return DAO.existsByEmail(playerJson.getEmail());
	}

	static PlayerJson savePlayerByJsonReturnJson(PlayerJson playerJson) {
		Player playerToSave = playerJson.toPlayer();

		Player playerSaved = DAO.save(playerToSave);

		return playerSaved.toJson();
	}

	static User validSpringUserToLoad(Player player) {
		return new User(player.getEmail(), player.getPassword(), listAuthorities(player));
	}

	private static List<GrantedAuthority> listAuthorities(Player player) {
		return Arrays.stream(player.getType().split(","))
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x))
				.collect(Collectors.toList());
	}

	static void ifNotHaveAllNeededToBeCreatedThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		if (notHaveAllNeededToBeCreated(playerJson))
			throwExceptionWithEspecificFlawsOfThis(playerJson);
	}
	
	static boolean notHaveAllNeededToBeCreated(PlayerJson playerJson) {
		return isEmpty(playerJson.getEmail()) || isEmpty(playerJson.getPassword()) 
				|| isEmpty(playerJson.getFirstName()) || isEmpty(playerJson.getLastName());
	}
	
	static PlayerServImplException throwExceptionWithEspecificFlawsOfThis(PlayerJson playerJson) {
		String message = "";

		if (isEmpty(playerJson.getEmail()))
			message.concat("Missing E-mail! ");

		if (isEmpty(playerJson.getPassword()))
			message.concat("Missing Password! ");

		if (isEmpty(playerJson.getFirstName()))
			message.concat("Missing First Name! ");

		if (isEmpty(playerJson.getLastName()))
			message.concat("Missing Last Name! ");

		return PlayerServImplException.throwsUp(message.trim());
	}

	static void ifEmailIsInvalidFormatThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		if (!isValidEmail(playerJson.getEmail()))
			throw PlayerServImplException.throwsUp("This email has invalid format!");
	}

	static void ifEmailIsAlreadyRegisteredThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		if (exists(playerJson))
			throw PlayerServImplException.throwsUp("This email is already registered!");
	}

}
