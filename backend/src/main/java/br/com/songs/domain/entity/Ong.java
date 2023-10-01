package br.com.songs.domain.entity;

import br.com.songs.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "ongs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Ong extends BaseEntity {

	private String name;

	@Column(unique = true)
	private String cnpj;

	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String address;

	@Column(nullable = true, name = "url_image")
	private String urlImage;
	
	@Column(nullable = true, name = "phone_number")
	private String phoneNumber;

	@Column(nullable = true, name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "id_acting_areas", referencedColumnName = "id")
	})
	private ActingArea actingArea;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ong", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	private List<Vacancies> vacancies;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumns({
			@JoinColumn(name = "id_perfil", referencedColumnName = "id")
	})
	private Perfil perfil;
}
