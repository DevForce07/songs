package br.com.songs.services.user.login;

import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;

public interface LoginUserOngService {
	PerfilOngRequestGetDTO login(String username, String password);
}
