package br.com.songs.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SongsUserDetailsService extends UserDetailsService {
	UserDetails loadUserByEmail(String email);
}
