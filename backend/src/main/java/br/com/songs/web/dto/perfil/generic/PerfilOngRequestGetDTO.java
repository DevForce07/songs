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
public class PerfilOngRequestGetDTO {
	private long id;

	private String name;

	private String email;

	private String cpf;

	private List<OngRequestGetDTO> ongs;

	private boolean isAdmin;

}
