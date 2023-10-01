package br.com.songs.repository;

import br.com.songs.domain.audit.Logs;
import br.com.songs.domain.entity.ActingArea;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findAllByOngId(long id, Pageable pageable);
}
