package br.com.songs.services.user.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.songs.web.dto.security.TokenJwtDTO;

public interface AuthenticateAndManagerTokenService extends UserDetailsService {
	void authenticate(String username, String password);

	String generateToken(UserDetails userDetails);

	TokenJwtDTO authenticateAndGenerateToken(String username, String password);
}
