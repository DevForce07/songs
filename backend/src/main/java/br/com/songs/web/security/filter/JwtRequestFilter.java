package br.com.songs.web.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.songs.exception.BadCredentialsCustomException;
import br.com.songs.services.user.login.AuthenticateAndManagerTokenService;
import br.com.songs.web.security.token.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {
	private static final String BEGIN_JWT_TOKEN = "Bearer ";
	private static final String HEADER_REQUEST_SOURCE_TOKEN_JWT = "Authorization";

	@Lazy
	private TokenUtil tokenUtil;

	@Autowired
	private AuthenticateAndManagerTokenService authenticateService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestTokenHeader = request.getHeader(HEADER_REQUEST_SOURCE_TOKEN_JWT);
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith(BEGIN_JWT_TOKEN)) {
			jwtToken = requestTokenHeader.substring(BEGIN_JWT_TOKEN.length());
			username = getUsernameFromToken(jwtToken);
		} else {
			log.debug("JWT Token does not begin with Bearer String");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = authenticateService.loadUserByUsername(username);

			if (tokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(request, response);

	}

	private String getUsernameFromToken(String token) {
		String username = null;

		try {
			tokenUtil = new TokenUtil();
			username = tokenUtil.getUsernameFromToken(token);
		} catch (IllegalArgumentException e) {
			log.error("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			log.error("JWT Token has expired");
		} catch (BadCredentialsCustomException e) {
			log.error("JWT Token invalid");
		} catch (BadCredentialsException e) {
			log.error("JWT Bad Credentials");
		} catch (Exception e) {
			log.error("JWT Erro");
		}

		return username;
	}

}
