package br.com.songs.services.audit;

import br.com.songs.converter.audit.LogConverter;
import br.com.songs.domain.audit.LogSystem;
import br.com.songs.domain.audit.Logs;
import br.com.songs.domain.entity.Perfil;
import br.com.songs.exception.OperationException;
import br.com.songs.repository.LogsRepository;
import br.com.songs.services.user.login.UserLoggedService;
import br.com.songs.web.dto.audit.LogDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class LogSystemServiceImpl implements LogSystemService{
    @Autowired
    private LogsRepository repository;
    @Autowired
    private UserLoggedService userLoggedService;

    @Override
    public void createLog(LogSystem logSystem, long idOng, long idUSer, String message) {
        Logs log = Logs.builder().idUSer(idUSer).ongId(idOng).message(message).logSystem(logSystem).build();
        repository.save(log);
    }

    @Override
    public List<LogDTO> findByIdOng(long id, Pageable pageable) {
        Optional<Perfil> userLogged = userLoggedService.getUserLogged();

        if(userLogged.isEmpty() || userLogged.get().getDecriminatorValue().isEmployee()){
            throw new OperationException("unauthorized user");
        }

        List<Logs> allByOngId = repository.findAllByOngId(id, pageable);
        return LogConverter.convertLogToDTO(allByOngId);
    }
}
