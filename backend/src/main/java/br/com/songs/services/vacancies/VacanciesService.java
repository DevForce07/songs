package br.com.songs.services.vacancies;

import br.com.songs.domain.entity.Ong;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestGetDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPostDTO;
import br.com.songs.web.dto.vacancies.VacanciesRequestPutDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacanciesService {
    VacanciesRequestGetDTO findById(long id);

    List<VacanciesRequestGetDTO> findAll(Pageable pageable);

    List<VacanciesRequestGetDTO> findByTitle(String name, Pageable pageable);

    List<VacanciesRequestGetDTO>  findByIdOng(long id, Pageable pageable);

    VacanciesRequestGetDTO saveOng(VacanciesRequestPostDTO vacanciesRequestPostDTO);

    void updateOng(VacanciesRequestPutDTO vacanciesRequestPutDTO);

    void deleteOngById(long id);
}
