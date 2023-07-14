package App;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import DAO.CatalogoDAO;
import DAO.PrestitoDAO;
import DAO.UtenteDAO;
import Entities.Catalogo;
import Entities.Libri;
import Entities.Periodicita;
import Entities.Prestito;
import Entities.Riviste;
import Entities.Utente;
import Util.JpaUtil;

public class App {

	private static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	public static void main(String[] args) {

		EntityManager em = emf.createEntityManager();

		CatalogoDAO cd = new CatalogoDAO(em);

		// Creo catalogo
		Set<Catalogo> catalogo = new HashSet<>();

		// Creo Libri
		Libri libro1 = new Libri(11111L, "Harry Potter", LocalDate.of(1998, 10, 2), 328, "Autore1", "Fantasy");
		Libri libro2 = new Libri(22222L, "Il Signore degli Anelli: La Compagnia dell'Anello",
				LocalDate.of(1954, 7, 29), 423, "J.R.R. Tolkien", "Fantasy");
		Libri libro3 = new Libri(33333L, "Orgoglio e pregiudizio", LocalDate.of(1813, 1, 28), 416,
				"Jane Austen", "Romanzo romantico");

		// Creo Riviste
		Riviste rivista1 = new Riviste(44444L, "National Geographic", LocalDate.of(1888, 10, 1), 150,
				Periodicita.MENSILE);
		Riviste rivista2 = new Riviste(55555L, "Vanity Fair", LocalDate.of(1913, 9, 1), 100,
				Periodicita.SEMESTRALE);
		Riviste rivista3 = new Riviste(66666L, "Focus", LocalDate.of(1992, 1, 1), 80, Periodicita.MENSILE);

		// aggiungo a catalogo
		catalogo.add(libro1);
		catalogo.add(libro2);
		catalogo.add(libro3);
		catalogo.add(rivista1);
		catalogo.add(rivista2);
		catalogo.add(rivista3);

		// forEach
		catalogo.forEach(el -> {
			cd.save(el);
		});

		// RIMUOVI CON isbn
		cd.removeByISBN(11111L);

		// Cerca CON isbn
		Catalogo findIsbn = cd.findByISBN(55555L);
		if (findIsbn != null) {
			System.out.println("Elemento trovato: \n" + findIsbn);
		} else {
			System.out.println("Elemento non trovato");
		}

		// Ricerca per anno pubblicazione
		List<Catalogo> findAnno = cd.findByAnnoPubblicazione(1992);
		if (!findAnno.isEmpty()) {
			System.out.println("Elementi trovati:");
			for (Catalogo elementi : findAnno) {
				System.out.println(elementi);
			}
		} else {
			System.out.println("Nessun elemento trovato");
		}

		// ricerca per autore
		List<Catalogo> findAutore = cd.findByAutore("J.R.R. Tolkien");
		if (!findAutore.isEmpty()) {
			System.out.println("Elementi trovati:");
			for (Catalogo elementi : findAutore) {
				System.out.println(elementi);
			}
		} else {
			System.out.println("Nessun elemento trovato");
		}

		// ricerca per titolo
		List<Catalogo> findTitolo = cd.findByTitolo("Orgoglio e pregiudizio");
		if (!findTitolo.isEmpty()) {
			System.out.println("Elementi trovati:");
			for (Catalogo elementi : findTitolo) {
				System.out.println(elementi);
			}
		} else {
			System.out.println("Nessun elemento trovato");
		}


		UtenteDAO ud = new UtenteDAO(em);
		Utente utente1 = new Utente("Benedetto", "De Stefano", LocalDate.of(1990, 5, 15), "12345");
		Utente utente2 = new Utente("Francesco", "Migliaccio", LocalDate.of(1997, 11, 15), "54321");

		ud.save(utente1);
		ud.save(utente2);

		PrestitoDAO pd = new PrestitoDAO(em);
		Prestito prestito1 = new Prestito(utente1, LocalDate.now(), LocalDate.now().plusDays(14), null);
		Prestito prestito2 = new Prestito(utente1, LocalDate.now(), LocalDate.now().plusDays(14), null);

		pd.save(prestito1);
		pd.save(prestito2);

		Set<Prestito> prestitiByNumeroTessera = pd.findPrestitiByNumeroTessera(54321);

		Set<Prestito> prestitiScaduti = pd.findPrestitiScaduti();


	}


}
