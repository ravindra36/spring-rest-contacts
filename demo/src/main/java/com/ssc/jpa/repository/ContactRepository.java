package com.ssc.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssc.model.Contact;

@Repository(value="contactRepository")
public interface ContactRepository extends CrudRepository<Contact, Long>{
	


}
