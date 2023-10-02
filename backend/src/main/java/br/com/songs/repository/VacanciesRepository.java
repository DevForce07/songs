package br.com.songs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.songs.domain.entity.Vacancies;

import java.util.List;

@Repository
public interface VacanciesRepository extends JpaRepository<Vacancies, Long> {
    @Query("SELECT v FROM Vacancies v join v.ong ong WHERE ong.id = :id_ong")
    List<Vacancies> findAllByIdOng(@Param("id_ong") long idOng, Pageable pageable);

    Page<Vacancies> findAllByTitleContainsIgnoreCase(String name, Pageable pageable);
}
