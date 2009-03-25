package hr.fer.zemris.ui.lab1;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * Konstruktor.
	 * 
	 * @param svijet Svijet u kojem agent jest.
	 */
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
	
	/**
	 * Prikupljanje znanja o okolini.
	 */
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
		
		// trenutna koordinata na kojoj se nalazimo
		Point koordinata = new Point(pozicija.x, pozicija.y);
		Point sljedecaKoordinata = new Point();
		
		// koordinate polja gore, dolje, lijevo, desno
		Point koordinataGore = new Point (koordinata.x, koordinata.y+1);
		Point koordinataDolje = new Point (koordinata.x, koordinata.y-1);
		Point koordinataLijevo = new Point (koordinata.x-1, koordinata.y);
		Point koordinataDesno = new Point (koordinata.x+1, koordinata.y);
		
		// TODO: Izvedi po prioritetu kretanja. (radi Teta Vedrana)
		// prvo neposjecena susjedna
		// zatim neposjecena nesusjedna
		// vracam koordinate polja kojega posjecujemo u iducem koraku
		
				
	// prvotno susjedna polja !! 
		for (int i = 0; i <4; i++){
			
			Point susjedna = new Point();
			
			switch (i) {
			case 0:
				susjedna = koordinataGore;				
				break;
			case 1:
				susjedna = koordinataDolje;				
				break;
			case 2:
				susjedna = koordinataLijevo;				
				break;
			case 3:
				susjedna = koordinataDesno;				
				break;	
			default:
				break;
			}
			
			// sigurna i u skupu potencijalnaZlata
			if (sigurnaNeposjecenaPolja.contains(susjedna) && 
					potencijalnaZlata.contains(susjedna) &&
					!smrdljivaPolja.contains(susjedna) &&
					!vjetrovitaPolja.contains(susjedna)) { 
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
			}		
			// sigurna i u skupu sjajnih polja
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					sjajnaPolja.contains(susjedna) &&
					!smrdljivaPolja.contains(susjedna) &&
					!vjetrovitaPolja.contains(susjedna)){
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
			}
			// samo sigurna, bez propuha i smrada, ali i bez sjaja ili potencijalnog zlata
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					!smrdljivaPolja.contains(susjedna) &&
					!vjetrovitaPolja.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i u skupu potencijalnoga zlata 
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					potencijalnaZlata.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i skupu sjajnih
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					sjajnaPolja.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
			}		
		}
		
		// nakon toga nesusjedna, iz liste neposjecenih sigurnih, po istom proncipu
		
		// a NAKOG TOGA bilo koje iz liste potencijalnih jama ili cudovista
		
		// zasad u sljedecaKoordinata zapisujem koordinate novoga polja na koje zelimo ici
		// pa onda s njima pozivamo daljne funkcije
		
		
		svijet.posjetiPolje(this, 5, 1);
		
		promotriOkolis();
	}
	
	/**
	 * Mijenjanje pozicije agenta (i obavljanje svih poslova koji se pri
	 * pomaku moraju obaviti!).<br\>
	 * Obavezno koristiti ovu metodu, a ne direktno mijenjati poziciju.
	 * 
	 * @param x X koordinata pozicije na koju mičemo agenta.
	 * @param y Y koordinata pozicije na koju mičemo agenta.
	 */
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
	 * @param x X koordinata polja na koje želimo pomaknuti agenta.
	 * @param y Y koordinata polja na koje želimo pomaknuti agenta.
	 * @return True ako se agent uspješno pomaknuo na točku ({@code x}, {@code y}).
	 */
	private boolean odiNaPolje(int x, int y) {
		List<Point> put = new LinkedList<Point>();
		Set<Point> posjecene = new HashSet<Point>();
		
		Point odrediste = new Point(x, y);
		if (izgradnjaPuta(pozicija.x, pozicija.y, odrediste, put, posjecene)) {
			for (Point p : put) {
				promijeniPoziciju(p.x, p.y);
			}
			return false;
			
		} else {
			return true;
		}
	}
	
	/**
	 * Izgradnja puta od točke ({@code x}, {@code y}) do točke {@code odredište}.
	 * 
	 * @param x X koordinata točke od koje krećemo.
	 * @param y Y koordinata točke od koje krećemo.
	 * @param odrediste Odredišna točka.
	 * @param put Put koji smo prošli.
	 * @param posjecene Skup posjećenih točaka.
	 * @return True ako smo uspješno izgradili put.
	 */
	private boolean izgradnjaPuta(int x, int y, Point odrediste, List<Point> put,
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
			if (izgradnjaPuta(x+1, y, odrediste, put, posjecene)) {
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
			if (izgradnjaPuta(x-1, y, odrediste, put, posjecene)) {
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
			if (izgradnjaPuta(x, y+1, odrediste, put, posjecene)) {
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
			if (izgradnjaPuta(x, y-1, odrediste, put, posjecene)) {
				put.add(radna);
				return true;
			}
		}
		
		return false;
	}
	
	public void ubij() {
		this.zivim = false;
	}
}
