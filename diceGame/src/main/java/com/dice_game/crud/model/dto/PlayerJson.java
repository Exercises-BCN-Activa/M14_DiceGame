package com.dice_game.crud.model.dto;

import java.util.Date;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.dice_game.crud.security.Role;
import static com.dice_game.crud.utilities.Util.encryptPassword;
import static com.dice_game.crud.utilities.Util.TitleCase;
import static com.dice_game.crud.utilities.Util.notNullOrEmpty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "password", "firstName", "lastName" }, allowSetters = true)
public final class PlayerJson {

	private Long id;

	private String firstName;

	private String lastName;

	private String fullName;

	private String email;

	private String password;

	private Date registration;

	private String status;

	public PlayerJson() {
	}

	private PlayerJson(Player player) {
		id = player.getId();
		email = player.getEmail();
		registration = player.getRegistration();
		setFullName(player.getFirstName(), player.getLastName());
		setStatus(player.status());
	}

	public static PlayerJson from(Player player) {
		return new PlayerJson(player);
	}

	public Player toPlayer() {

		Player player = new Player();

		player.setEmail(email);
		player.setFirstName(TitleCase(firstName));
		player.setLastName(TitleCase(lastName));
		player.setType(Role.BASIC);
		if (notNullOrEmpty(password))
			player.setPassword(encryptPassword(password));

		return player;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		if (StringUtils.isEmpty(fullName)) {
			setFullName(firstName, lastName);
		}
		return fullName;
	}

	void setFullName(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
		this.fullName = String.format("%s, %s", lastName, firstName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistration() {
		return registration;
	}

	void setRegistration(Date registration) {
		this.registration = registration;
	}

	public String getStatus() {
		return status;
	}

	void setStatus(float status) {
		this.status = (status == 0) ? "never played" : status + "% victories";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, fullName, registration, status);
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
		PlayerJson other = (PlayerJson) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(registration, other.registration)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerJson [id=");
		builder.append(id);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", registration=");
		builder.append(registration);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
