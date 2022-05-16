package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.auth.Usermine;

public interface UserRepositoryI extends CrudRepository<Usermine, Long> {

	Usermine findByUsername(String username);

} //end of class
