package com.dice_game.crud.dto;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Main DTO class, allows users to register and offers login and access control mechanisms
 * 
 * @author FaunoGuazina
 *
 */
@Document(collection = "player")
@JsonIgnoreProperties(value={ "password", "roles", "status" }, allowSetters=true)  //don't show fields in get requests
public class Player {

	@Id
	private String _id;
	
	private String username;
	
	private String password;
	
	private String roles = "USER"; //establish basic role for every user

	private String name;

	private Date register = new Date(System.currentTimeMillis()); //establish date of creation for every user

	private Double status;

	public Player() {}  //standard and empty constructor

	public String getName() {
		return name;
	}

	public Date getRegister() {
		return register;
	}
	
	public Double getStatus() {
		return status;
	}
	
	//virtual getter to show formated "status" 
	public String getScore() {
		return (status==null) ? "never played" : status + "% victories";
	}

	public String get_id() {
		return _id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles.toUpperCase();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setRegister() {
		if (register == null) {
			register = new Date(System.currentTimeMillis());
		}
	}

	//if empty or null configures the name as anonymous
	public void setName(String name) {
		this.name = (name == null || name.isEmpty()) ? "anonymous" : name;
	}

	//set the status value from the user's number of games
	public void setStatus(List<Dice> rounds) {
		status = (rounds.isEmpty()) ? 0							// if have no dices in the list return zero
						: Double.parseDouble(					// parse to Double because decimal format return string
							new DecimalFormat("#.##").format(	// create new decimal format to adjust de decimal status
							(double) rounds.stream()			// casting to double (count method return long) and stream the list of dices
							.filter(x -> x.getStatus() == true)	// filter out only won games
							.count() 							// then return the amount of the won dices 
							/ rounds.size() * 100)				// then divide by number total of dices
							.replaceAll(",", "."));				// then replace the commas to dots
	}

	@Override
	public String toString() {
		return "'Player' : {'_id' : '" + _id + "'"
				+ ", 'username' : '" + username + "'" 
				+ ", 'name' : '" + name + "'"
				+ ", 'register' : '" + register + "'" 
				+ ", 'status' : '" + status + "'}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, username, name, register, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		return Objects.equals(_id, other._id) && Objects.equals(name, other.name)
				&& Objects.equals(register, other.register) && Objects.equals(username, other.username);
	}
	
	
	
}
