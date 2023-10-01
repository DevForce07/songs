package br.com.songs.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.songs.services.security.SongsUserDetailsService;
import br.com.songs.web.security.filter.JwtAuthenticationEntryPoint;
import br.com.songs.web.security.filter.JwtRequestFilter;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private SongsUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("configure security");
		

		httpSecurity.authorizeHttpRequests()
				.requestMatchers("/api/login/**", "**/public/**", "/swagger-resources/**", "/swagger-ui/**",
						"/swagger-ui.html", "/api-docs/**")
				.permitAll().and()
				.authorizeHttpRequests()
				.requestMatchers(org.springframework.http.HttpMethod.POST, "/api/songs/employees/**","/api/songs/ongs/**","/api/songs/admins/**","/api/songs/login")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers(org.springframework.http.HttpMethod.GET,"/api/songs/**", "/api/songs/employees/**","/api/songs/ongs/**")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/api/songs/**")
				.authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.authorizeHttpRequests().anyRequest().permitAll().and().httpBasic();
		httpSecurity.addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.csrf().disable();
		httpSecurity.cors();

		return httpSecurity.build();
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return null;
	}

}
