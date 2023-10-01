package br.com.songs.services.audit;

import br.com.songs.domain.audit.LogSystem;
import br.com.songs.web.dto.audit.LogDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LogSystemService {
    void createLog(LogSystem logSystem, long idOng, long idUSer, String message);

    List<LogDTO> findByIdOng(long id, Pageable pageable);
}
