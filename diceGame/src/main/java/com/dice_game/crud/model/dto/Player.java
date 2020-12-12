package com.dice_game.crud.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dice_game.crud.model.json.PlayerJson;
import com.dice_game.crud.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "player")
@JsonIgnoreProperties(value = "password", allowSetters = true)
public final class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date registration;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String type;

	private float status;

	@OneToMany(orphanRemoval = true, targetEntity = Dice.class, mappedBy = "player", cascade = CascadeType.REMOVE)
	private List<Dice> rounds;

	public Player() {
	}

	public PlayerJson toJson() {
		PlayerJson playerJson = new PlayerJson();

		playerJson.setId(id);
		playerJson.setEmail(email);
		playerJson.setRegistration(registration);
		playerJson.setFullName(firstName, lastName);
		playerJson.setStatus(getStatus());

		return playerJson;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(Role type) {
		this.type = type.getRole();
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public float getStatus() {
		setStatus();
		return status;
	}

	public void setStatus() {
		this.status = (rounds == null || rounds.isEmpty()) ? 0 : fromRounds();

	}

	private float fromRounds() {
		return (float) rounds.stream().filter(x -> x.isWon()).count() / rounds.size() * 100;
	}

	public List<Dice> getRounds() {
		return rounds;
	}

	public void setRounds(List<Dice> rounds) {
		this.rounds = rounds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, registration, email, firstName, lastName, type);
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
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(registration, other.registration) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [id=");
		builder.append(id);
		builder.append(", registration=");
		builder.append(registration);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", type=");
		builder.append(type);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
