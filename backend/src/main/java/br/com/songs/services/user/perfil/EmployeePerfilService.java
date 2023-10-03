package br.com.songs.services.user.perfil;

import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;

import java.util.List;

public interface EmployeePerfilService {
	
	void updateUserCurrent(EmployeeRequestPutDTO userDTO);

	void updatePasswordUserCurrent(String password);

	EmployeeRequestGetDTO createUser(EmployeeRequestPostDTO userDTO);

	List<EmployeeRequestGetDTO> findAllUsersByIdOng(long id);

	EmployeeRequestGetDTO findById(long id);

	void deleteUserCurrent();

	void deleteUserById(long id);
}
