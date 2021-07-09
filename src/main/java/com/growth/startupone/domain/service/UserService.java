package com.growth.startupone.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.growth.startupone.domain.exception.EmailAlreadyInUseException;
import com.growth.startupone.domain.model.User;
import com.growth.startupone.domain.repository.UserRepository;

@Service
public class UserService {
	
	private static final String EMAIL_ALREADY_IN_USE = "Provided email '%s' is already in use.";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean checkEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if (user.isPresent()) {
			throw new EmailAlreadyInUseException(String.format(EMAIL_ALREADY_IN_USE, email));
		}
		
		return false;
	}
	
	@Transactional
	public User store(User user) {
		checkEmail(user.getEmail());
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
}
