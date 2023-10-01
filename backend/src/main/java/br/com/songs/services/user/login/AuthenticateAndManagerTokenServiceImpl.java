package br.com.songs.services.user.login;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.songs.domain.entity.Perfil;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.songs.domain.authenticate.UserAuthenticateDetail;
import br.com.songs.domain.entity.Ong;
import br.com.songs.exception.BadCredentialsCustomException;
import br.com.songs.exception.UserDisabledException;
import br.com.songs.services.security.SongsUserDetailsService;
import br.com.songs.web.dto.security.TokenJwtDTO;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.security.token.TokenUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticateAndManagerTokenServiceImpl implements AuthenticateAndManagerTokenService {
	@Lazy
	private SongsUserDetailsService userDetailsServiceImpl;
	@Lazy
	private AuthenticationManager authenticationManager;
	@Lazy
	private TokenUtil jwtTokenUtil;

	@Override
	public TokenJwtDTO authenticateAndGenerateToken(String username, String password) {
		authenticate(username, password);
		UserDetails userDetails = loadUserByUsername(username);

		return generateTokenFromUserDetails(userDetails);
	}

	@Override
	public void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new UserDisabledException("User disabled " + e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsCustomException("invalid credentials");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDetailsServiceImpl.loadUserByUsername(username);
	}

	public PerfilOngRequestGetDTO extractUserDtoFromUserDetails(UserDetails userDetails) {
		UserAuthenticateDetail acessoUser = (UserAuthenticateDetail) userDetails;
		return convertPerfilEntityToPerfilRequestGetDTO(acessoUser.getUser());
	}

	private PerfilOngRequestGetDTO convertPerfilEntityToPerfilRequestGetDTO(Perfil user) {
		List<OngRequestGetDTO> ongRequestGetDTOS = null;
		if(user.getOngs() != null){
			ongRequestGetDTOS = user.getOngs().stream().map(this::convertUserEntityToUserDto).collect(Collectors.toList());

		}

		return PerfilOngRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).cpf(user.getDocument()).ongs(ongRequestGetDTOS).build();
	}

	private OngRequestGetDTO convertUserEntityToUserDto(Ong user) {
		return OngRequestGetDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName())
				.urlImage(user.getUrlImage()).cnpj(user.getCnpj()) .build();
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return jwtTokenUtil.generateToken(userDetails);
	}

	private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private TokenJwtDTO generateTokenFromUserDetails(UserDetails userDetails) {
		String generateToken = generateToken(userDetails);

		Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(generateToken);

		PerfilOngRequestGetDTO userDtoCurrent = extractUserDtoFromUserDetails(userDetails);

        return TokenJwtDTO.builder().token(generateToken)
				.expire(convertToLocalDateTimeViaInstant(expirationDateFromToken)).userDTO(userDtoCurrent).build();
	}

}
