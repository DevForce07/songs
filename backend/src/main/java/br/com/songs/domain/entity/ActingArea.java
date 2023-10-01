package br.com.songs.domain.entity;

import br.com.songs.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "acting_areas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ActingArea extends BaseEntity {
	private String name;
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "id_ong", referencedColumnName = "id")
	})
	private Ong ong;
}
