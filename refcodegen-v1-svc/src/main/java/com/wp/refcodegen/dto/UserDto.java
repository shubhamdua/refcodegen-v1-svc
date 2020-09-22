package com.wp.refcodegen.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

import lombok.Data;

@Component
@Data
public class UserDto {

	private String id;
	
	@NotNull
	private String name;
	
	private String role;
	
	private String city;
	
	@NotNull
	private String state;
	
	@NotNull
	private String contactNo;
	
	@NotNull
	private String emailId;
	
	private String linkedTo;
	
	private LocalDate createdOn;
	
	private String referralCode;
}
