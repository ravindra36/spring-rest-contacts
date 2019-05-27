package com.ssc.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

@Entity(name="ADDRESSES")
public class Address {


	@Id
	@GeneratedValue
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;
	
	@NotEmpty(message="Please provide Street")
	private String street;
	@NotEmpty(message="Please provide City")
	private String city;
	@NotEmpty(message="Please provide State")
	private String state;
	@NotEmpty(message="Please provide Zip")
	@Size(min=5, max=5)
	private String zip;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
	@JoinColumn(name = "contact_id")
	private Contact contact;
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
