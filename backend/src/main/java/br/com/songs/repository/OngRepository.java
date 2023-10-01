package br.com.songs.repository;

import br.com.songs.domain.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
    Ong findAllByIdIn(List<Ong> ongs);
}
