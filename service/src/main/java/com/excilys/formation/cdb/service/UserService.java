package com.excilys.formation.cdb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.persistence.DAOUser;

public class UserService implements UserDetailsService {

	private DAOUser daoUser;

	public UserService() {
	}

	public UserService(DAOUser daoUser) {
		this.daoUser = daoUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = daoUser.findByUsername(username);
		return user;
	}

}
