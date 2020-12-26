package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.errorMap;
import static com.dice_game.crud.utilities.Util.msgError;
import static com.dice_game.crud.utilities.Util.successMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.view.service.PlayerDetailService;

@Service
public final class PlayerService implements PlayerDetailService, UserDetailsService {
	
	@Autowired
	private final PlayerServiceComponent service;

	@Override
	public Map<String, Object> createOne(PlayerJson playerJson) {
		try {
			
			PlayerJsonToSaveValidation.verifyIsAble(playerJson);
			
			service.ifEmailIsAlreadyRegisteredThrowException(playerJson);

			PlayerJson saved = service.savePlayerByJsonReturnJson(playerJson);

			return successMap("Player successfully created", saved);

		} catch (Exception e) {
			return errorMap(msgError("create Player").concat(e.getMessage()));
		}
	}


	@Override
	public Map<String, Object> readAll(PlayerJson AdminEmailAndPassword) {
		try {
			
			service.ifPasswordDoesNotMatchThrowException(AdminEmailAndPassword);
			
			List<PlayerJson> listOfAllPlayers = service.findAllPlayers();
			
			return successMap("List of All Players in DataBase", listOfAllPlayers);
			
		} catch (Exception e) {
			return errorMap(msgError("list all Players").concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> readOne(PlayerJson playerJson) {
		try {

			PlayerJson requested = service.findPlayerByEmailOrId(playerJson).toJson();
			
			return successMap("Player successfully finded", requested);
			
		} catch (Exception e) {
			return errorMap(msgError("find a Player").concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> updateOne(PlayerJson playerJson) {
		try {
			
			PlayerJson updated = service.updatePlayerIfMeetRequirements(playerJson);

			return successMap("Player updated successfully", updated);

		} catch (Exception e) {
			return errorMap(msgError("update a Player").concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> deleteOne(PlayerJson playerJson) {
		try {
			
			service.deleteEspecificPlayerIfWasUser(playerJson);

			return successMap("The Player was correctly deleted", null);

		} catch (Exception e) {
			return errorMap(msgError("delete a Player").concat(e.getMessage()));
		}
	}

	@Override
	public Map<String, Object> deleteAll(PlayerJson AdminEmailAndPassword) {
		try {
			
			service.ifPasswordDoesNotMatchThrowException(AdminEmailAndPassword);
			
			service.deleteAllPlayersWhoHaveRoleUser();

			return successMap("All users have been deleted", null);

		} catch (Exception e) {
			return errorMap(msgError("delete all users").concat(e.getMessage()));
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {

			return service.validSpringUserToLoad(email);

		} catch (Exception e) {
			throw new UsernameNotFoundException(email);
		}
	}
	
	PlayerService(PlayerServiceComponent playerServiceComponent) {
		service = playerServiceComponent;
	}

}
