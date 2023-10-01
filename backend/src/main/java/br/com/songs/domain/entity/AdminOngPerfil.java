package br.com.songs.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("A")
public class AdminOngPerfil extends Perfil {

}
