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

        if(userLogged.isEmpty() || !userLogged.get().getDecriminatorValue().isAdmin()){
            throw new UserNotFoundException("User admin not found");
        }

        Optional<ActingArea> areaOptional = actingAreaRepository.findById(ongRequestPostDTO.getActingArea());

        if(areaOptional.isEmpty()){
            throw new OperationException("Acting area not found or is null");
        }

        ong.setActingArea(areaOptional.get());
        ong.setPerfil(userLoggedService.getUserLogged().get());
        Ong saved = repository.save(ong);

        logSystemService.createLog(LogSystem.CREATE_ONG,ong.getId(), userLoggedService.getUserLogged().get().getId(), "criando uma nova ong");

        return convertOngEntity(saved);
    }

    @Override
    public void updateOng(OngRequestPutDTO ongRequestPutDTO) {
        OngRequestGetDTO requestGetDTO = findById(ongRequestPutDTO.getId());

        Perfil perfilLogged = userLoggedService.getUserLogged().get();

        if(!perfilLogged.getOngs().stream().map(Ong::getId).anyMatch(id -> Long.compare(id, requestGetDTO.getId()) == 0)){
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

        logSystemService.createLog(LogSystem.UPDATE_ONG, ong.getId(),userLoggedService.getUserLogged().get().getId(), "alterando ong");
    }

    @Override
    public void deleteOngById(long id) {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();
        OngRequestGetDTO requestGetDTO = findById(id);

        if(!userLogged.get().getOngs().stream().map(Ong::getId).anyMatch(idOng -> Long.compare(idOng, id) == 0)){
            throw new OperationException("operation not permitted");
        }

        if(userLogged.isEmpty() || !userLogged.get().getDecriminatorValue().isAdmin()){
            throw new UserNotFoundException("User admin not found");
        }
        repository.deleteById(requestGetDTO.getId());
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
