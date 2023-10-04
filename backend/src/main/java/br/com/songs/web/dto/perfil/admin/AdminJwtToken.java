package br.com.songs.web.dto.perfil.admin;

import br.com.songs.web.dto.perfil.generic.PerfilOngRequestGetDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminJwtToken {
    private String token;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime expire;

    private AdminOngRequestGetDTO userDTO;
}
