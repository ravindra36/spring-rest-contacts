package com.ssc.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssc.demo.error.handler.ContactNotFoundException;
import com.ssc.facade.ContactFacade;
import com.ssc.model.dto.ContactDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
@Api(value="Contacts")
@RequestMapping("/contacts")
public class ContactDTOController {
	
	@Autowired 
	ContactFacade contactFacade;
	
	@ApiOperation(value = "View a list of available contacts",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    		}
    )
	@GetMapping
    public List<ContactDTO> getAllContacts( ) {
		
		List<ContactDTO> it =contactFacade.getAllContacts();;
		it.forEach(System.out::println);
    	return it;
    }
	
	@ApiOperation(value = "View an existing contact by id",response = ContactDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    		}
    )
	@GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactsById(@PathVariable Long id ) {
		
		ContactDTO contactDTO=  contactFacade.getContactById(id);
		
		if(contactDTO==null) {
			 throw new ContactNotFoundException("id-" + id);
		 }
		return ResponseEntity.ok().body(contactDTO);
    }
	
	@PostMapping
	@ApiOperation(value = "Create a new Contact",response = ContactDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully posted data"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    		}
    )
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
	

	@PutMapping(value="/{id}")
	@ApiOperation(value = "Update an existing contact",response = ContactDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the contact"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    		}
    )
	public ResponseEntity<Object> updateContact(@Valid @RequestBody ContactDTO contactDto,@PathVariable("id")Long id ){		
		
		return ResponseEntity.ok().body(contactFacade.updateContact(id, contactDto));
		
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete an existing contact",response = ContactDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the contact"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    		}
    )
    public ResponseEntity<Void> deleteContact(@PathVariable Long id ) {
		
		contactFacade.delete(id);
		return  ResponseEntity.noContent().build();
    }
	
}
