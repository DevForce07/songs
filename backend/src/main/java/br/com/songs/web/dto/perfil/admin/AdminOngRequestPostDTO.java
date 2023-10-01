package br.com.songs.web.dto.perfil.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminOngRequestPostDTO {

	private String name;

	private String email;

	private String password;

	private String cpf;
}
