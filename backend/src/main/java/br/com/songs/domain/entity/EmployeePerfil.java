package br.com.songs.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@DiscriminatorValue("E")
public class EmployeePerfil extends Perfil{
    private LocalDate birthDate;

    private char sex;

    @Column(name = "ong_employee")
    private long ongEmployeeId;

    @Transient
    private List<Ong> ongs;

    @Override
    public List<Ong> getOngs(){
        return ongs == null ? new ArrayList<>():ongs;
    }
}
