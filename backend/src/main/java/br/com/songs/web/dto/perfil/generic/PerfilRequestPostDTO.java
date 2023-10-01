package br.com.songs.web.dto.perfil.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PerfilRequestPostDTO {

	private String name;

	private String email;

	private String password;

	private String cpf;
}
