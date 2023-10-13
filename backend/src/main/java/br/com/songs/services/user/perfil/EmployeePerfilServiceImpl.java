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
import br.com.songs.services.file.FileUploadServiceImpl;
import br.com.songs.services.ong.OngService;
import br.com.songs.services.user.login.AuthenticateAndManagerTokenService;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.perfil.employee.EmployeeJwtToken;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestGetDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPostDTO;
import br.com.songs.web.dto.perfil.employee.EmployeeRequestPutDTO;
import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import br.com.songs.web.dto.security.TokenJwtDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private AuthenticateAndManagerTokenService authenticateService;
    @Autowired
    private FileUploadServiceImpl fileUpload;
    @Override
    public void updateUserCurrent(EmployeeRequestPutDTO userDTO) {
        EmployeePerfil employeePerfil = convertEmployeeRequestPutDTOToEmployeeEntity(userDTO);
        PerfilOngRequestGetDTO perfilLogged = userLoggedService.getPerfilLogged();

        if (employeePerfil.getId() == 0 || perfilLogged.getId() != employeePerfil.getId() ){
            throw new UserNotFoundException("ID not found");
        }


        EmployeePerfil emplloyeeOld = userRepository.findByIdEmplloyee(employeePerfil.getId());

        if (emplloyeeOld == null){
            throw new UserNotFoundException("ID user not found");
        }

        checkoutIfExistsOng(employeePerfil);

        List<Ong> ongs = ongService.findIn(userDTO.getOngs());
        checkFieldsFromUser(employeePerfil, false);
        employeePerfil.setOngs(ongs);
        employeePerfil.setPassword(emplloyeeOld.getPassword());
        userRepository.save(employeePerfil);
        logSystemService.createLog(LogSystem.UPDATE_EMPLOYEES,employeePerfil.getOngEmployeeId(), userLoggedService.getUserLogged().get().getId(), "update employee");

    }

    @Override
    public void updatePasswordUserCurrent(String password) {
        Perfil userLogged = userLoggedService.getUserLogged().get();
        userLogged.setPassword(passwordEncoder.encode(password));
        checkFieldsFromUser(userLogged, false);
        userRepository.save(userLogged);
        EmployeeRequestGetDTO employeeRequestGetDTO = findById(userLogged.getId());
        logSystemService.createLog(LogSystem.UPDATE_EMPLOYEES,employeeRequestGetDTO.getOngEmployeeId(), employeeRequestGetDTO.getId(), "update employee");
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
            EmployeeRequestGetDTO employeeRequestGetDTO = findById(userLogged.get().getId());
            logSystemService.createLog(LogSystem.DELETE_EMPLOYEES, employeeRequestGetDTO.getOngEmployeeId(), employeeRequestGetDTO.getId(), "delete empĺoyee "+employeeRequestGetDTO.getName());
            userRepository.delete(userLogged.get());
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUserById(long id) {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        if(userLogged.isPresent() && userLogged.get().getDecriminatorValue().isAdmin()){

            EmployeeRequestGetDTO employeeRequestGetDTO = findById(id);
            logSystemService.createLog(LogSystem.DELETE_EMPLOYEES, employeeRequestGetDTO.getOngEmployeeId(), userLoggedService.getUserLogged().get().getId(), "delete empĺoyee "+employeeRequestGetDTO.getName());

            userRepository.deleteById(id);

        }
    }

    @Override
    public EmployeeJwtToken login(String email, String password) {
        TokenJwtDTO authenticateAndGenerateToken = authenticateService.authenticateAndGenerateToken(email,
                password);
        long id = authenticateAndGenerateToken.getUserDTO().getId();
        EmployeeRequestGetDTO employeeRequestGetDTO = findById(id);

        if(employeeRequestGetDTO.getOngEmployeeId() != 0){
                logSystemService.createLog(LogSystem.LOGIN, employeeRequestGetDTO.getOngEmployeeId() , employeeRequestGetDTO.getId(), "novo login user employee "+employeeRequestGetDTO.getName());
        }

        return EmployeeJwtToken.builder().token(authenticateAndGenerateToken.getToken()).expire(authenticateAndGenerateToken.getExpire()).userDTO(employeeRequestGetDTO).build();
    }

    @Override
    public void saveImagePerfil(String fileName, MultipartFile multipartFile) {
        Perfil userLogged = userLoggedService.getUserLogged().get();
        String newImageURL = userLogged.getImageURL();
        try {
            newImageURL = fileUpload.saveFile(fileName, multipartFile);
        } catch (IOException e) {
            throw new OperationException(e);
        }

        userLogged.setImageURL(newImageURL);
        checkFieldsFromUser(userLogged, false);
        userRepository.save(userLogged);

        EmployeeRequestGetDTO employeeRequestGetDTO = findById(userLogged.getId());
        logSystemService.createLog(LogSystem.UPDATE_EMPLOYEES,employeeRequestGetDTO.getOngEmployeeId(), employeeRequestGetDTO.getId(), "update employee "+employeeRequestGetDTO.getName());

    }
}
