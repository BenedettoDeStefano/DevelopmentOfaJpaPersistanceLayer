package Entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente {

	private String nome;
	private String cognome;
	private LocalDate dataNascita;

	@Id
	private String numeroTessera;

	@OneToMany(mappedBy = "utente")
	private Set<Prestito> prestiti;

	public Utente(String nome, String cognome, LocalDate dataNascita, String numeroTessera) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.numeroTessera = numeroTessera;
	}

}
