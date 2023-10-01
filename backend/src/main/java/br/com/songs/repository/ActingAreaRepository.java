package br.com.songs.repository;

import br.com.songs.domain.entity.ActingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.songs.domain.entity.Ong;

@Repository
public interface ActingAreaRepository extends JpaRepository<ActingArea, Long> {

}
