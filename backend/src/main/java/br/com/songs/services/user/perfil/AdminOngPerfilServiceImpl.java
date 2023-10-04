package br.com.songs.services.user.perfil;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.AdminOngPerfil;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Ong;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.OperationException;
import br.com.songs.exception.UserNotFoundException;
import br.com.songs.repository.UserPerfilRepository;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.ong.OngService;
import br.com.songs.services.user.login.AuthenticateAndManagerTokenService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.perfil.admin.AdminJwtToken;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPostDTO;
import br.com.songs.web.dto.perfil.admin.AdminOngRequestPutDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeJwtToken;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.security.TokenJwtDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.songs.converter.users.PerfilConverter.*;

@Service
@AllArgsConstructor
@Data
public class AdminOngPerfilServiceImpl implements AdminOngPerfilService{
    @Autowired
    private UserPerfilRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserLoggedService userLoggedService;
    @Autowired
    private OngService ongService;
    @Autowired
    private LogSystemService logSystemService;
    @Autowired
    private AuthenticateAndManagerTokenService authenticateService;

    @Override
    public AdminOngRequestGetDTO findById(long id) {
        AdminOngPerfil byIdAdmin = userRepository.findByIdAdmin(id);

        if (byIdAdmin == null){
            throw new UserNotFoundException("Use admin fot found");
        }
        return adminOngEntityToConvertUserOngRequestGetDTO(byIdAdmin);
    }

    @Override
    public void updateUserCurrent(AdminOngRequestPutDTO userDTO) {
        AdminOngPerfil adminOngPerfil = convertUserOngRequestPutDTOToAdminOngEntity(userDTO);
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();

        if (adminOngPerfil.getId() == 0){
            throw new UserNotFoundException("ID not found");
        }

        List<Ong> ongs = ongService.findIn(userDTO.getOngs());

        adminOngPerfil.setOngs(ongs);
        adminOngPerfil.setPassword(userLogged.get().getPassword());
        checkFieldsFromUser(adminOngPerfil, false);
        userRepository.save(adminOngPerfil);

        adminOngPerfil.getOngs().stream().forEach(e ->{
                    logSystemService.createLog(LogSystem.UPDATE_ADMIN,e.getId(), userLoggedService.getUserLogged().get().getId(), "update admin");
                }

        );

    }

    @Override
    public void updatePasswordUserCurrent(String password) {
        Perfil userLogged = userLoggedService.getUserLogged().get();
        userLogged.setPassword(passwordEncoder.encode(password));
        checkFieldsFromUser(userLogged, false);
        userRepository.save(userLogged);
        userLogged.getOngs().stream().forEach(e ->{
            logSystemService.createLog(LogSystem.UPDATE_ADMIN,e.getId(), userLoggedService.getUserLogged().get().getId(), "update admin");
        });
    }

    @Override
    public AdminOngRequestGetDTO createUser(AdminOngRequestPostDTO userDTO) {
        AdminOngPerfil adminOngPerfil = convertUserOngRequestPostDTOToAdminOngEntity(userDTO);
        if(userRepository.existsPerfilByEmail(adminOngPerfil.getEmail())){
            throw new UserNotFoundException("Erro email already exists");
        }

        checkFieldsFromUser(adminOngPerfil, true);

        adminOngPerfil.setPassword(passwordEncoder.encode(adminOngPerfil.getPassword()));
        AdminOngPerfil ongPerfil = userRepository.save(adminOngPerfil);

        return adminOngEntityToConvertUserOngRequestGetDTO(ongPerfil);
    }

    @Override
    public void deleteUserCurrent() {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        if(userLogged.isPresent()&& userLogged.get().getDecriminatorValue().isAdmin()){
            userRepository.delete(userLogged.get());
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public AdminJwtToken login(String email, String password) {
        TokenJwtDTO authenticateAndGenerateToken = authenticateService.authenticateAndGenerateToken(email,
                password);
        long id = authenticateAndGenerateToken.getUserDTO().getId();


        AdminOngRequestGetDTO adminOngRequestGetDTO = findById(id);

        return AdminJwtToken.builder().token(authenticateAndGenerateToken.getToken()).expire(authenticateAndGenerateToken.getExpire()).userDTO(adminOngRequestGetDTO).build();
    }
}
