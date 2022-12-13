package com.quat.Kumquat.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.google.common.hash.Hashing;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.repository.UserRepository;
import com.quat.Kumquat.controller.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.*;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public void addNewUser(User user){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if(violations.isEmpty() && !userRepository.findUserByEmail(user.getEmail()).isPresent()) {
			user.setPassword(hashMyString(user.getPassword()));
			userRepository.save(user);
		}
		else{
			throw new ConstraintViolationException(violations);
		}
	}


	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(name);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		return new MyUserDetails(user);
	}

	public String hashMyString(String string){
		return Hashing.sha256()
				.hashString(string, StandardCharsets.UTF_8)
				.toString();
	}
}
