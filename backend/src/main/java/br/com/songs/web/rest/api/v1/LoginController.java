package br.com.songs.web.rest.api.v1;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.songs.services.user.login.AuthenticateAndManagerTokenService;
import br.com.songs.web.dto.security.TokenJwtDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
@Tag(name = "login", description = "login users")
@AllArgsConstructor
public class LoginController {
	private AuthenticateAndManagerTokenService authenticateService;

	private UserLoggedService userLoggedService;

	private LogSystemService logSystemService;
	@ApiResponse(responseCode = "200", description = "login", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TokenJwtDTO.class)) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Login login) {
		TokenJwtDTO authenticateAndGenerateToken = authenticateService.authenticateAndGenerateToken(login.email,
				login.password);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("token", authenticateAndGenerateToken.getToken());
		PerfilOngRequestGetDTO perfil = authenticateAndGenerateToken.getUserDTO();

		if(!perfil.getOngs().isEmpty()){
			perfil.getOngs().stream().forEach(e->{
				logSystemService.createLog(LogSystem.LOGIN,e.getId(), e.getId(), "novo login");
			});

		}

		return new ResponseEntity<>(authenticateAndGenerateToken, responseHeaders,
				HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "get User current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PerfilOngRequestGetDTO.class)) })
	@GetMapping("/current")
	public ResponseEntity<?> userCurrent() {
		PerfilOngRequestGetDTO perfilLogged = userLoggedService.getPerfilLogged();
		return new ResponseEntity<>(perfilLogged, HttpStatus.CREATED);
	}
	public record Login(String email, String password) {
	}

}
