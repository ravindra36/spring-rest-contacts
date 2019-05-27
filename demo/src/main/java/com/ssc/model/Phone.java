package com.ssc.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name="PHONES")
public class Phone {

	@Id
	@GeneratedValue
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;

	@NotEmpty(message="Please provide Phone Number")	
	private String number;
	
	@Enumerated(EnumType.STRING)
	private PhoneType type;
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "contact_id")
	private Contact contact;
	
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		return "Phone [id=" + id + ", number=" + number + ", type=" + type + "]";
	}
}
