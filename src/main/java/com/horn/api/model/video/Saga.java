package com.horn.api.model.video;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="saga")
@AllArgsConstructor
@NoArgsConstructor
public class Saga {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	
	@OneToMany(
		    mappedBy = "saga",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
    @JsonIgnoreProperties("saga")
	private List<SagaPlaylists> maps = new ArrayList<SagaPlaylists>();
	
	public Saga(String title) {
		this.title = title;
	}
}