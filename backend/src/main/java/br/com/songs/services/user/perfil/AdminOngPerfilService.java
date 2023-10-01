package br.com.songs.services.user.perfil;

import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;

public interface AdminOngPerfilService {
	
	void updateUserCurrent(AdminOngRequestPutDTO userDTO);

	void updatePasswordUserCurrent(String password);

	AdminOngRequestGetDTO createUser(AdminOngRequestPostDTO userDTO);

	void deleteUserCurrent();

}
