package com.ssc.jpa.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ssc.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long>{

	Optional<Phone> findById(Long id);
}
