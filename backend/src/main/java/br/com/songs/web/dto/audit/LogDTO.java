package br.com.songs.web.dto.audit;

import br.com.songs.domain.audit.LogSystem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogDTO {
    private long id;

    private String logSystem;

    private long idUSer;

    private String message;

    private LocalDateTime created;
}
