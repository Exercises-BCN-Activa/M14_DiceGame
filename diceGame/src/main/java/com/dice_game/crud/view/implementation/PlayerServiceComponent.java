package com.dice_game.crud.view.implementation;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;

import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@Component
final class PlayerServiceComponent {

	@Autowired
	private final PlayerDAO DAO;
	
	
	PlayerServiceComponent(PlayerDAO dAO) {
		DAO = dAO;
	}

	Player findPlayerByEmail(String email) throws PlayerServImplException {
		Optional<Player> player = DAO.findByEmail(email);
		if (!player.isPresent())
			PlayerServImplException.throwsUp("This player does not exist!");
		return player.get();
	}
	
	Player findPlayerByID(Long id) throws PlayerServImplException {
		Optional<Player> player = DAO.findById(id);
		if (!player.isPresent())
			PlayerServImplException.throwsUp("This player does not exist!");
		return player.get();
	}

	boolean existsByEmail(String email) {
		return DAO.existsByEmail(email);
	}

	PlayerJson savePlayerByJsonReturnJson(PlayerJson playerJson) {
		Player playerToSave = playerJson.toPlayer();

		Player playerSaved = DAO.save(playerToSave);

		return playerSaved.toJson();
	}
	
	void ifEmailIsAlreadyRegisteredThrowException(PlayerJson playerJson) throws PlayerServImplException {
		
		if (existsByEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("This email is already registered!");
	}

	User validSpringUserToLoad(String email) {
		Player player = findPlayerByEmail(email);
		return new User(player.getEmail(), player.getPassword(), listAuthorities(player));
	}

	private static Set<GrantedAuthority> listAuthorities(Player player) {
		return Arrays.stream(arrayRoles(player))
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x))
				.collect(Collectors.toSet());
	}
	
	private static String[] arrayRoles(Player player) {
		return player.getType().replaceAll("( )+", "").split(",");
	}

}
