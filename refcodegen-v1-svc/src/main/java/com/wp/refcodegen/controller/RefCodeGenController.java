package com.wp.refcodegen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wp.refcodegen.dto.UserDto;
import com.wp.refcodegen.service.RefCodeGenService;

@RestController
@RequestMapping("rcg/v1/svc/")
public class RefCodeGenController {
	
	@Autowired
	private RefCodeGenService refCodeGenService;

	@PostMapping("/create/salesagent")
	public ResponseEntity<UserDto> createSalesAgent(@RequestBody UserDto userDto){
		userDto = refCodeGenService.createSalesAgent(userDto);
		return new ResponseEntity<>(userDto,HttpStatus.CREATED);
	}

}
