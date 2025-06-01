package com.sv.app.security;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sv.app.entity.Account;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.UserAppRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserAppRepository userRepo;
	
	public Collection<GrantedAuthority> authorities(List<Account> rol){
		return rol.stream().map(role -> new SimpleGrantedAuthority(role.getNumberAccount())).collect(Collectors.toList());
	}
	
	/*
	 * Valida que el usuario exista en la DB y authentica segun spring security
	 * 
	 */
	 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserApp> user = userRepo.findByEmail(email);
		if(user.isPresent()) {
			UserApp userAuth= user.get();
			return new User(userAuth.getEmail(), userAuth.getPassword(), authorities(userAuth.getRoles()));
		}
		throw new UsernameNotFoundException("usuario no encontrado");
	}

}
