package com.dice_game.crud.view.implementation;

import java.util.Arrays;
import java.util.List;
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
import com.dice_game.crud.security.Role;
import static com.dice_game.crud.utilities.Util.noEmpty;
import static com.dice_game.crud.utilities.Util.isEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;
import static com.dice_game.crud.utilities.Util.encryptMatches;
import static com.dice_game.crud.view.implementation.PlayerJsonUpdateValidation.cloneStructureSetWhatIsUpgradeable;

import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@Component
final class PlayerServiceComponent {

	@Autowired
	private final PlayerDAO DAO;
	
	void ifEmailIsAlreadyRegisteredThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		
		if (existsByEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("This email is already registered!");
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

	PlayerJson updatePlayerIfMeetRequirements(PlayerJson playerJson) throws PlayerServImplException {
		Player oldPlayer = findPlayerByID(playerJson.getId());
		Player toUpdate = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		if (oldPlayer.equals(toUpdate))
			PlayerServImplException.throwsUp("It looks like there's nothing to update!");
		Player newPlayer = DAO.save(toUpdate);
		return newPlayer.toJson();
	}
	
	void deleteEspecificPlayerIfWasUser(PlayerJson playerJson) {
		Player player = findPlayerByEmailOrId(playerJson);
		DAO.delete(player);
		if (existsByEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("I don't know what to say, but it seems that this player doesn't die!");
		
	}

	void deleteAllPlayersWhoHaveRoleUser() {
		List<Player> beforeDelete = DAO.findAll().parallelStream()
				.filter(play -> play.getType().equals(Role.BASIC.getRole()))
				.collect(Collectors.toList());
		
		if (!beforeDelete.isEmpty())
			beforeDelete.stream().forEach(play -> DAO.delete(play));
		else
			PlayerServImplException.throwsUp("It seems that there are no players users in the database!");
		
		List<Player> afterDelete = DAO.findAll().parallelStream()
				.filter(play -> play.getType().equals(Role.BASIC.getRole()))
				.collect(Collectors.toList());
		
		if (!afterDelete.isEmpty())
			PlayerServImplException.throwsUp("It seems that players users still remain in database!");
	}
	
	void ifPasswordDoesNotMatchThrowException(PlayerJson playerJson) {
		Player player = findPlayerByEmail(playerJson.getEmail());
		ifPasswordsNotMachesThrowException(playerJson.getPassword(), player.getPassword());
		
	}

	User validSpringUserToLoad(String email) throws PlayerServImplException {
		Player player = findPlayerByEmail(email);
		return new User(player.getEmail(), player.getPassword(), listAuthorities(player));
	}

	private static Set<GrantedAuthority> listAuthorities(Player player) {
		return Arrays.stream(arrayRoles(player))
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x))
				.collect(Collectors.toSet());
	}
	
	private static String[] arrayRoles(Player player) throws PlayerServImplException {
		return player.getType().replaceAll("( )+", "").split(",");
	}
	
	private void ifPasswordsNotMachesThrowException(String rawPassword, String encodedPassword)
			throws PlayerServImplException {

		if (!encryptMatches(rawPassword, encodedPassword))
			PlayerServImplException.throwsUp("This email is already registered!");
	}

	private void ifNotHaveIdAndEmailThrowException(PlayerJson playerJson) 
			throws PlayerServImplException {
		if (isEmpty(playerJson.getId()) && isEmpty(playerJson.getEmail()))
			PlayerServImplException.throwsUp("Do not have email and ID for consultation!");
	}
	
	private void ifIsInvalidNumberIdThrowException(Long id) 
			throws PlayerServImplException {
		if (isEmpty(id))
			PlayerServImplException.throwsUp("This ID does not have a valid format!");
	}
	
	private void ifIsInvalidEmailThrowException(String email) 
			throws PlayerServImplException {
		if (!isValidEmail(email))
			PlayerServImplException.throwsUp("This email does not have a valid format!");
	}
	
	private void ifPlayerIsNotPresentThrowException(Optional<Player> player) 
			throws PlayerServImplException {
		if (!player.isPresent())
			PlayerServImplException.throwsUp("This player does not exist!");
	}
	
	PlayerServiceComponent(PlayerDAO dAO) {
		DAO = dAO;
	}

}
