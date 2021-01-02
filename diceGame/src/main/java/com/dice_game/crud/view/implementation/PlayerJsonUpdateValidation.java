package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.TitleCase;
import static com.dice_game.crud.utilities.Util.encryptMatches;
import static com.dice_game.crud.utilities.Util.encryptPassword;
import static com.dice_game.crud.utilities.Util.isNullOrEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;

import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;

final class PlayerJsonUpdateValidation {

	static Player cloneStructureSetWhatIsUpgradeable(Player oldPlayer, PlayerJson playerJson) {
		PlayerJsonUpdateValidation updater = new PlayerJsonUpdateValidation(oldPlayer, playerJson);
		updater.cloneStructuralAttributesFromOldPlayer();
		updater.compareAndSetUpdatableAttributes();
		updater.setUpgradeableAttributesToNewPlayer();
		return updater.getUpdatedPlayer();
	}

	private PlayerJsonUpdateValidation(Player oldPlayer, PlayerJson playerJson) {
		this.oldPlayer = oldPlayer;
		this.playerJson = playerJson;
		this.newPlayer = new Player();
	}

	private void cloneStructuralAttributesFromOldPlayer() {
		newPlayer.setId(oldPlayer.getId());
		newPlayer.setRegistration(oldPlayer.getRegistration());
		newPlayer.setType(oldPlayer.getType());
		newPlayer.setRounds(oldPlayer.getRounds());
		newPlayer.setStatus();
	}

	private void compareAndSetUpdatableAttributes() {
		firstName = checkNameShouldBe(playerJson.getFirstName(), oldPlayer.getFirstName());
		lastName = checkNameShouldBe(playerJson.getLastName(), oldPlayer.getLastName());
		email = checkPasswordShouldBe(playerJson.getPassword(), oldPlayer.getPassword());
		password = checkEmailShouldBe(playerJson.getEmail(), oldPlayer.getEmail());
	}

	private String checkNameShouldBe(String toVerify, String toCompare) {
		return (isNullOrEmpty(toVerify) || toVerify.equals(toCompare)) ? toCompare : TitleCase(toVerify);
	}

	private String checkPasswordShouldBe(String toVerify, String toCompare) {
		return (isNullOrEmpty(toVerify) || encryptMatches(toVerify, toCompare)) ? toCompare : encryptPassword(toVerify);
	}

	private String checkEmailShouldBe(String toVerify, String toCompare) {
		return (isValidEmail(toVerify) && !toVerify.equals(toCompare)) ? toVerify : toCompare;
	}

	private void setUpgradeableAttributesToNewPlayer() {
		newPlayer.setFirstName(firstName);
		newPlayer.setLastName(lastName);
		newPlayer.setPassword(email);
		newPlayer.setEmail(password);
	}

	private Player getUpdatedPlayer() {
		return newPlayer;
	}

	private final PlayerJson playerJson;
	private final Player oldPlayer;
	private final Player newPlayer;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
