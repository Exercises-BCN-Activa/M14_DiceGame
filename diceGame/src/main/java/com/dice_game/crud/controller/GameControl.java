package com.dice_game.crud.controller;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dice_game.crud.dto.Player;
import com.dice_game.crud.dto.Dice;
import com.dice_game.crud.service.DiceService;
import com.dice_game.crud.service.PlayerService;

@RestController
@RequestMapping("/api")
public class GameControl {
	

	@Autowired
	private PlayerService PLAYER;

	@Autowired
	private DiceService DICE;


	@PostMapping("/players")
	public Player saveOne(@RequestBody Player player) {

		boolean verifyName = true;

		if (player.getName() != null && !player.getName().equalsIgnoreCase("unknown")
				&& !player.getName().equalsIgnoreCase("anonymous")) {

			for (Player p : PLAYER.readAll()) {

				if (p.getName().equalsIgnoreCase(player.getName())) {

					verifyName = false;
				}
			}
		}

		return (verifyName) ? PLAYER.saveOne(player) : null;
	}

	@GetMapping("/players")
	public List<Player> readAll() {

		return PLAYER.readAll();
	}

	@GetMapping("/players/{id}")
	public Player readOne(@PathVariable(name = "id") String id) {
		
		return PLAYER.readOne(id);
	}

	@PutMapping("/players/{id}")
	public Player updateOne(@PathVariable(name = "id") String id, @RequestBody Player pUpdate) {

		Player player = null;
		
		boolean ok = false;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		if (player != null && pUpdate != null) {
			if (pUpdate.getName() != null && !pUpdate.getName().isEmpty()
					&& !pUpdate.getName().equals(player.getName())) {

				player.setName(pUpdate.getName());
				
				ok = true;
			}

		}

		return (ok) ? PLAYER.updateOne(player) : null;
	}

	@DeleteMapping("/players/{id}")
	public void deleteOne(@PathVariable(name = "id") String id) {

		DICE.readByPlayer(id).stream().forEach(x -> DICE.delete(x));

		PLAYER.deleteOne(id);
	}

	@PostMapping("/players/{id}/play")
	public Dice createOne(@PathVariable(name = "id") String id) {

		Player player = null;
		Dice dice = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		if (player != null) {
			dice = DICE.saveOne(new Dice(player));
			player.setStatus(DICE.readByPlayer(player.get_id()));
			PLAYER.updateOne(player);
		}

		return (player == null && dice == null) ? null : dice;
	}

	@GetMapping("/players/{id}/play")
	public List<Dice> readAllPlays(@PathVariable(name = "id") String id) {

		Player player = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}
		
		return (player!=null) ? DICE.readByPlayer(player.get_id()) : null;
	}

	@DeleteMapping("/players/{id}/play")
	public void deleteAllPlays(@PathVariable(name = "id") String id) {

		Player player = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		DICE.readByPlayer(player.get_id()).stream().forEach(x -> DICE.delete(x));
		player.setStatus(DICE.readByPlayer(player.get_id()));
		PLAYER.updateOne(player);

	}

	@GetMapping("/players/ranking")
	public String rankingValue() {

		List<Dice> plays = DICE.readAll();
		
		Double wins = (double) plays.stream().filter(x->x.getStatus()==true).count();

		return new DecimalFormat("#.##").format(wins / plays.size()*100) + "%";
	}
	
	@GetMapping("/players/ranking/all")
	public List<Player> rankingAll() {
		
		List<Player> theList = PLAYER.readAll().stream()
									.filter(x -> x.getStatus() != null)
									.collect(Collectors.toList());
		
		theList.sort(Comparator.comparing(Player::getStatus).reversed());
		
		return theList;
		
	}
	
	@GetMapping("/players/ranking/winner")
	public List<Player> rankingWinner() {
		
		List<Player> theList = rankingAll();
		
		return theList.stream()
				.filter(x -> x.getStatus()==theList.get(0).getStatus())
				.collect(Collectors.toList());
		
	}
	
	@GetMapping("/players/ranking/loser")
	public List<Player> rankingLoser() {
		
		List<Player> theList = rankingAll();
		
		return theList.stream()
				.filter(x -> x.getStatus()==theList.get(theList.size()-1).getStatus())
				.collect(Collectors.toList());
		
	}

}
