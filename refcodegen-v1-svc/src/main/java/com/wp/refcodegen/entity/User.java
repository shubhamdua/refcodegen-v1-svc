package com.wp.refcodegen.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "role", nullable = false)
	private String role;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "contact_no", unique = true)
	private String contactNo;
	
	@Column(name = "email_id", unique = true)
	private String emailId;
	
	@Column(name = "linked_to")
	private String linkedTo;
	
	@Column(name = "created_on")
	private LocalDate createdOn;
	
	@Column(name = "referral_code")
	private String referralCode;
}
