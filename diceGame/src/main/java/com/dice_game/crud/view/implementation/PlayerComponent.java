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


}
