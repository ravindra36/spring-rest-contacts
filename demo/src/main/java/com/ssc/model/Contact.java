package com.ssc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name="CONTACTS")
public class Contact {
	
	
	@Id
	@GeneratedValue
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;
	
	@NotEmpty(message="Please provide email")
	@Email(message = "Email should be valid")

	private String email;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(mappedBy = "contact", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonManagedReference
    @Valid
	private ContactDetail name;
	
	@OneToOne(mappedBy = "contact", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
	
    @JsonManagedReference
    @Valid
	private Address address;
	
	//@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(
            name = "CONTACT_PHONE",
            joinColumns = @JoinColumn(name = "CONTACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PHONE_ID")
    )
    @JsonManagedReference
    @Valid
	private List<Phone> phone;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public ContactDetail getName() {
		return name;
	}
	public void setName(ContactDetail name) {
		this.name = name;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<Phone> getPhone() {
		return phone;
	}
	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}

	
}
