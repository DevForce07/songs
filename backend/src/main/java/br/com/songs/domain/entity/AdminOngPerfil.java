package br.com.songs.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@SuperBuilder
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("A")
public class AdminOngPerfil extends Perfil {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "perfil", cascade = {CascadeType.MERGE, CascadeType.DETACH , CascadeType.REFRESH})
    private List<Ong> ongs;

    @Override
    public List<Ong> getOngs(){
        return ongs == null ? new ArrayList<>():ongs;
    }
}
