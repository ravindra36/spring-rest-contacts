package com.ssc.model.dto;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ssc.model.Contact;

public class AddressDTO {
	
	public AddressDTO() {
		
	}
	
	public AddressDTO( String street,String city,String state,String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	@NotEmpty(message="Please provide Street")
	private String street;
	
	@NotEmpty(message="Please provide City")
	private String city;
	
	@NotEmpty(message="Please provide State")
	private String state;
	
	@NotEmpty(message="Please provide Zip")
	@Size(min=5, max=5)
	private String zip;
	
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
