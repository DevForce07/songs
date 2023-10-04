package br.com.songs.web.dto.perfil.employee;

import br.com.songs.web.dto.perfil.admin.AdminOngRequestGetDTO;
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
public class EmployeeJwtToken {
    private String token;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime expire;

    private EmployeeRequestGetDTO userDTO;
}
