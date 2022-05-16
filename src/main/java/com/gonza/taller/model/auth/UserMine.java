package com.gonza.taller.model.auth;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQuery(name = "UserMine.findAll", query = "SELECT a FROM UserMine a")
public class Usermine implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public final static String ADMIN = "ADMIN";
	public final static String USER = "USER";
	
	@Id
	@SequenceGenerator(name="USERR_USERID_GENERATOR", sequenceName="USERR_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERR_USERID_GENERATOR")
	@Column(name="USER_ID")
	private long userId;
	private String username;
	private String password;
	private String type;
	

	public Usermine() {
		
	}
			
	public Usermine(long userId, String username, String password, String type) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
} //end of class
