package com.dice_game.crud.model.dto;

import java.util.Date;
import java.util.Objects;

public final class DiceJson {

	private Long id;

	private Date registration;

	private Integer value1;

	private Integer value2;

	private boolean status;
	
	protected DiceJson() {
	}

	private DiceJson(Dice dice) {
		setId(dice.getId());
		setRegistration(dice.getRegistration());
		setValue1(dice.getValue1());
		setValue2(dice.getValue2());
		setStatus(dice.isWon());
	}

	public static DiceJson from(Dice dice) {
		return new DiceJson(dice);
	}

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public Date getRegistration() {
		return registration;
	}

	void setRegistration(Date registration) {
		this.registration = registration;
	}

	public Integer getValue1() {
		return value1;
	}

	void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public boolean isStatus() {
		return status;
	}

	void setStatus(boolean status) {
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
