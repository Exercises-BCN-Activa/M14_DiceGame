package com.dice_game.crud.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dice_game.crud.dao.IPlayer;
import com.dice_game.crud.dto.Player;

/**
 * Class that implements the use of the DAO class and allows queries in the database.
 * It also extends the UserDetailsService interface that allows users to login.
 * 
 * @author FaunoGuazina
 *
 */
@Service
public class PlayerService implements UserDetailsService, simpleCrud<Player> {
	
	@Autowired
	private IPlayer dao;

	@Override
	public Player saveOne(Player item) {
		return dao.save(item);
	}

	@Override
	public List<Player> readAll() {
		return dao.findAll();
	}

	@Override
	public Player readOne(String id) {
		return dao.findById(id).get();
	}

	@Override
	public Player updateOne(Player item) {
		return dao.save(item);
	}

	@Override
	public void deleteOne(String id) {
		dao.deleteById(id);
	}
	
	public Player readUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Player player;
		
		try {
			player = readUsername(username);
		} catch (Exception e) {
			System.out.println("Player Not Founded!!! " + e.getMessage());
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> authorities = Arrays.stream(player.getRoles().split(","))
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x)).collect(Collectors.toList());
		
		return new User(player.getUsername(), player.getPassword(), authorities);
	}

}
