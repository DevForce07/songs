package br.com.songs.web.dto.vacancies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacanciesRequestPutDTO {
	private long id;
	private String title;
	private Long ong;
	private String description;
	private int qtdVacancies;
}