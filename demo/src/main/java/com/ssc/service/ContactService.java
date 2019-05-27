package com.ssc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssc.demo.error.handler.ContactNotFoundException;
import com.ssc.jpa.repository.AddressRepository;
import com.ssc.jpa.repository.ContactDetailRepository;
import com.ssc.jpa.repository.ContactRepository;
import com.ssc.jpa.repository.PhoneRepository;
import com.ssc.model.Contact;

@Component
public class ContactService {

	
	@Autowired
    private ContactRepository contactRepository;
	@Autowired
    private AddressRepository addressRepository;
	@Autowired
    private ContactDetailRepository contactDetailRepository;
	@Autowired
    private PhoneRepository phoneRepository;
	
	public Optional<Contact> getContactById(Long id) {
		
		return contactRepository.findById(id);

	}
	
	public Iterable<Contact> getAllContacts(){
		return contactRepository.findAll();
	}

	public void createContact(Contact contact) {
		contactRepository.save(contact);
		
	}

	public void updateContact(Contact contact) {
		contactRepository.save(contact);
		
	}

	public void delete(Long id) {

		Optional<Contact> contact = contactRepository.findById(id);
		if(!contact.isPresent())
			throw new ContactNotFoundException("id-"+id);
		
		contactRepository.delete(contact.get());
	}
}
