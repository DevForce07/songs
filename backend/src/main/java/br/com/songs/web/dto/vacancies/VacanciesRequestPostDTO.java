package br.com.songs.web.dto.vacancies;

import br.com.songs.domain.entity.Ong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacanciesRequestPostDTO {
	private String title;
	private Long ong;
	private String description;
	private int qtdVacacies;
}
