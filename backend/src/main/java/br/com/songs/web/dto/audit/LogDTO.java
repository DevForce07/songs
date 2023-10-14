package br.com.songs.web.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogDTO {
    private long id;

    private String logSystem;

    private String userName;

    private String message;

    private LocalDateTime dateTime;
}
