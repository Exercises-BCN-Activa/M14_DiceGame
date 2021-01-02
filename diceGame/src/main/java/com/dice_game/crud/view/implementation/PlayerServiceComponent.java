package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.noEmpty;
import static com.dice_game.crud.utilities.exceptions.PlayerServImplException.throwsUp;
import static com.dice_game.crud.view.implementation.PlayerJsonUpdateValidation.cloneStructureSetWhatIsUpgradeable;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifIsInvalidEmailThrowException;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifIsInvalidNumberIdThrowException;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifNotHaveIdAndEmailThrowException;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifPasswordsNotMachesThrowException;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifPlayerIsNotPresentThrowException;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.newValidUserBy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@Component
final class PlayerServiceComponent {

	@Autowired
	private final PlayerDAO DAO;
	
	void ifEmailIsAlreadyRegisteredThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		
		if (existsByEmail(playerJson.getEmail()))
			throwsUp("This email is already registered!");
	}
	
	private boolean existsByEmail(String email) {
		return DAO.existsByEmail(email);
	}
	
	PlayerJson savePlayerByJsonReturnJson(PlayerJson playerJson) {
		Player playerToSave = playerJson.toPlayer();
		Player playerSaved = DAO.save(playerToSave);
		return playerSaved.toJson();
	}

	Player findPlayerByEmailOrId(PlayerJson playerJson) {
		ifNotHaveIdAndEmailThrowException(playerJson);
		Player player = (noEmpty(playerJson.getId())) 
							? findPlayerByID(playerJson.getId())
							: findPlayerByEmail(playerJson.getEmail());
		return player;
	}

	Player findPlayerByEmail(String email) throws PlayerServImplException {
		ifIsInvalidEmailThrowException(email);
		Optional<Player> player = DAO.findByEmail(email);
		ifPlayerIsNotPresentThrowException(player);
		return player.get();
	}
	
	Player findPlayerByID(Long id) throws PlayerServImplException {
		ifIsInvalidNumberIdThrowException(id);
		Optional<Player> player = DAO.findById(id);
		ifPlayerIsNotPresentThrowException(player);
		return player.get();
	}

	List<PlayerJson> findAllPlayers() {
		return DAO.findAll().parallelStream().map(Player::toJson).collect(Collectors.toList());
	}

	PlayerJson updatePlayerByIdIfMeetRequirements(PlayerJson playerJson) throws PlayerServImplException {
		Player oldPlayer = findPlayerByID(playerJson.getId());
		Player toUpdate = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		if (oldPlayer.equals(toUpdate))
			PlayerServImplException.throwsUp("It looks like there's nothing to update!");
		Player updatedPlayer = DAO.save(toUpdate);
		return updatedPlayer.toJson();
	}
	
	void deleteEspecificPlayerIfWasUser(PlayerJson playerJson) throws PlayerServImplException {
		Player player = findPlayerByEmailOrId(playerJson);
		DAO.delete(player);
		if (existsByEmail(player.getEmail()))
			throwsUp("I don't know what to say, but it seems that this player doesn't die!");
		
	}
	
	void ifPasswordDoesNotMatchThrowException(PlayerJson playerJson) throws PlayerServImplException {
		Player player = findPlayerByEmail(playerJson.getEmail());
		ifPasswordsNotMachesThrowException(playerJson.getPassword(), player.getPassword());
		
	}

	void deleteAllPlayersWhoHaveRoleUser() throws PlayerServImplException {
		List<Player> beforeDelete = listAllPlayersUsers();
		
		if (beforeDelete.isEmpty())
			throwsUp("It seems that there are no players users in the database!");
		else
			beforeDelete.stream().forEach(player -> DAO.delete(player));
		
		List<Player> afterDelete = listAllPlayersUsers();
		
		if (!afterDelete.isEmpty())
			throwsUp("It seems that players users still remain in database!");
	}
	
	private List<Player> listAllPlayersUsers(){
		return DAO.findAll().parallelStream()
				.filter(play -> play.getType().equals(Role.BASIC.getRole()))
				.collect(Collectors.toList());
	}

	User validSpringUserToLoad(String email) throws PlayerServImplException {
		Player player = findPlayerByEmail(email);
		return newValidUserBy(player);
	}

	PlayerServiceComponent(PlayerDAO dAO) {
		DAO = dAO;
	}

}
