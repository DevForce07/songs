package br.com.songs.web.dto.perfil.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRequestPostDTO {

	private String name;

	private String email;

	private String password;

	private String cpf;

	private LocalDate birthDate;

	private long ongEmployeeId;

	private char sex;
}
