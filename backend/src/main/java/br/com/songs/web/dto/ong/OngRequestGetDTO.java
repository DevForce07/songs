package br.com.songs.web.dto.ong;

import br.com.songs.web.dto.acting.ActingAreaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OngRequestGetDTO {

	private long id;

	private String name;

	private String cnpj;

	private String email;
	
	private String address;

	private String urlImage;
	
	private String phoneNumber;
	
	private ActingAreaDTO actingArea;

	private String description;
}
