package com.dice_game.crud.model.json;

import java.util.Objects;

import com.dice_game.crud.model.dto.Dice;

public final class DiceJson {

	private Long id;

	private String registration;

	private Integer value1;

	private Integer value2;

	private boolean status;

	public DiceJson() {
	}

	private DiceJson(Dice dice) {
		this.id = dice.getId();
		this.registration = dice.getRegistration().toString();
		this.value1 = dice.getValue1();
		this.value2 = dice.getValue2();
		this.status = dice.isWon();
	}

	public static DiceJson from(Dice dice) {
		return new DiceJson(dice);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, registration, status, value1, value2);
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
		DiceJson other = (DiceJson) obj;
		return Objects.equals(id, other.id) && Objects.equals(registration, other.registration)
				&& status == other.status && Objects.equals(value1, other.value1)
				&& Objects.equals(value2, other.value2);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dicejson [id=");
		builder.append(id);
		builder.append(", registration=");
		builder.append(registration);
		builder.append(", value1=");
		builder.append(value1);
		builder.append(", value2=");
		builder.append(value2);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
