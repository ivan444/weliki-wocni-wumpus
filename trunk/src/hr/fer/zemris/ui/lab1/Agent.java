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
		promijeniPoziciju(1, 1);
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
		
		// koordinate polja na koje cemo se pomaknuti
		Point sljedecaKoordinata = new Point();
		
		// koordinate polja gore, dolje, lijevo, desno
		Point koordinataGore = new Point (koordinata.x, koordinata.y+1);
		Point koordinataDolje = new Point (koordinata.x, koordinata.y-1);
		Point koordinataLijevo = new Point (koordinata.x-1, koordinata.y);
		Point koordinataDesno = new Point (koordinata.x+1, koordinata.y);
		
		// TODO: Odaberi sljedece polje po prioritetu kretanja. 
		//
		// prvo neposjecena susjedna, sigurna
		// zatim neposjecena NEsusjedna, sigurna
		// zatim neposjecena nesigurna :(
		//
		// postavlja koordinate polja kojega agent posjecuje u iducem koraku u tocku
		// ---- Point sljedecaKoordinata ----
		
				
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
				break;
			}		
			// sigurna i u skupu sjajnih polja
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					sjajnaPolja.contains(susjedna) &&
					!smrdljivaPolja.contains(susjedna) &&
					!vjetrovitaPolja.contains(susjedna)){
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
				break;
			}
			// samo sigurna, bez propuha i smrada, ali i bez sjaja ili potencijalnog zlata
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					!smrdljivaPolja.contains(susjedna) &&
					!vjetrovitaPolja.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
				break;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i u skupu potencijalnoga zlata 
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					potencijalnaZlata.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
				break;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i skupu sjajnih
			else if (sigurnaNeposjecenaPolja.contains(susjedna) &&
					sjajnaPolja.contains(susjedna)) {
				sljedecaKoordinata.x = susjedna.x;
				sljedecaKoordinata.y = susjedna.y;
				break;
			}		
		}
		
		// nakon toga NESUSJEDNA, iz liste neposjecenih sigurnih, po istom principu:
		
		for (Point nesusjedna : sigurnaNeposjecenaPolja) {
			
			// sigurna i u skupu potencijalnaZlata
			if (potencijalnaZlata.contains(nesusjedna) &&
					!smrdljivaPolja.contains(nesusjedna) &&
					!vjetrovitaPolja.contains(nesusjedna)) { 
				sljedecaKoordinata.x = nesusjedna.x;
				sljedecaKoordinata.y = nesusjedna.y;
				break;
			}		
			// sigurna i u skupu sjajnih polja
			else if (sjajnaPolja.contains(nesusjedna) &&
					!smrdljivaPolja.contains(nesusjedna) &&
					!vjetrovitaPolja.contains(nesusjedna)){
				sljedecaKoordinata.x = nesusjedna.x;
				sljedecaKoordinata.y = nesusjedna.y;
				break;
			}
			// samo sigurna, bez propuha i smrada, ali i bez sjaja ili potencijalnog zlata
			else if (!smrdljivaPolja.contains(nesusjedna) &&
					!vjetrovitaPolja.contains(nesusjedna)) {
				sljedecaKoordinata.x = nesusjedna.x;
				sljedecaKoordinata.y = nesusjedna.y;
				break;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i u skupu potencijalnoga zlata 
			else if (potencijalnaZlata.contains(nesusjedna)) {
				sljedecaKoordinata.x = nesusjedna.x;
				sljedecaKoordinata.y = nesusjedna.y;
				break;
			}
			//sigurna i u skupu smrdljivih/vjetrovitih, ali i skupu sjajnih
			else if (sjajnaPolja.contains(nesusjedna)) {
				sljedecaKoordinata.x = nesusjedna.x;
				sljedecaKoordinata.y = nesusjedna.y;
				break;
			}			
		} 
		
				
		// a ako ni sada nismo nasli sigurno polje, agent odlazi na
		// bilo koje polje iz liste potencijalnih jama ili cudovista
		
		for (int i = 0; i <4; i++){
					
					Point susjedOpasno = new Point();
					
					switch (i) {
					case 0:
						susjedOpasno = koordinataGore;				
						break;
					case 1:
						susjedOpasno = koordinataDolje;				
						break;
					case 2:
						susjedOpasno = koordinataLijevo;				
						break;
					case 3:
						susjedOpasno = koordinataDesno;				
						break;	
					default:
						break;
					}
					
					if (svijet.postojiPolje(susjedOpasno) && 
							(potencijalnaCudovista.contains(susjedOpasno) || potencijalneJame.contains(susjedOpasno))) {
						sljedecaKoordinata.x = susjedOpasno.x;
						sljedecaKoordinata.y = susjedOpasno.y;
					}					
					
					// --- VAZNO!!! --- 
					// kad ode nasumicno na opasno polje - provjeriti je li mrtav !!
					// ako nije umro, maknuti to dosad opasno polje iz 
					// liste potencijalnihJama tj. potencijalnihCudovista
					// ako je umro, sprovod?
		}
				
			
		svijet.posjetiPolje(this, 5, 1);
		
		promotriOkolis(); //nakon svakog pomaka ponovno promatra okolis i obogacuje bazu znanja 	
	}
	
	/**
	 * Mijenjanje pozicije agenta (i obavljanje svih poslova koji se pri
	 * pomaku moraju obaviti!).<br\> (btw. komentari ne moraju biti XHTML valid ;D)
	 * Obavezno koristiti ovu metodu, a ne direktno mijenjati poziciju.
	 * 
	 * @param x X koordinata pozicije na koju mičemo agenta.
	 * @param y Y koordinata pozicije na koju mičemo agenta.
	 */
	private void promijeniPoziciju(int x, int y) {
		this.pozicija.setLocation(x, y);
		CentralnaInformacijskaAgencija.getCIA().obavijestiOPromjeni();
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
		CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Agent je mrtav");
		CentralnaInformacijskaAgencija.getCIA().obavijestiOPromjeni();
	}
}
