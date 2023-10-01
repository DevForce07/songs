package br.com.songs.services.security;

import java.util.Optional;

import br.com.songs.domain.entity.Perfil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.songs.domain.authenticate.UserAuthenticateDetail;
import br.com.songs.repository.UserPerfilRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements SongsUserDetailsService {
	private UserPerfilRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUserByEmail(username);
	}

	@Override
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		Optional<Perfil> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			throw new UsernameNotFoundException(email);
		}
		return new UserAuthenticateDetail(userOptional.get());
	}

}
