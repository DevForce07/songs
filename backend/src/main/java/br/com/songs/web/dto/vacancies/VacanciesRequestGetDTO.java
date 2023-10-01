package br.com.songs.web.dto.vacancies;

import br.com.songs.web.dto.ong.OngRequestGetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacanciesRequestGetDTO {
	private long id;
	private String title;
	private OngRequestGetDTO ong;
	private String description;
	private int qtdVacancies;
	private LocalDateTime lastUpdate;
	private LocalDateTime dateCreated;

}
