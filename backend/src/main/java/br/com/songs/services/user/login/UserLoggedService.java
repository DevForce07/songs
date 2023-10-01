package br.com.songs.services.user.login;

import java.util.Optional;

import br.com.songs.domain.entity.Perfil;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;

public interface UserLoggedService {
	Optional<Perfil> getUserLogged();

	PerfilOngRequestGetDTO getPerfilLogged();
}
