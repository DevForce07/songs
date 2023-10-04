package br.com.songs.repository;

import java.util.List;
import java.util.Optional;

import br.com.songs.domain.entity.AdminOngPerfil;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPerfilRepository extends JpaRepository<Perfil, Long> {
	@Query(value = "SELECT p.id,p.email,p.name, p.password FROM perfil p WHERE p.email = :email and p.password = :password", nativeQuery = true)
	Optional<Perfil> findByUsernameAndPassword(@Param("email") String email,@Param("password") String password);

	Optional<Perfil> findByEmail(@Param("email") String email);

	boolean existsPerfilByEmail(@Param("email") String email);
	@Query(value = "SELECT p FROM EmployeePerfil p WHERE p.ongEmployeeId = :id")
	List<EmployeePerfil> findByIdOngEmplloyee(@Param("id") long id);

	@Query(value = "SELECT p FROM EmployeePerfil p WHERE p.id = :id")
	EmployeePerfil findByIdEmplloyee(@Param("id") long id);

	@Query(value = "SELECT a FROM AdminOngPerfil a WHERE a.id = :id")
	AdminOngPerfil findByIdAdmin(@Param("id") long id);

}
