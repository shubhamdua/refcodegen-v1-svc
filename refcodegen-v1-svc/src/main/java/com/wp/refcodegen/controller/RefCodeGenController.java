package com.wp.refcodegen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PostMapping("/create/provider")
	public ResponseEntity<UserDto> createProvider(@RequestBody UserDto userDto){
		userDto = refCodeGenService.createProvider(userDto);
		return new ResponseEntity<>(userDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/{agentId}/providers/all")
	public ResponseEntity<List<UserDto>> fetchAllProvidersByAgentId(@PathVariable("agentId") String agentId){
		List<UserDto> listOfProviders = refCodeGenService.fetchAllProvidersByAgentId(agentId);
		return new ResponseEntity<>(listOfProviders,HttpStatus.OK);
	}
	
	@PutMapping("/update/provider")
	public ResponseEntity<UserDto> UpdateProviderByProviderId(@RequestBody UserDto userDto){
		UserDto updatedUserDto = refCodeGenService.updateProviderByProviderId(userDto);
		return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
	}
}
