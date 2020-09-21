package com.wp.refcodegen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rcg/v1/svc/")
public class RefCodeGenController {

	@GetMapping("/hello")
	public String getHello(){
		return "Hello";
	}

}
