package br.com.songs.web.dto.perfil.employee;

import br.com.songs.web.dto.ong.OngRequestGetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRequestPutDTO {
	private long id;

	private String name;

	private String email;

	private String cpf;

	private LocalDate birthDate;

	private List<Long> ongs;

	private long ongEmployeeId;

	private char sex;

	private String imageURL;
}