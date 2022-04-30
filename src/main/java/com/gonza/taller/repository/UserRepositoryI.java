package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.auth.UserMine;

public interface UserRepositoryI extends CrudRepository<UserMine, Long> {

	UserMine findByUsername(String username);

} //end of class
