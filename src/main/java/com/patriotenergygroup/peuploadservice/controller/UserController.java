package com.patriotenergygroup.peuploadservice.controller;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patriotenergygroup.peuploadservice.model.User;
import com.patriotenergygroup.peuploadservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody User user) {
        logger.debug("Creating user");
        
        if (user.getRoles() == null || user.getRoles().size() == 0) {
        	user.setRoles(new HashSet<>());
        	logger.info("Empty role, creating one for API_USER");
        }
        user.setActive(1);
        userService.saveUser(user);

        return new ResponseEntity<>("Successfully created", new HttpHeaders(), HttpStatus.OK);
	}
	
}
