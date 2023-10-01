package br.com.songs.services.user.login;

import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.UserNotFoundException;
import br.com.songs.repository.UserPerfilRepository;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.songs.converter.users.PerfilConverter.convertPerfilEntityToPerfilRequestGetDTO;
import static br.com.songs.converter.users.PerfilConverter.extratUserOrThrowException;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Log4j2
public class LoginPerfilServiceImpl implements UserLoggedService, LoginUserOngService {
	@Autowired
	private UserPerfilRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public PerfilOngRequestGetDTO login(String username, String password) {
		Optional<Perfil> findByUsernameAndPassword = userRepository.findByUsernameAndPassword(username, password);
		Perfil user = findByUsernameAndPassword.orElseThrow(() -> new UserNotFoundException("username or password are incorrect"));
		return convertPerfilEntityToPerfilRequestGetDTO(user);
	}

	@Override
	public Optional<Perfil> getUserLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails;

		try {
			userDetails = (UserDetails) auth.getPrincipal();
		} catch (Exception e) {
			throw new UserNotFoundException("User not found");
		}
		return userRepository.findByEmail(userDetails.getUsername());
	}

	@Override
	public PerfilOngRequestGetDTO getPerfilLogged() {
		Perfil user = extratUserOrThrowException(getUserLogged());
		return convertPerfilEntityToPerfilRequestGetDTO(user);
	}


}
