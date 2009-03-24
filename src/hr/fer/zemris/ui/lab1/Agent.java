package hr.fer.zemris.ui.lab1;

import java.awt.Point;
import java.util.HashSet;
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
	 * Zapisivanje obilježja polja.
	 * 
	 * @param koordinata Koordinata na kojoj se polje nalazi.
	 */
	private void zabiljezi(Point koordinata) {
		Polje polje = this.svijet.opisi(koordinata);
		
		// TODO: Dodaj opis u skupove (sa papira "Baza znanja") (radi Teta Vedrana)
	}
	
	public void pomakniSe() {
		if (!zivim) {
			throw new IllegalStateException("Agent je mrtav :'(");
		}
		// TODO: Izvedi po prioritetu kretanja. (radi Teta Vedrana)
		svijet.posjetiPolje(this, 5, 1);
		
		promotriOkolis();
	}
	
	private void odiNaPolje(int x, int y) {
		// TODO: Micanje agenta do nekog polja po posjećenim poljima.
	}
	
	public void ubij() {
		this.zivim = false;
	}
}
