package com.ssc.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import com.ssc.model.PhoneType;

public class PhoneDTO {


	@NotEmpty(message="Please provide Phone Number")	
	private String number;
	
	@Enumerated(EnumType.STRING)
	private PhoneType type;
	
	public PhoneDTO(String number, PhoneType phoneType) {
		this.number=number;
		this.type=phoneType;
	}
	
	public PhoneDTO() {
		
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public PhoneType getType() {
		return type;
	}
	public void setType(PhoneType type) {
		this.type = type;
	}
	
	
	@Override
	public String toString() {
		return "Phone [ number=" + number + ", type=" + type + "]";
	}

}
