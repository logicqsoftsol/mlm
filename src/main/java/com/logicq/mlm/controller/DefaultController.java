package com.logicq.mlm.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.mlm.service.login.ILoginService;

@RestController
@RequestMapping("/load")
public class DefaultController {

	
 @Autowired
 ILoginService loginservice;
	
    @PostConstruct
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Boolean> loadUser() throws Exception {
		        loginservice.loadusers();
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
