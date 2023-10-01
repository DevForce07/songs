package br.com.songs.web.rest.api.v1;

import br.com.songs.services.ong.OngService;
import br.com.songs.util.PageableUtil;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/songs/ongs")
@Tag(name = "ongs", description = "ongs")
@AllArgsConstructor
public class OngController {
	@Autowired
	private OngService ongService;

	@ApiResponse(responseCode = "200", description = "find ong by id", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OngRequestGetDTO.class)) })
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") long id) {
		OngRequestGetDTO requestGetDTO = ongService.findById(id);
		return new ResponseEntity<>(requestGetDTO, HttpStatus.CREATED);
	}

	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OngRequestGetDTO[].class)) })
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageableUtil.getPageable(page, size);
		return new ResponseEntity<>(ongService.findAll(pageable), HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OngRequestGetDTO.class)) })
	@PostMapping("/create")
	public ResponseEntity<?> createOng(@RequestBody OngRequestPostDTO ongDTO) {
		OngRequestGetDTO ongRequestGetDTO = ongService.saveOng(ongDTO);
		return new ResponseEntity<>(ongRequestGetDTO, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "update ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody OngRequestPutDTO ongDTO) {
		ongService.updateOng(ongDTO);
		return ResponseEntity.ok().build();
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "204", description = "delete ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		ongService.deleteOngById(id);
		return ResponseEntity.ok().build();
	}
}
