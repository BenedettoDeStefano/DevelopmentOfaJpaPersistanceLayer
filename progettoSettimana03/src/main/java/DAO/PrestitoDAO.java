package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
}
