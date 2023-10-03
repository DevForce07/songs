package br.com.songs.web.rest.api.v1;

import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.services.user.perfil.AdminOngPerfilService;
import br.com.songs.services.user.perfil.EmployeePerfilService;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/songs/employees")
@Tag(name = "employees", description = "users")
@AllArgsConstructor
public class EmployeePerfilController {
	@Autowired
	private EmployeePerfilService userService;

	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OngRequestGetDTO[].class)) })
	@GetMapping("/findAllByIdOng/{id}")
	public ResponseEntity<?> findAll(@PathVariable("id") long id) {
		return new ResponseEntity<>(userService.findAllUsersByIdOng(id), HttpStatus.OK);
	}

	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OngRequestGetDTO.class)) })
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") long id) {
		return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "create a new User", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdminOngRequestPostDTO.class)) })
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody EmployeeRequestPostDTO userDTO) {
		EmployeeRequestGetDTO userGET = userService.createUser(userDTO);
		return new ResponseEntity<>(userGET, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "update password User", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody EmployeeRequestPutDTO userDTO) {
		userService.updateUserCurrent(userDTO);
		return ResponseEntity.ok().build();
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "update password User", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@PatchMapping("/update/password")
	public ResponseEntity<?> updatePasswordUser(@RequestBody Password password) {
		userService.updatePasswordUserCurrent(password.password);
		return ResponseEntity.ok().build();
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "204", description = "delete user current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete() {
		userService.deleteUserCurrent();
		return ResponseEntity.ok().build();
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "204", description = "delete user current", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@DeleteMapping("/delete/byId/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok().build();
	}


	public record Password(String password){}
}
