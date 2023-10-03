package br.com.songs.web.rest.api.v1;

import br.com.songs.services.vacancies.VacanciesService;
import br.com.songs.util.PageableUtil;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestGetDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPostDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPutDTO;
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

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/songs/vacancies")
@Tag(name = "vacancies", description = "vacancies")
@AllArgsConstructor
public class VacanciesController {
	@Autowired
	private VacanciesService vacanciesService;

	@ApiResponse(responseCode = "200", description = "find ong by id", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VacanciesRequestGetDTO.class)) })
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") long id) {
		VacanciesRequestGetDTO vacanciesRequestGetDTO = vacanciesService.findById(id);
		return new ResponseEntity<>(vacanciesRequestGetDTO, HttpStatus.CREATED);
	}

	@ApiResponse(responseCode = "200", description = "find ong by id", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VacanciesRequestGetDTO.class)) })
	@GetMapping("/findByTitle/{title}")
	public ResponseEntity<?> findByTitle(@PathVariable("title") String title, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageableUtil.getPageable(page, size);
		List<VacanciesRequestGetDTO> vacanciesRequestGetDTOList = vacanciesService.findByTitle(title, pageable);
		return new ResponseEntity<>(vacanciesRequestGetDTOList, HttpStatus.CREATED);
	}

	@ApiResponse(responseCode = "200", description = "find ong by id", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VacanciesRequestGetDTO.class)) })
	@GetMapping("/findByIdOng/{id}")
	public ResponseEntity<?> findByIdOng(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageableUtil.getPageable(page, size);
		List<VacanciesRequestGetDTO> vacanciesRequestGetDTOList = vacanciesService.findByIdOng(id, pageable);
		return new ResponseEntity<>(vacanciesRequestGetDTOList, HttpStatus.CREATED);
	}

	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VacanciesRequestGetDTO[].class)) })
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageableUtil.getPageable(page, size);
		return new ResponseEntity<>(vacanciesService.findAll(pageable), HttpStatus.OK);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "create a new Ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VacanciesRequestGetDTO.class)) })
	@PostMapping("/create")
	public ResponseEntity<?> createOng(@RequestBody VacanciesRequestPostDTO vacanciesDTO) {
		VacanciesRequestGetDTO vacanciesRequestGetDTO = vacanciesService.saveOng(vacanciesDTO);
		return new ResponseEntity<>(vacanciesRequestGetDTO, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "200", description = "update ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody VacanciesRequestPutDTO vacanciesDTO) {
		vacanciesService.updateOng(vacanciesDTO);
		return ResponseEntity.ok().build();
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponse(responseCode = "204", description = "delete ong", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) })
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		vacanciesService.deleteOngById(id);
		return ResponseEntity.ok().build();
	}
}
