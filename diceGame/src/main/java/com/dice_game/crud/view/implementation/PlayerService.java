package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Response.error;
import static com.dice_game.crud.utilities.Response.success;
import static com.dice_game.crud.utilities.Util.msgError;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.service.PlayerDetailService;

@Service
public final class PlayerService implements PlayerDetailService, UserDetailsService {
	
	@Autowired
	private PlayerServiceComponent service;

	@Override
	public Response createOne(PlayerJson playerJson) {
		
		Response response = error(msgError("create Player"));
		
		try {

			PlayerJsonToSaveValidation.verifyIsAble(playerJson);
		
			service.ifEmailIsAlreadyRegisteredThrowException(playerJson);

			PlayerJson saved = service.savePlayerByJsonReturnJson(playerJson);

			response = success("Player successfully created", saved);

		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}


	@Override
	public Response readAll(PlayerJson AdminEmailAndPassword) {
		
		Response response = error(msgError("list all Players"));
		
		try {
			
			service.ifPasswordDoesNotMatchThrowException(AdminEmailAndPassword);
			
			List<PlayerJson> listOfAllPlayers = service.findAllPlayers();
			
			response = success("List of All Players in DataBase", listOfAllPlayers);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response readOne(PlayerJson playerJson) {
		
		Response response = error(msgError("find a Player"));
		
		try {

			PlayerJson requested = service.findPlayer(playerJson);
			
			response = success("Player successfully finded", requested);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response updateOne(PlayerJson playerJson) {
		
		Response response = error(msgError("update a Player"));
		
		try {
			
			PlayerJson updated = service.updatePlayerByIdIfMeetRequirements(playerJson);

			response = success("Player updated successfully", updated);

		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteOne(PlayerJson playerJson) {
		
		Response response = error(msgError("delete a Player"));
		
		try {
			
			service.deleteEspecificPlayerIfWasUser(playerJson);

			response = success("The Player was correctly deleted", null);

		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteAll(PlayerJson adminPlayerJson) {
		
		Response response = error(msgError("delete all users"));
		
		try {
			
			service.ifPasswordDoesNotMatchThrowException(adminPlayerJson);
			
			service.deleteAllPlayersWhoHaveRoleUser();

			response = success("All users have been deleted", null);

		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {

			return service.validSpringUserToLoad(email);

		} catch (Exception e) {
			throw new UsernameNotFoundException(email);
		}
	}

}
