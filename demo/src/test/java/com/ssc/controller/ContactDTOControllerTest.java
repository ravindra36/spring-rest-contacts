package com.ssc.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssc.demo.ContactPersistenceContext;
import com.ssc.demo.DemoApplication;
import com.ssc.facade.ContactFacade;
import com.ssc.model.PhoneType;
import com.ssc.model.dto.AddressDTO;
import com.ssc.model.dto.ContactDTO;
import com.ssc.model.dto.ContactDetailDTO;
import com.ssc.model.dto.PhoneDTO;

@ContextConfiguration(classes= {DemoApplication.class, ContactPersistenceContext.class},loader=AnnotationConfigWebContextLoader.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")

public class ContactDTOControllerTest {

	
	
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	
	 private MockRestServiceServer mockRestServiceServer;

	    @Autowired
	    private WebApplicationContext wac;
	
	@MockBean
	private ContactFacade contactFacade;
	
	
	 @Before
	    public void setUp() throws Exception {
	        mockMvc = MockMvcBuilders .webAppContextSetup(this.wac).build();
	    }
	
	@Test
	public void createContact() throws Exception{
		String mockjson= "{ \"email\":\"ravindra.noolu@gmail.com\", \"name\":{ \"first\":\"spiderman\", \"middle\":\"spiderman\", \"last\":\"noolu\" }, \"address\": { \"street\": \"109 Gov\", \"city\": \"Cannon\", \"state\": \"Delaware\", \"zip\": \"19797\" }, \"phone\": [ { \"number\": \"302-611-9180\", \"type\": \"home\" }, { \"number\": \"302-532-9427\", \"type\": \"mobile\" } ] }";
		
		ContactDTO contactDto=null;
				

		Mockito.when(
				contactFacade.createContact(Mockito.any(ContactDTO.class))).thenReturn(contactDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/contacts")
										.accept(MediaType.APPLICATION_JSON)
										.content(mockjson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
		
		/*
		
		 mockMvc.perform(post("/dtocontacts")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(mockjson) 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isCreated());
		*/
	}
	
	@Test
	public void badRequestCreateContactWithMissingEmail() throws Exception{
		
		ArrayList list = new ArrayList<PhoneDTO>();
		
		list.add(new PhoneDTO("", PhoneType.mobile));
		list.add(new PhoneDTO("", PhoneType.home));
		ContactDTO contactDto = new ContactDTO("",
				new ContactDetailDTO("Harold", "Francis", "Gilkey"),
				new AddressDTO("8360 High Autumn Row","Cannon","Delaware","19797" ),
				list
				);
		Mockito.when(
				contactFacade.createContact(Mockito.any(ContactDTO.class))).thenReturn(contactDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/contacts")
										.accept(MediaType.APPLICATION_JSON)
										.content(toJson(contactDto))
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	
	@Test
	public void shouldNotFoundGetContactByBadId() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/contacts/300")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	
	private byte[] toJson(Object object) throws Exception {
        return this.mapper.writeValueAsString(object).getBytes();
    }
	
}
