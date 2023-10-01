package br.com.songs.web.dto.security;

import java.time.LocalDateTime;

import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenJwtDTO {
	private String token;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime expire;

	private PerfilOngRequestGetDTO userDTO;
}
