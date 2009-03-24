package hr.fer.zemris.ui.lab1;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Realizacija agenta.
 */
public class Agent {
	private Set<Point> posjecenaPolja;
	private Set<Point> sigurnaNeposjecenaPolja;
	private Set<Point> smrdljivaPolja;
	private Set<Point> vjetrovitaPolja;
	private Set<Point> sjajnaPolja;
	private Set<Point> potencijalneJame;
	private Set<Point> potencijalnaCudovista;
	private Set<Point> potencijalnaZlata;
	private Svijet svijet;
	private Point pozicija;
	private boolean zivim;
	private boolean nasaoZlato;
	
	public Agent(Svijet svijet) {
		
		posjecenaPolja = new HashSet<Point>();
		sigurnaNeposjecenaPolja = new HashSet<Point>();
		smrdljivaPolja = new HashSet<Point>();
		vjetrovitaPolja = new HashSet<Point>();
		sjajnaPolja = new HashSet<Point>();
		potencijalneJame = new HashSet<Point>();
		potencijalnaCudovista = new HashSet<Point>();
		potencijalnaZlata = new HashSet<Point>();
		this.svijet = svijet;
		this.zivim = true;
		this.nasaoZlato = false;
		this.pozicija = new Point(1,1);
		promotriOkolis();
	}

	private void promotriOkolis() {
		posjecenaPolja.add(pozicija);
		Point koordinata;
		
		koordinata = new Point(pozicija.x+1, pozicija.y);
		if (!posjecenaPolja.contains(koordinata)
				&& svijet.postojiPolje(koordinata)) {
			zabiljezi(koordinata);
		}
		
		koordinata = new Point(pozicija.x-1, pozicija.y);
		if (!posjecenaPolja.contains(koordinata)
				&& svijet.postojiPolje(koordinata)) {
			zabiljezi(koordinata);
		}
		
		koordinata = new Point(pozicija.x, pozicija.y+1);
		if (!posjecenaPolja.contains(koordinata)
				&& svijet.postojiPolje(koordinata)) {
			zabiljezi(koordinata);
		}
		
		koordinata = new Point(pozicija.x, pozicija.y-1);
		if (!posjecenaPolja.contains(koordinata)
				&& svijet.postojiPolje(koordinata)) {
			zabiljezi(koordinata);
		}
	}
	
	/**
	 * Zapisivanje obiljezja polja - obogacivanje baze znanja.
	 * 
	 * @param koordinata Koordinata polja na kojem se agent trenutno nalazi.
	 */
	private void zabiljezi(Point koordinata) {
		
		Polje polje = this.svijet.opisi(koordinata);
		
		Point koordinataGore = new Point (koordinata.x, koordinata.y+1);
		Point koordinataDolje = new Point (koordinata.x, koordinata.y-1);
		Point koordinataLijevo = new Point (koordinata.x-1, koordinata.y);
		Point koordinataDesno = new Point (koordinata.x+1, koordinata.y);		
		
		// Ne propuhuje, ne smrdi, ne sjaji.
		if (!polje.isPropuhuje() && !polje.isSmrdi() && !polje.isSjaji()) {			
			sigurnaNeposjecenaPolja.add(koordinata);
					
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
				}						
		}
		
		// Ne propuhuje, ne smrdi, ali sjaji!
		// Evidencija o poljima na kojima se mozda nalazi zlato.
		if (!polje.isPropuhuje() && !polje.isSmrdi() && polje.isSjaji()) {
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalnaZlata.add(koordinataGore);
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalnaZlata.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalnaZlata.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalnaZlata.add(koordinataDesno);
				}			
		}
		
		
		// Ne propuhuje, smrdi, ne sjaji!
		// Evidencija o poljima na kojima se mozda nalazi cudoviste.
		if (!polje.isPropuhuje() && polje.isSmrdi() && !polje.isSjaji()) {
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalnaCudovista.add(koordinataGore);					
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalnaCudovista.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalnaCudovista.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalnaCudovista.add(koordinataDesno);
				}			
		}
		
		// Ne propuhuje, ali smrdi i sjaji!
		// Evidencija o poljima na kojima se mozda nalazi cudoviste i zlato.
		if (!polje.isPropuhuje() && polje.isSmrdi() && polje.isSjaji()) {
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalnaCudovista.add(koordinataGore);
					potencijalnaZlata.add(koordinataGore);					
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalnaCudovista.add(koordinataDolje);
					potencijalnaZlata.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalnaCudovista.add(koordinataLijevo);
					potencijalnaZlata.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalnaCudovista.add(koordinataDesno);
					potencijalnaZlata.add(koordinataDesno);
				}			
		}
		
		// Propuhuje, ali ne smrdi i ne sjaji!
		// Evidencija o poljima na kojima se mozda nalazi jama.
		if (polje.isPropuhuje() && !polje.isSmrdi() && !polje.isSjaji()){
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalneJame.add(koordinataGore);
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalneJame.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalneJame.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalneJame.add(koordinataDesno);
				}					
		}
		
		// Propuhuje, ne smrdi, ali sjaji!
		// Evidencija o poljima na kojima se mozda nalazi jama i zlato.
		if (polje.isPropuhuje() && !polje.isSmrdi() && polje.isSjaji()){
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalneJame.add(koordinataGore);
					potencijalnaZlata.add(koordinataGore);
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalneJame.add(koordinataDolje);
					potencijalnaZlata.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalneJame.add(koordinataLijevo);
					potencijalnaZlata.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalneJame.add(koordinataDesno);
					potencijalnaZlata.add(koordinataDesno);
				}					
		}
		
		// Propuhuje, smrdi, ali ne sjaji!
		// Evidencija o poljima na kojima se mozda nalazi jama i cudoviste.
		if (polje.isPropuhuje() && polje.isSmrdi() && !polje.isSjaji()){
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalneJame.add(koordinataGore);
					potencijalnaCudovista.add(koordinataGore);
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalneJame.add(koordinataDolje);
					potencijalnaCudovista.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalneJame.add(koordinataLijevo);
					potencijalnaCudovista.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalneJame.add(koordinataDesno);
					potencijalnaCudovista.add(koordinataDesno);
				}					
		}
		
		// Propuhuje, smrdi i sjaji ZOMG
		// Evidencija o poljima na kojima se mozda nalazi jama, cudoviste i zlato.
		if (polje.isPropuhuje() && polje.isSmrdi() && polje.isSjaji()){
			sigurnaNeposjecenaPolja.add(koordinata);
			
			//provjera susjednih neposjecenih i dodavanje u skupove baze znanja
				if (!posjecenaPolja.contains(koordinataGore) && svijet.postojiPolje(koordinataGore)){
					sigurnaNeposjecenaPolja.add(koordinataGore); 
					potencijalneJame.add(koordinataGore);
					potencijalnaCudovista.add(koordinataGore);
					potencijalnaZlata.add(koordinataGore);
				}
				if (!posjecenaPolja.contains(koordinataDolje) && svijet.postojiPolje(koordinataDolje)){
					sigurnaNeposjecenaPolja.add(koordinataDolje);
					potencijalneJame.add(koordinataDolje);
					potencijalnaCudovista.add(koordinataDolje);
					potencijalnaZlata.add(koordinataDolje);
				}					
				if (!posjecenaPolja.contains(koordinataLijevo) && svijet.postojiPolje(koordinataLijevo)){
					sigurnaNeposjecenaPolja.add(koordinataLijevo);
					potencijalneJame.add(koordinataLijevo);
					potencijalnaCudovista.add(koordinataLijevo);
					potencijalnaZlata.add(koordinataLijevo);
				}				
				if (!posjecenaPolja.contains(koordinataDesno) && svijet.postojiPolje(koordinataDesno)){
					sigurnaNeposjecenaPolja.add(koordinataDesno);
					potencijalneJame.add(koordinataDesno);
					potencijalnaCudovista.add(koordinataDesno);
					potencijalnaZlata.add(koordinataDesno);
				}					
		}
	}
	
	public void pomakniSe() {
		if (!zivim) {
			throw new IllegalStateException("Agent je mrtav :'(");
		}
		// TODO: Izvedi po prioritetu kretanja. (radi Teta Vedrana)
		svijet.posjetiPolje(this, 5, 1);
		
		promotriOkolis();
	}
	
	//!!! ATT PAZI VIDI BITNO VAŽNO
	// Pri svakom mijenjanju pozicije, molim Vas, koristite ovu metodu.
	private void promijeniPoziciju(int x, int y) {
		this.pozicija.setLocation(x, y);
		// TODO: obavijesti sve o promjeni...
	}
	
	public Point getPozicija() {
		return this.pozicija;
	}
	
	/**
	 * Pomiče agenta na neko posjećeno polje .
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean odiNaPolje(int x, int y) {
		List<Point> put = new LinkedList<Point>();
		Set<Point> posjecene = new HashSet<Point>();
		
		Point odrediste = new Point(x, y);
		if (pomak(pozicija.x, pozicija.y, odrediste, put, posjecene)) {
			for (Point p : put) {
				promijeniPoziciju(p.x, p.y);
			}
			return false;
			
		} else {
			return true;
		}
	}
	
	// Ogromna i ružna metoda, al što'š...
	private boolean pomak(int x, int y, Point odrediste, List<Point> put,
			Set<Point> posjecene) {
		
		Point radna = new Point(x, y);
		posjecene.add(radna);
			
		radna.x = x+1;
		radna.y = y;
		if (radna.equals(odrediste)) {
			put.add(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (pomak(x+1, y, odrediste, put, posjecene)) {
				put.add(radna);
				return true;
			}
		}
		
		radna.x = x-1;
		radna.y = y;
		if (radna.equals(odrediste)) {
			put.add(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (pomak(x-1, y, odrediste, put, posjecene)) {
				put.add(radna);
				return true;
			}
		}
		
		radna.x = x;
		radna.y = y+1;
		if (radna.equals(odrediste)) {
			put.add(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (pomak(x, y+1, odrediste, put, posjecene)) {
				put.add(radna);
				return true;
			}
		}
		
		radna.x = x;
		radna.y = y-1;
		if (radna.equals(odrediste)) {
			put.add(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (pomak(x, y-1, odrediste, put, posjecene)) {
				put.add(radna);
				return true;
			}
		}
		
		return false;
	}
	
	// Pokušao implementirati A*... skužio da je overkill^overkill pri čemu "^" nije XOR
	// Ostavljeno tek tako da ne pokušaš slično ;D
//	private boolean odiNaPolje(int x, int y) {
//		Point radna = new Point(pozicija);
//		Point odrediste = new Point(x, y);
//		Set<UdaljenaTocka> otvorene = new TreeSet<UdaljenaTocka>();
//		List<UdaljenaTocka> zatvorene = new LinkedList<UdaljenaTocka>();
//		
//		otvorene.add(new UdaljenaTocka(radna, 0, 0));
//		Point[] susjedi = new Point[4];
//		while (!otvorene.isEmpty()) {
//			UdaljenaTocka tocka = otvorene.iterator().next();
//			otvorene.iterator().remove();
//			radna = tocka.getTocka();
//			if (radna.equals(odrediste)) {
//				zatvorene.add(tocka);
//				break;
//			}
//			
//			susjedi[0].x = radna.x+1;
//			susjedi[0].y = radna.y;
//			susjedi[1].x = radna.x-1;
//			susjedi[1].y = radna.y;
//			susjedi[2].x = radna.x;
//			susjedi[2].y = radna.y+1;
//			susjedi[3].x = radna.x;
//			susjedi[3].y = radna.y-1;
//			
//			for (int i = 0; i < susjedi.length; i++) {
//				if (posjecenaPolja.contains(susjedi[i])) {
//					for (UdaljenaTocka zatvor : zatvorene) {
//						if (zatvor.equals(susjedi[i])) {
//							if ()
//						}
//					}
//				}
//			}
//			
//		}
//		
//		return true;
//		
//	}
	
	public void ubij() {
		this.zivim = false;
	}
	
//	private class UdaljenaTocka implements Comparable<UdaljenaTocka> {
//		private Point tocka;
//		private int udaljenostPocetka;
//		private int udaljenostHeuristika;
//		
//		public UdaljenaTocka(Point tocka, int udaljenostPocetka,
//				int udaljenostHeuristika) {
//			super();
//			this.tocka = tocka;
//			this.udaljenostPocetka = udaljenostPocetka;
//			this.udaljenostHeuristika = udaljenostHeuristika;
//		}
//
//		@Override
//		public int compareTo(UdaljenaTocka o) {
//			return (this.udaljenostHeuristika+this.udaljenostPocetka)
//					- (o.getUdaljenostHeuristika()+o.getUdaljenostPocetka());
//		}
//
//		public Point getTocka() {
//			return tocka;
//		}
//
//		public int getUdaljenostPocetka() {
//			return udaljenostPocetka;
//		}
//
//		public int getUdaljenostHeuristika() {
//			return udaljenostHeuristika;
//		}
//
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + getOuterType().hashCode();
//			result = prime * result + ((tocka == null) ? 0 : tocka.hashCode());
//			return result;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (getClass() != obj.getClass())
//				return false;
//			UdaljenaTocka other = (UdaljenaTocka) obj;
//			if (!getOuterType().equals(other.getOuterType()))
//				return false;
//			if (tocka == null) {
//				if (other.tocka != null)
//					return false;
//			} else if (!tocka.equals(other.getTocka()))
//				return false;
//			return true;
//		}
//
//		private Agent getOuterType() {
//			return Agent.this;
//		}
//		
//	}
}
