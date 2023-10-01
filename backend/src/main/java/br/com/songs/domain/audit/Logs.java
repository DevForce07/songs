package br.com.songs.domain.audit;

import br.com.songs.domain.BaseEntity;
import br.com.songs.domain.entity.Ong;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "logs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Logs extends BaseEntity {
    @Column(name = "id_ong")
    private long ongId;

    @Column(name = "log_system")
    @Enumerated(EnumType.STRING)
    private LogSystem logSystem;

    @Column(name = "id_user")
    private long idUSer;

    private String message;

    @CreationTimestamp()
    private Instant createdOn;

}
