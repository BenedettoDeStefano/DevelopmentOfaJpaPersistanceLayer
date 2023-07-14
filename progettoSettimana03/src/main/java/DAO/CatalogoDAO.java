package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import Entities.Catalogo;


public class CatalogoDAO {

	private final EntityManager em;

	public CatalogoDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Catalogo c) {
		EntityTransaction t = em.getTransaction();
		t.begin();

		em.persist(c);

		t.commit();

		System.out.println("Elemento salvato");
	}


	public void removeByISBN(Long isbn) {
		EntityTransaction t = em.getTransaction();
		t.begin();

		Catalogo catalogo = em.find(Catalogo.class, isbn);
		if (catalogo != null) {
			em.remove(catalogo);
			System.out.println("Elemento rimosso");
		} else {
			System.out.println("Elemento non trovato");
		}

		t.commit();
	}

	public Catalogo findByISBN(Long isbn) {
		TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.isbn = :isbn", Catalogo.class);
		query.setParameter("isbn", isbn);
		List<Catalogo> result = query.getResultList();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public List<Catalogo> findByAnnoPubblicazione(int annoPubblicazione) {
		TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE YEAR(c.annoPubblicazione) = :anno",
				Catalogo.class);
		query.setParameter("anno", annoPubblicazione);
		return query.getResultList();
	}

	public List<Catalogo> findByAutore(String autore) {
		String queryString = "SELECT c FROM Catalogo c WHERE TYPE(c) = Libri AND c.autore = :autore";
		TypedQuery<Catalogo> query = em.createQuery(queryString, Catalogo.class);
		query.setParameter("autore", autore);
		return query.getResultList();
	}

	public List<Catalogo> findByTitolo(String titolo) {
		TypedQuery<Catalogo> query = em
				.createQuery("SELECT c FROM Catalogo c WHERE LOWER(c.titolo) LIKE LOWER(:titolo)", Catalogo.class);
		query.setParameter("titolo", "%" + titolo + "%");
		return query.getResultList();
	}


	
//	public List<Catalogo> findElementiInPrestitoByNumeroTessera(String numeroTessera) {
//		TypedQuery<Catalogo> query = em.createQuery(
//				"SELECT c FROM Catalogo c JOIN c.prestiti p JOIN p.utente u WHERE u.numeroTessera = :numeroTessera",
//				Catalogo.class);
//		query.setParameter("numeroTessera", numeroTessera);
//		return query.getResultList();
//	}
//
//	public List<Prestito> findPrestitiScadutiNonRestituiti() {
//		TypedQuery<Prestito> query = em.createQuery(
//				"SELECT p FROM Prestito p WHERE p.dataScadenza < :currentDate AND p.stato = 'NON_RESTITUITO'",
//				Prestito.class);
//		query.setParameter("currentDate", LocalDate.now());
//		return query.getResultList();
//	}

}
