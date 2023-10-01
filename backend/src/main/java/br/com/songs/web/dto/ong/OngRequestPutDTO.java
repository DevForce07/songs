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
public class OngRequestPutDTO {
	private long id;

	private String name;

	private String email;

	private String cnpj;

	private String urlImage;
	
	private String address;
	
	private String phoneNumber;
	
	private Long actingArea;

	private String description;
}