package DAO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import Entities.Prestito;

public class PrestitoDAO {

	private final EntityManager em;

	public PrestitoDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Prestito prestito1) {
		EntityTransaction t = em.getTransaction();
		t.begin();

		em.persist(prestito1);

		t.commit();

		System.out.println("Prestito salvato");
	}


	public Set<Prestito> findPrestitiByNumeroTessera(String string) {
		TypedQuery<Prestito> query = em.createQuery(
				"SELECT p FROM Prestito p JOIN p.utente u WHERE u.numeroTessera = :numeroTessera", Prestito.class);
		query.setParameter("numeroTessera", string);
		return new HashSet<>(query.getResultList());
	}


	public Set<Prestito> findPrestitiScaduti() {
		LocalDate today = LocalDate.now();
		TypedQuery<Prestito> query = em.createQuery(
				"SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :today AND p.dataRestituzioneEffettiva IS NULL",
				Prestito.class);
		query.setParameter("today", today);
		return new HashSet<>(query.getResultList());
	}
}
