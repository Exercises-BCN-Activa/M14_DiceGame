package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.exceptions.DiceServImplException.throwsUp;
import static com.dice_game.crud.view.implementation.PlayerServiceValidations.ifPasswordsNotMachesThrowException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.exceptions.DiceServImplException;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@Component
class DiceServiceComponent {
	
	@Autowired
	private PlayerServiceComponent playerService;
	
	@Autowired
	private DiceDAO DAO;

	DiceJson createNewRoundToPlayerBy(PlayerJson playerJson) throws PlayerServImplException {
		
		Player player = findPlayerByJson(playerJson);
		
		Dice newDice = Dice.newRound(player);
		
		Dice savedDice = DAO.save(newDice);
		
		return savedDice.toJson();
	}
	
	private Player findPlayerByJson(PlayerJson playerJson) throws PlayerServImplException {
		
		Player player = playerService.findPlayerByEmailOrId(playerJson);
		
		return player;
	}

	List<DiceJson> reedAllRoundsOfPlayerBy(PlayerJson playerJson) 
			throws PlayerServImplException, DiceServImplException {
		
		Player player = findPlayerByJson(playerJson);
		
		List<Dice> listDice = collectListDiceByPlayer(player);
		
		List<DiceJson> listJson = convertListDiceToJson(listDice);
		
		return listJson;
	}
	
	private List<Dice> collectListDiceByPlayer(Player player) {
		List<Dice> listDice = DAO.findByPlayerId(player.getId());
		ifNoHaveDicesToDeleteThrowExceptions(listDice);
		return listDice;
	}
	
	private void ifNoHaveDicesToDeleteThrowExceptions(List<Dice> listDice) {
		boolean noHaveDiceToDeleted = listDice.isEmpty();
		if (noHaveDiceToDeleted)
			throwsUp("It seems that there is no dice to delete!");
		
	}

	private List<DiceJson> convertListDiceToJson(List<Dice> listDice) {
		return listDice.parallelStream()
				.map(dice -> dice.toJson()).collect(Collectors.toList());
	}

	boolean deleteAllRoundsOfPlayerBy(PlayerJson playerJson) 
			throws PlayerServImplException, DiceServImplException {
		
		Player player = findPlayerByJsonAndVerifyPassword(playerJson);
		
		List<Dice> listDice = collectListDiceByPlayer(player);
		
		DAO.deleteAll(listDice);
			
		return trueIfNoExistsAnyDiceElseThrowsException(listDice);
	}

	private Player findPlayerByJsonAndVerifyPassword(PlayerJson playerJson) throws PlayerServImplException {
		
		Player player = findPlayerByJson(playerJson);
		
		ifPasswordsNotMachesThrowException(playerJson.getPassword(), player.getPassword());
		
		return player;
	}
	
	private boolean trueIfNoExistsAnyDiceElseThrowsException(List<Dice> listDice) throws DiceServImplException {
		
		boolean stillExists = listDice.stream()
				.anyMatch(dice -> DAO.existsById(dice.getId()));
		
		if (stillExists)
			throwsUp("It seems this Player still have Rounds in database!");
		
		return true;
	}

	boolean ifIsAdminAndDeleteAllRoundsOfGame(PlayerJson playerJson) 
			throws PlayerServImplException, DiceServImplException {
		
		Player player = findPlayerByJsonAndVerifyPassword(playerJson);
		
		ifPlayerIsNotAdminThrowsExceptions(player);
		
		DAO.deleteAll();
		
		return trueIfAllErasedElseThrowException();
	}
	
	private boolean trueIfAllErasedElseThrowException() {
		
		boolean ifNotErased = DAO.findAll().isEmpty() ? false : true;
		
		if (ifNotErased)
			throwsUp("It looks like you don't have authorization for reset the game!");
		
		return true;
	}

	private void ifPlayerIsNotAdminThrowsExceptions(Player player) throws DiceServImplException {
		
		String playerAuth = player.getType();
		String accessLevel = Role.CHIEF.getRole();
		
		boolean playerIsNOTAdmin = (playerAuth.contains(accessLevel)) ? false : true;
		
		if (playerIsNOTAdmin)
			throwsUp("It looks like you don't have authorization for reset the game!");
	}

}
