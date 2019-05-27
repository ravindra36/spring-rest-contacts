package com.ssc.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ContactDTO {

	
	@NotEmpty(message="Please provide email")
	@Email(message = "Email should be valid")

	private String email;
	
    @Valid
	private ContactDetailDTO name;
	
	
    
    @Valid
	private AddressDTO address;
	
	
   
    @Valid
	private List<PhoneDTO> phone;
    
    public ContactDTO( String email, ContactDetailDTO name, AddressDTO address, ArrayList<PhoneDTO> phone) {
    	this.email=email;
    	this.name=name;
    	this.address=address;
    	this.phone=phone;
    }
    
    public ContactDTO() {
    	
    }
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public ContactDetailDTO getName() {
		return name;
	}
	public void setName(ContactDetailDTO name) {
		this.name = name;
	}
	
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	
	public List<PhoneDTO> getPhone() {
		return phone;
	}
	public void setPhone(List<PhoneDTO> phone) {
		this.phone = phone;
	}

}
