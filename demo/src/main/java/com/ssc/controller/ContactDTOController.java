package com.ssc.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssc.demo.error.handler.ContactNotFoundException;
import com.ssc.facade.ContactFacade;
import com.ssc.model.dto.ContactDTO;

@RestController
@Validated
public class ContactDTOController {
	
	@Autowired 
	ContactFacade contactFacade;
	
	
	@GetMapping("/contacts")
    public List<ContactDTO> getAllContacts( ) {
		
		List<ContactDTO> it =contactFacade.getAllContacts();;
		it.forEach(System.out::println);
    	return it;
    }
	
	@GetMapping("/contacts/{id}")
    public ResponseEntity<ContactDTO> getContactsById(@PathVariable Long id ) {
		
		ContactDTO contactDTO=  contactFacade.getContactById(id);
		
		if(contactDTO==null) {
			 throw new ContactNotFoundException("id-" + id);
		 }
		return ResponseEntity.ok().body(contactDTO);
    }
	
	@PostMapping(value="/contacts")
	public ResponseEntity<Object>  createContact(@Valid @RequestBody ContactDTO dto) {
	
		ContactDTO contactDTO = null;
		try {
			contactDTO = contactFacade.createContact(dto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.accepted().body(contactDTO);
	}
	

	@PutMapping(value="/contacts/{id}")
	public ResponseEntity<Object> updateContact(@Valid @RequestBody ContactDTO contactDto,@PathVariable("id")Long id ){		
		
		return ResponseEntity.ok().body(contactFacade.updateContact(id, contactDto));
		
	}
	
	@DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id ) {
		
		contactFacade.delete(id);
		return  ResponseEntity.noContent().build();
    }
	
}
