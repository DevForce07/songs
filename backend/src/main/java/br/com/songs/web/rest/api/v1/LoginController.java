package br.com.songs.web.rest.api.v1;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.OperationException;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.services.user.perfil.AdminOngPerfilService;
import br.com.songs.services.user.perfil.EmployeePerfilService;
import br.com.songs.web.dto.perfil.admin.AdminJwtToken;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeJwtToken;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
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
	private AdminOngPerfilService adminOngPerfilService;

	private EmployeePerfilService employeePerfilService;

	private UserLoggedService userLoggedService;

	private LogSystemService logSystemService;
	@ApiResponse(responseCode = "200", description = "login", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TokenJwtDTO.class)) })
	@PostMapping("/admins/login")
	public ResponseEntity<?> loginAdmin(@RequestBody Login login) {
		AdminJwtToken authenticateAndGenerateToken = adminOngPerfilService.login(login.email,
				login.password);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("token", authenticateAndGenerateToken.getToken());

		return new ResponseEntity<>(authenticateAndGenerateToken, responseHeaders,
				HttpStatus.OK);
	}

	@ApiResponse(responseCode = "200", description = "login", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TokenJwtDTO.class)) })
	@PostMapping("/employees/login")
	public ResponseEntity<?> loginEmployee(@RequestBody Login login) {
		EmployeeJwtToken authenticateAndGenerateToken = employeePerfilService.login(login.email,
				login.password);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("token", authenticateAndGenerateToken.getToken());

		return new ResponseEntity<>(authenticateAndGenerateToken, responseHeaders,
				HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "get User current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PerfilOngRequestGetDTO.class)) })
	@GetMapping("/admins/current")
	public ResponseEntity<?> userAdminCurrent() {
		PerfilOngRequestGetDTO perfilLogged = userLoggedService.getPerfilLogged();
		AdminOngRequestGetDTO adminOngRequestGetDTO = adminOngPerfilService.findById(perfilLogged.getId());
		return new ResponseEntity<>(adminOngRequestGetDTO, HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "get User current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PerfilOngRequestGetDTO.class)) })
	@GetMapping("/employees/current")
	public ResponseEntity<?> userEmployeesCurrent() {
		PerfilOngRequestGetDTO perfilLogged = userLoggedService.getPerfilLogged();
		EmployeeRequestGetDTO employeeRequestGetDTO = employeePerfilService.findById(perfilLogged.getId());
		return new ResponseEntity<>(employeeRequestGetDTO, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "get User current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PerfilOngRequestGetDTO.class)) })
	@GetMapping("/current")
	public ResponseEntity<?> userCurrent() {
		PerfilOngRequestGetDTO perfilLogged = userLoggedService.getPerfilLogged();
		return new ResponseEntity<>(perfilLogged, HttpStatus.OK);
	}
	public record Login(String email, String password) {

	}

}
