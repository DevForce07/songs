package br.com.songs.web.rest.api.v1;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.user.login.AuthenticateAndManagerTokenService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.util.PageableUtil;
import br.com.songs.web.dto.audit.LogDTO;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import br.com.songs.web.dto.security.TokenJwtDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/songs/audit")
@Tag(name = "audit", description = "audit")
@AllArgsConstructor
public class AuditController {

	private LogSystemService logSystemService;
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "login", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = LogDTO[].class)) })
	@GetMapping("/findByOng/{id}")
	public ResponseEntity<?> findbyId(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageableUtil.getPageable(page, size);
		List<LogDTO> logDTOS = logSystemService.findByIdOng(id, pageable);
		return new ResponseEntity<>(logDTOS, HttpStatus.OK);
	}


}
