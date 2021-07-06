package com.growth.startupone.core.security.authorizationServer.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.growth.startupone.core.security.authorizationServer.model.AuthUser;
import com.growth.startupone.domain.model.User;
import com.growth.startupone.domain.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username with informed email was not found."));
		
		return new AuthUser(user, getAuthorities(user));
	}
	
	public Collection<GrantedAuthority> getAuthorities(User user) {
		return user
					.getRoles()
						.stream()
							.flatMap(role -> role.getPermissions().stream())
							.map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
						.collect(Collectors.toSet());
	}

}
