package br.com.songs.converter.vacancies;

import br.com.songs.domain.entity.Vacancies;
import br.com.songs.web.dto.vacancies.VacanciesRequestGetDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPostDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPutDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VacanciesConverter {
    public static VacanciesRequestGetDTO convertOVacanciesToOngRequestGetDTO(Vacancies vacancies){
        return VacanciesRequestGetDTO.builder().id(vacancies.getId()).title(vacancies.getTitle()).description(vacancies.getDescription())
                .qtdVacancies(vacancies.getQtdVacacies()).ong(null).dateCreated(convertTimesTamp(vacancies.getCreatedOn())).lastUpdate(convertTimesTamp(vacancies.getLastUpdatedOn())).build();
    }

    private static LocalDateTime convertTimesTamp(Instant instant){
        return instant == null ? LocalDateTime.now():instant.atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

    public static Vacancies convertVacanciesRequestPostDTOToVacanciesEntity(VacanciesRequestPostDTO vacancies){
        return Vacancies.builder().title(vacancies.getTitle()).description(vacancies.getDescription()).qtdVacacies(vacancies.getQtdVacacies()).ong(null).build();
    }

    public static Vacancies convertVacanciesRequestPutDTOToVacanciesEntity(VacanciesRequestPutDTO vacancies){
        return Vacancies.builder().id(vacancies.getId()).title(vacancies.getTitle()).description(vacancies.getDescription()).qtdVacacies(vacancies.getQtdVacancies()).ong(null).build();
     }

}
