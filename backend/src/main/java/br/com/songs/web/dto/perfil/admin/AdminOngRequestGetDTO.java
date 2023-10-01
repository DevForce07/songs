package br.com.songs.web.dto.perfil.admin;

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
public class AdminOngRequestGetDTO {
	private long id;

	private String name;

	private String email;

	private String cpf;

	private List<OngRequestGetDTO> ongs;
}
