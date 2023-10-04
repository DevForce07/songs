package br.com.songs.services.user.perfil;

import br.com.songs.web.dto.perfil.admin.AdminJwtToken;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;

public interface AdminOngPerfilService {

	AdminOngRequestGetDTO findById(long id);
	
	void updateUserCurrent(AdminOngRequestPutDTO userDTO);

	void updatePasswordUserCurrent(String password);

	AdminOngRequestGetDTO createUser(AdminOngRequestPostDTO userDTO);

	void deleteUserCurrent();

	AdminJwtToken login(String email, String password);

}
