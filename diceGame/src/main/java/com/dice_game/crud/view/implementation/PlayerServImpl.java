package com.dice_game.crud.view.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.view.service.PlayerService;

@Service
public final class PlayerServImpl implements PlayerService, UserDetailsService {
	
	@Autowired
	private PlayerDAO dao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		try {
			
			Player player = dao.findByEmail(email).get();
			
			List<GrantedAuthority> authorities = Arrays.stream(player.getType().split(","))
					.map(x -> new SimpleGrantedAuthority("ROLE_" + x)).collect(Collectors.toList());
			
			return new User(player.getEmail(), player.getPassword(), authorities);
			
		} catch (Exception e) {
			
			System.out.println("Player Not Founded!!! " + e.getMessage());
			throw new UsernameNotFoundException(email);
		}
	
	}

	@Override
	public Map<String, Object> createOne(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readAll(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readOne(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateOne(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateAll(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteOne(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteAll(String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
