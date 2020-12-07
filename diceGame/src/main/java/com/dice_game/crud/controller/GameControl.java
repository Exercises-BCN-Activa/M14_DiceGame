package com.dice_game.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * control class of all API endpoints
 * 
 * @author FaunoGuazina
 *
 */
@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api")
public class GameControl {

//	@Autowired
//	private PlayerService playerService;
//
//	@Autowired
//	private DiceService diceService;
//	
//	/**
//	 * Method to create a player in database
//	 * 
//	 * @param player to save in database
//	 * @return saved player or null if the player does not have a username and password
//	 */
//	@PostMapping("/players")
//	public Player saveOne(@RequestBody Player player) {
//
//		// if player have a username and password set true
//		boolean verify = (player.getUsername() == null || player.getUsername().isEmpty()
//								|| player.getPassword() == null || player.getPassword().isEmpty())
//									? false : true;
//		if (verify) {
//			
//			//appeals to all database users to comparing username
//			for (Player p : playerService.readAll()) {
//				
//				if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
//					
//					verify = false;
//				}
//			}
//			
//			//if username is released and has password, set encryption
//			player.setPassword((verify) ? crypt.encode(player.getPassword()) : null);
//		}
//
//		return (verify) ? playerService.saveOne(player) : null;
//	}
//
//	/**
//	 * Method to read all players in database
//	 * 
//	 * @return all players in database
//	 */
//	@GetMapping("/players")
//	public List<Player> readAll() {
//
//		return playerService.readAll();
//	}
//
//	/**
//	 * Method to read a player in database by username
//	 * 
//	 * @param username string
//	 * @return one player
//	 */
//	@GetMapping("/players/{username}")
//	public Player readOne(@PathVariable(name = "username") String username) {
//		
//		return playerService.readUsername(username);
//	}
//
//	/**
//	 * Method to update a player in database
//	 * 
//	 * @param username
//	 * @param pUpdate
//	 * @return
//	 */
//	@PutMapping("/players/{username}")
//	public Player updateOne(@PathVariable(name = "username") String username, @RequestBody Player pUpdate) {
//
//		Player player = null;
//		
//		boolean ok = false;
//
//		try {
//			
//			player = playerService.readUsername(username);
//
//		} catch (Exception e) {
//			System.out.println("Player Not Founded!!!");
//		}
//
//		if (player != null && pUpdate != null) {
//			
//			if (pUpdate.getName() != null && !pUpdate.getName().isEmpty()
//					&& !pUpdate.getName().equals(player.getName())) {
//
//				player.setName(pUpdate.getName());
//				
//				ok = true;
//			}
//			
//			if (pUpdate.getUsername() != null && !pUpdate.getUsername().isEmpty()
//					&& !pUpdate.getUsername().equals(player.getUsername())) {
//				
//				player.setUsername(pUpdate.getUsername());
//				
//				ok = true;
//			}
//			
//			if (pUpdate.getPassword() != null && !pUpdate.getPassword().isEmpty()
//					&& !pUpdate.getPassword().equals(player.getPassword())) {
//				
//				player.setPassword(pUpdate.getPassword());
//				
//				ok = true;
//			}
//
//		}
//
//		return (ok) ? playerService.updateOne(player) : null;
//	}
//
//	/**
//	 * Method to delete a player in database
//	 * 
//	 * @param id string
//	 */
//	@PreAuthorize("hasRole('ADMIN')")
//	@DeleteMapping("/players/{id}")
//	public void deleteOne(@PathVariable(name = "id") String id) {
//		
//		Player player = null;
//
//		try {
//			player = playerService.readOne(id);
//
//		} catch (Exception e) {
//			System.out.println("Player Not Founded!!!");
//		}
//
//		//erases all player dice games
//		diceService.readByPlayer(player).stream().forEach(x -> diceService.delete(x));
//
//		//then delete a player
//		playerService.deleteOne(player.get_id());
//	}
//
//	/**
//	 * Method to create a dice game to a player in database
//	 * 
//	 * @param username string
//	 * @return the dice game
//	 */
//	@PostMapping("/players/{username}/play")
//	public Dice createOne(@PathVariable(name = "username") String username) {
//
//		Player player = null;
//		Dice dice = null;
//
//		try {
//			player = playerService.readUsername(username);
//
//		} catch (Exception e) {
//			System.out.println("Player Not Founded!!!");
//		}
//
//		//if there is a player
//		if (player != null) {
//			dice = diceService.saveOne(new Dice(player));		//create a new game
//			player.setStatus(diceService.readByPlayer(player));	//changes the player's status
//			playerService.updateOne(player);					//updates the player with new status
//		}
//
//		return (player == null && dice == null) ? null : dice;
//	}
//
//	/**
//	 * Method to read all dice games of a player in database
//	 * 
//	 * @param username
//	 * @return
//	 */
//	@GetMapping("/players/{username}/play")
//	public List<Dice> readAllPlays(@PathVariable(name = "username") String username) {
//
//		Player player = null;
//
//		try {
//			player = playerService.readUsername(username);
//
//		} catch (Exception e) {
//			System.out.println("Player Not Founded!!!");
//		}
//		
//		//if there is a player, return the list
//		return (player==null) ? null : diceService.readByPlayer(player);
//	}
//
//	/**
//	 * Method to delet all dice games of a player in database
//	 * 
//	 * @param username
//	 */
//	@PreAuthorize("hasRole('ADMIN')")
//	@DeleteMapping("/players/{username}/play")
//	public void deleteAllPlays(@PathVariable(name = "username") String username) {
//
//		Player player = null;
//
//		try {
//			player = playerService.readUsername(username);
//
//		} catch (Exception e) {
//			System.out.println("Player Not Founded!!!");
//		}
//		
//		//if there is a player
//		if (player != null) {
//			diceService.readByPlayer(player).stream()
//						.forEach(x -> diceService.delete(x));	//lambda to delete all games
//			player.setStatus(diceService.readByPlayer(player));	//changes the player's status
//			playerService.updateOne(player);					//updates the player with new status
//		}
//	}
//
//	/**
//	 * Method to calculate the ranking value of all dice games in database
//	 * 
//	 * @return
//	 */
//	@GetMapping("/players/ranking")
//	public String rankingValue() {
//
//		List<Dice> plays = diceService.readAll();			//collect a list of games
//		
//		Double wins = (double) plays.stream()
//				.filter(x->x.getStatus()==true).count();	//counts how many games are winners
//
//		return new DecimalFormat("#.##")					//returns a formatted string with sum of winners
//				.format(wins / plays.size()*100) + "%";			//divided by the number of games
//	}
//	
//	/**
//	 * Method that orders players based on their rank values
//	 * 
//	 * @return
//	 */
//	@GetMapping("/players/ranking/all")
//	public List<Player> rankingAll() {
//		
//		//collect all players who have played a game
//		List<Player> theList = playerService.readAll().stream()
//									.filter(x -> x.getStatus() != null)
//									.collect(Collectors.toList());
//		
//		//organizes the list in descending order
//		theList.sort(Comparator.comparing(Player::getStatus).reversed());
//		
//		return theList;
//		
//	}
//	
//	/**
//	 * Method that returns all players who are in the first position
//	 * 
//	 * @return
//	 */
//	@GetMapping("/players/ranking/winner")
//	public List<Player> rankingWinner() {
//		
//		List<Player> theList = rankingAll();
//		
//		return theList.stream()
//				.filter(x -> x.getStatus()==theList.get(0).getStatus())
//				.collect(Collectors.toList());
//		
//	}
//	
//	/**
//	 * Method that returns all players who are in the last position
//	 * 
//	 * @return
//	 */
//	@GetMapping("/players/ranking/loser")
//	public List<Player> rankingLoser() {
//		
//		List<Player> theList = rankingAll();
//		
//		return theList.stream()
//				.filter(x -> x.getStatus()==theList.get(theList.size()-1).getStatus())
//				.collect(Collectors.toList());
//		
//	}

}
