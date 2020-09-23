package com.wp.refcodegen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("v1/svc/rcg")
@CrossOrigin("http://localhost:9100")
public class RefCodeGenController {
	
	@Autowired
	private RefCodeGenService refCodeGenService;

	@PostMapping("/create/salesagent")
	public ResponseEntity<UserDto> createSalesAgent(@RequestBody UserDto userDto){
		userDto = refCodeGenService.createSalesAgent(userDto);
		return new ResponseEntity<>(userDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/agent/{agentId}/create/provider")
	public ResponseEntity<UserDto> createProvider(@PathVariable("agentId") String agentId, @RequestBody UserDto userDto){
		userDto.setLinkedTo(agentId);
		userDto = refCodeGenService.createProvider(userDto);
		return new ResponseEntity<>(userDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/{agentId}/providers/all")
	public ResponseEntity<List<UserDto>> fetchAllProvidersByAgentId(@PathVariable("agentId") String agentId){
		List<UserDto> listOfProviders = refCodeGenService.fetchAllProvidersByAgentId(agentId);
		return new ResponseEntity<>(listOfProviders,HttpStatus.OK);
	}
	
	@GetMapping("/provider/{providerId}")
	public ResponseEntity<UserDto> fetchProviderById(@PathVariable("providerId") String providerId){
		UserDto provider = refCodeGenService.fetchProviderById(providerId);
		return new ResponseEntity<>(provider,HttpStatus.OK);
	}
	
	@PutMapping("/update/provider")
	public ResponseEntity<UserDto> UpdateProviderByProviderId(@RequestBody UserDto userDto){
		UserDto updatedUserDto = refCodeGenService.updateProviderByProviderId(userDto);
		return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
	}
}
