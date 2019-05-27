package com.ssc.facade;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssc.demo.error.handler.ContactNotFoundException;
import com.ssc.model.Contact;
import com.ssc.model.Phone;
import com.ssc.model.dto.ContactDTO;
import com.ssc.model.dto.PhoneDTO;
import com.ssc.service.ContactService;

@Component
public class ContactFacade {

	@Autowired
	private ContactService contactService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public ContactDTO getContactById(Long id){
		 Optional<Contact> contact = contactService.getContactById(id);
		 if(contact.isPresent()) {
			 return convertToDto(contact.get());
		 }
	     return null;
	  }


	public List<ContactDTO> getAllContacts() {
		
		List<ContactDTO> list= new ArrayList<ContactDTO>();
		Iterable<Contact> itList= contactService.getAllContacts();
		
		itList.forEach(contact->{
			
			ContactDTO dto =convertToDto(contact);
			list.add(dto);
		});
		
		return list;		
	}
	
	public ContactDTO createContact(ContactDTO dto) throws ParseException {
		
		/*
		contact.getAddress().setContact(contact);
    	
    	
		*/
		Contact contact = convertToEntity(dto);
		contact.getName().setContact(contact);
		contact.getAddress().setContact(contact);
    	//contact.getPhone().stream().forEach(x->x.setContact(contact));
		contactService.createContact(contact);
		return convertToDto(contact);
	
	}
	
	private ContactDTO convertToDto(Contact contact) {
		ContactDTO contactDto = modelMapper.map(contact, ContactDTO.class);
		//Additional setters 
	    return contactDto;
	}
	
	private Contact convertToEntity(ContactDTO contactDTO) throws ParseException {
	    Contact contact = modelMapper.map(contactDTO, Contact.class);
	    //Additional setters 
	    return contact;
	}


	public ContactDTO updateContact(Long id, ContactDTO contactDto) {
		
		Contact contact = modelMapper.map(contactDto, Contact.class);
		
		Optional<Contact> contactOptional = contactService.getContactById(id);
		if (!contactOptional.isPresent())
		      throw new ContactNotFoundException("id-" + id);
		Contact oldContact =  contactOptional.get();
		contact.setId(id);
		contact.getAddress().setContact(contact);
		contact.getAddress().setId(oldContact.getAddress().getId());
		contact.getName().setContact(contact);
		contact.getName().setId(oldContact.getName().getId());
		contactService.updateContact(contact);

		return contactDto;
	}


	public void delete(Long id) {

		contactService.delete(id);
	}

}
