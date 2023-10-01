package br.com.songs.domain.entity;

import br.com.songs.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "vacancies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Vacancies extends BaseEntity {
	private String title;
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "id_ong", referencedColumnName = "id")
	})
	private Ong ong;
	private String description;

	@Column(name = "qtd_vacacies")
	private int qtdVacacies;

	@CreationTimestamp()
	private Instant createdOn;

	@UpdateTimestamp()
	private Instant lastUpdatedOn;
}
