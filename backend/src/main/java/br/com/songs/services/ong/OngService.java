package br.com.songs.services.ong;

import br.com.songs.domain.entity.Ong;
import br.com.songs.web.dto.ong.OngRequestGetDTO;
import br.com.songs.web.dto.ong.OngRequestPostDTO;
import br.com.songs.web.dto.ong.OngRequestPutDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OngService {
    OngRequestGetDTO findById(long id);

    List<Ong> findIn(List<Long> id);

    Ong findOngEntityById(long id);
    List<OngRequestGetDTO> findAll(Pageable pageable);

    OngRequestGetDTO saveOng(OngRequestPostDTO ongRequestPostDTO);

    void saveImageOng(long idOng, String fileName, MultipartFile multipartFile);

    void updateOng(OngRequestPutDTO ongRequestPutDTO);

    void deleteOngById(long id);
}
