package br.com.songs.services.ong;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.entity.ActingArea;
import br.com.songs.domain.entity.Ong;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.OngNotFoundException;
import br.com.songs.exception.OperationException;
import br.com.songs.exception.UserNotFoundException;
import br.com.songs.repository.ActingAreaRepository;
import br.com.songs.repository.OngRepository;
import br.com.songs.services.audit.LogSystemService;
import br.com.songs.services.file.FileUploadServiceImpl;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.acting.ActingAreaDTO;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.songs.converter.ong.OngConverter.*;

@Service
@AllArgsConstructor
@Data
public class OngServiceImpl implements OngService {
    @Autowired
    private OngRepository repository;
    @Autowired
    private UserLoggedService userLoggedService;
    @Autowired
    private ActingAreaRepository actingAreaRepository;
    @Autowired
    private LogSystemService logSystemService;
    @Autowired
    private FileUploadServiceImpl fileUpload;

    @Override
    public OngRequestGetDTO findById(long id) {
        OngRequestGetDTO ongRequestGetDTO = convertOngEntity(findOngEntityById(id));
        return ongRequestGetDTO;
    }

    @Override
    public List<Ong> findIn(List<Long> id) {
        if(id == null){
            return new ArrayList<>();
        }

        return repository.findAllById(id);
    }
    @Override
    public Ong findOngEntityById(long id) {
        Optional<Ong> ongOptional = repository.findById(id);

        Ong ong = ongOptional.orElseThrow(() -> new OngNotFoundException("Ong not found using id " + id));

        return ong;
    }

    @Override
    public List<OngRequestGetDTO> findAll(Pageable pageable) {
        List<Ong> ongList = repository.findAll(pageable).getContent();
        if(ongList == null || ongList.isEmpty()){
            return new ArrayList<>();
        }
        return convertOngEntity(ongList);
    }

    @Override
    public OngRequestGetDTO saveOng(OngRequestPostDTO ongRequestPostDTO) {

        Ong ong = convertOngRequestPostDTOToOngEntity(ongRequestPostDTO);

        checkFieldsFromOng(ong);

        Optional<Perfil> userLogged = userLoggedService.getUserLogged();

        if(userLogged.isEmpty() || !isPerfilLoggedIsAdmin(userLogged.get())){
            throw new UserNotFoundException("User admin not found");
        }

        Optional<ActingArea> areaOptional = actingAreaRepository.findById(ongRequestPostDTO.getActingArea());

        if(areaOptional.isEmpty()){
            throw new OperationException("Acting area not found or is null");
        }

        ong.setActingArea(areaOptional.get());
        ong.setPerfil(userLoggedService.getUserLogged().get());
        Ong saved = repository.save(ong);
        Perfil perfil = userLoggedService.getUserLogged().get();
        boolean isAdmin = isPerfilLoggedIsAdmin(perfil);
        logSystemService.createLog(LogSystem.CREATE_ONG,ong.getId(), userLoggedService.getUserLogged().get().getId(), "create new ong: "+ong.getName()+" by user "+(isAdmin ? "admin":"employee")+perfil.getName());

        return convertOngEntity(saved);
    }

    @Override
    public void saveImageOng(long idOng, String fileName, MultipartFile multipartFile) {

        Optional<Perfil> userLogged = userLoggedService.getUserLogged();

        if(userLogged.isEmpty() || !isPerfilLoggedIsAdmin(userLogged.get())){
            throw new UserNotFoundException("User admin not found");
        }

        Optional<Ong> ongOptional = repository.findById(idOng);

        if(ongOptional.isEmpty()){
            throw  new OperationException("ong not found");
        }

        Ong ong = ongOptional.get();

        String newImageURL = ong.getUrlImage();
        try {
            newImageURL = fileUpload.saveFile(fileName, multipartFile);
        } catch (IOException e) {
            throw new OperationException(e);
        }

        ong.setUrlImage(newImageURL);

        repository.save(ong);


        Perfil perfil = userLoggedService.getUserLogged().get();
        boolean isAdmin = isPerfilLoggedIsAdmin(perfil);
        logSystemService.createLog(LogSystem.UPDATE_ONG, ong.getId(),userLoggedService.getUserLogged().get().getId(), "update ong "+ong.getName()+" by user "+(isAdmin ? "admin":"employee")+perfil.getName());
    }

    @Override
    public void updateOng(OngRequestPutDTO ongRequestPutDTO) {
        OngRequestGetDTO requestGetDTO = findById(ongRequestPutDTO.getId());

        Perfil perfilLogged = userLoggedService.getUserLogged().get();

        if(perfilLogged.getOngs() != null &&!perfilLogged.getOngs().stream().map(Ong::getId).anyMatch(id -> Long.compare(id, requestGetDTO.getId()) == 0)){
            throw new OperationException("operation not permitted");
        }

        Ong ong = convertOngRequestPutDTOToOngEntity(ongRequestPutDTO);

        checkFieldsFromOng(ong);

        Optional<ActingArea> ongOptional = actingAreaRepository.findById(ongRequestPutDTO.getActingArea());

        if(ongOptional.isEmpty()){
            throw new OperationException("Acting area not found or is null");
        }

        ong.setActingArea(ongOptional.get());
        ong.setPerfil(userLoggedService.getUserLogged().get());

        repository.save(ong);

        Perfil perfil = userLoggedService.getUserLogged().get();
        boolean isAdmin = isPerfilLoggedIsAdmin(perfil);
        logSystemService.createLog(LogSystem.UPDATE_ONG, ong.getId(),userLoggedService.getUserLogged().get().getId(), "update ong "+ong.getName()+" by user "+(isAdmin ? "admin":"employee")+perfil.getName());
    }

    @Override
    public void deleteOngById(long id) {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        OngRequestGetDTO requestGetDTO = findById(id);

        if(userLogged.get().getOngs() != null && !userLogged.get().getOngs().stream().map(Ong::getId).anyMatch(idOng -> Long.compare(idOng, id) == 0)){
            throw new OperationException("operation not permitted");
        }

        if(userLogged.isEmpty() || !isPerfilLoggedIsAdmin(userLogged.get())){
            throw new UserNotFoundException("User admin not found");
        }

        Perfil perfil = userLoggedService.getUserLogged().get();
        boolean isAdmin = isPerfilLoggedIsAdmin(perfil);
        logSystemService.createLog(LogSystem.DELETE_ONG, requestGetDTO.getId(),userLoggedService.getUserLogged().get().getId(), "delete ong "+requestGetDTO.getName()+" by user "+(isAdmin ? "admin":"employee")+perfil.getName());
        repository.deleteById(requestGetDTO.getId());

    }

    private static boolean isPerfilLoggedIsAdmin(Perfil perfil) {
        return perfil.getDecriminatorValue().isAdmin();
    }

    private List<OngRequestGetDTO> convertOngEntity(List<Ong> ong){
        return ong.stream().map(this::convertOngEntity).collect(Collectors.toList());
    }

    private OngRequestGetDTO convertOngEntity(Ong ong){
        OngRequestGetDTO ongRequestGetDTO = convertOngEntityToOngRequestGetDTO(ong);
        Optional<ActingArea> ongOptional = actingAreaRepository.findById(ong.getActingArea().getId());
        ActingAreaDTO actingAreaDTO = convertActingAreaEntityToActingAreaDTO(ongOptional.get());
        ongRequestGetDTO.setActingArea(actingAreaDTO);
        return ongRequestGetDTO;
    }
}
