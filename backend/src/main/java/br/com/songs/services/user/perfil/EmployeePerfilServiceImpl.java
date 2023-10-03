package br.com.songs.services.user.perfil;

import br.com.songs.converter.users.PerfilConverter;
import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.EmployeePerfil;
import br.com.songs.domain.entity.Ong;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.OperationException;
import br.com.songs.exception.UserNotFoundException;
import br.com.songs.repository.UserPerfilRepository;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.ong.OngService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.songs.converter.users.PerfilConverter.*;

@Service
@AllArgsConstructor
@Data
public class EmployeePerfilServiceImpl implements EmployeePerfilService{
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
    @Override
    public void updateUserCurrent(EmployeeRequestPutDTO userDTO) {
        EmployeePerfil employeePerfil = convertEmployeeRequestPutDTOToEmployeeEntity(userDTO);

        if (employeePerfil.getId() == 0){
            throw new UserNotFoundException("ID not found");
        }

        checkoutIfExistsOng(employeePerfil);

        List<Ong> ongs = ongService.findIn(userDTO.getOngs());
        checkFieldsFromUser(employeePerfil, false);
        employeePerfil.setOngs(ongs);
        employeePerfil.setPassword(passwordEncoder.encode(employeePerfil.getPassword()));
        userRepository.save(employeePerfil);
        logSystemService.createLog(LogSystem.UPDATE_EMPLOYEES,employeePerfil.getOngEmployeeId(), userLoggedService.getUserLogged().get().getId(), "update empĺoyee");

    }

    @Override
    public void updatePasswordUserCurrent(String password) {
        Perfil userLogged = userLoggedService.getUserLogged().get();
        userLogged.setPassword(passwordEncoder.encode(password));
        checkFieldsFromUser(userLogged, false);
        userRepository.save(userLogged);
        //TODO add log
    }

    @Override
    public EmployeeRequestGetDTO createUser(EmployeeRequestPostDTO userDTO) {
        EmployeePerfil employeePerfil = convertEmployeeRequestPostDTOToEmployeeEntity(userDTO);
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        if(!userLogged.isPresent() || !userLogged.get().getDecriminatorValue().isAdmin()){
            throw new UserNotFoundException("User admin not found");
        }

        if(userRepository.existsPerfilByEmail(userDTO.getEmail())){
            throw new UserNotFoundException("Erro email already exists");
        }


        checkoutIfExistsOng(employeePerfil);
        checkFieldsFromUser(employeePerfil,true);
        employeePerfil.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        EmployeePerfil ongPerfil = userRepository.save(employeePerfil);
        logSystemService.createLog(LogSystem.CREATE_EMPLOYEES, ongPerfil.getOngEmployeeId(), userLoggedService.getUserLogged().get().getId(), "create empĺoyee");
        return employeeEntityToConvertEmployeeRequestGetDTO(ongPerfil);
    }

    @Override
    public List<EmployeeRequestGetDTO> findAllUsersByIdOng(long id) {
        List<EmployeePerfil> employeePerfils = userRepository.findByIdOngEmplloyee(id);

        return employeePerfils.stream().map(PerfilConverter::employeeEntityToConvertEmployeeRequestGetDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeRequestGetDTO findById(long id) {
        EmployeePerfil byId = userRepository.findByIdEmplloyee(id);

        if(byId == null){
            throw new OperationException("user not found");
        }

        return employeeEntityToConvertEmployeeRequestGetDTO(byId);
    }

    private void checkoutIfExistsOng(EmployeePerfil employeePerfil){
        try{
            ongService.findById(employeePerfil.getOngEmployeeId());
        }catch (Exception ex){
            throw new OperationException("Ong employee not found");
        }
    }

    @Override
    public void deleteUserCurrent() {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        if(userLogged.isPresent() && userLogged.get().getDecriminatorValue().isEmployee()){
            userRepository.delete(userLogged.get());
            logSystemService.createLog(LogSystem.DELETE_EMPLOYEES, ((EmployeePerfil) userLogged.get()).getOngEmployeeId(), userLoggedService.getUserLogged().get().getId(), "delete empĺoyee");

        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUserById(long id) {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        if(userLogged.isPresent() && userLogged.get().getDecriminatorValue().isAdmin()){
            userRepository.deleteById(id);
        }
    }
}
