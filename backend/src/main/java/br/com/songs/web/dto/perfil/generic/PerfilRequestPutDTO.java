package br.com.songs.web.dto.perfil.generic;

import br.com.songs.web.dto.ong.OngRequestGetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PerfilRequestPutDTO {
	private long id;

	private String name;

	private String email;

	private String password;

	private String cpf;

	private List<Long> ongs;
}