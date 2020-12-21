package com.dice_game.crud.view.implementation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dto.PlayerJson;

import static com.dice_game.crud.utilities.Util.successMap;
import static com.dice_game.crud.utilities.Util.errorMap;
import static com.dice_game.crud.utilities.Util.msgError;
import com.dice_game.crud.view.service.PlayerService;

@Service
public final class PlayerImplementation implements PlayerService, UserDetailsService {
	
	@Autowired
	private final PlayerServiceComponent DAO;
	
	PlayerImplementation(PlayerServiceComponent playerServiceComponent) {
		DAO = playerServiceComponent;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {

			return DAO.validSpringUserToLoad(email);

		} catch (Exception e) {
			throw new UsernameNotFoundException(email);
		}
	}

	@Override
	public Map<String, Object> createOne(PlayerJson playerJson) {
		try {
			
			PlayerJsonAspectValidation.verifyIsAbleToSave(playerJson);
			
			DAO.ifEmailIsAlreadyRegisteredThrowException(playerJson);

			PlayerJson savedToSend = DAO.savePlayerByJsonReturnJson(playerJson);

			return successMap("Player successfully created", savedToSend);

		} catch (Exception e) {
			return errorMap(msgError("create Player").concat(e.getMessage()));
		}
	}


	@Override
	public Map<String, Object> readAll(String password) {
		try {

			return successMap(null, null);
		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> readOne(PlayerJson playerJson) {
		try {

			return successMap(null, null);
		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> updateOne(PlayerJson playerJson) {
		try {

			return successMap(null, null);

		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> updateAll(String password) {
		try {

			return successMap(null, null);

		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> deleteOne(PlayerJson playerJson) {
		try {

			return successMap(null, null);

		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> deleteAll(String password) {
		try {

			return successMap(null, null);

		} catch (Exception e) {
			return errorMap("Something went wrong when creating the player: ".concat(e.getMessage()));
		}
	}

}
