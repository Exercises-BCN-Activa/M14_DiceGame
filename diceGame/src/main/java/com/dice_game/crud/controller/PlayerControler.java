package com.dice_game.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.implementation.PlayerService;

@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api/player")
public class PlayerControler {
	
	@Autowired
	private PlayerService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createPlayer(@RequestBody PlayerJson playerJson) {
		return service.createOne(playerJson);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response findPlayer(@RequestBody PlayerJson playerJson) {
		return service.readOne(playerJson);
	}
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public Response findAllPlayers(@RequestBody PlayerJson adminPlayerJson) {
		return service.readAll(adminPlayerJson);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response updatePlayer(@RequestBody PlayerJson playerJson) {
		return service.updateOne(playerJson);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('USER')")
	public Response deletePlayer(@RequestBody PlayerJson playerJson) {
		return service.deleteOne(playerJson);
	}

	@DeleteMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteAllPlayers(@RequestBody PlayerJson adminPlayerJson) {
		return service.deleteAll(adminPlayerJson);
	}
	
}
