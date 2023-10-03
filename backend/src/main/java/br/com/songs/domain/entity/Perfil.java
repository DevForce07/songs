package br.com.songs.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "perfil")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode()
@DiscriminatorColumn(name="type_perfil",
        discriminatorType = DiscriminatorType.STRING)
public class Perfil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String document;


    @Transient
    public PerfilDecriminator getDecriminatorValue() {
        String value = this.getClass().getAnnotation(DiscriminatorValue.class).value();
        PerfilDecriminator decriminator;

        if("A".equals(value)){
            decriminator = PerfilDecriminator.ADMIN;
        }else{
            decriminator = PerfilDecriminator.EMPLOYEE;
        }

        return decriminator;
    }

    public enum PerfilDecriminator{
        ADMIN,EMPLOYEE;

        public boolean isAdmin(){
            return ADMIN.equals(this);
        }
        public boolean isEmployee(){
            return EMPLOYEE.equals(this);
        }
    }

    public List<Ong> getOngs(){
        return null;
    }

}
