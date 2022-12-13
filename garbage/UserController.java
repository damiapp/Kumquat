package com.quat.Kumquat.controller;

import java.util.List;

import com.quat.Kumquat.model.User;
import com.quat.Kumquat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(path = "api/user")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/getAllUsers")
	public List<User> getUsers(){
		return userService.getUsers();
	}

	@PostMapping("/register")
	public void registerNewUser(@Valid @RequestBody User user){ userService.addNewUser(user);}

	@GetMapping("/login")
	public UserDetails loginUser(@RequestBody String name){
		return userService.loadUserByUsername(name);
	}
}
