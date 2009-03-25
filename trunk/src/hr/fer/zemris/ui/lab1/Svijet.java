package hr.fer.zemris.ui.lab1;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

/**
 * Reprezentacija svijeta.
 */
public class Svijet {
	private Map<Point, StvarnoPolje> mapaSvijeta;
	
	enum Opis {
		NE_POSTOJI,
		NISTA,
		SMRAD,
		SJAJ,
		PROPUH,
		ZLATO;
	}
	
	private int sirina;
	private int visina;
	
	public Svijet(int sirina, int visina, double pCudovista, double pJama) {
		mapaSvijeta = GeneratorSvijeta.stvoriSvijet(sirina, visina, pCudovista, pJama);
		this.visina = visina;
		this.sirina = sirina;
	}
	
	public Polje opisi(int x, int y) {
		return opisi(new Point(x, y));
	}
	
	public Polje opisi(Point koordinata) {
		return mapaSvijeta.get(koordinata).getPolje();
	}
	
	public boolean postojiPolje(int x, int y) {
		return postojiPolje(new Point(x, y));
	}
	
	/**
	 * Postoji li polje u svijetu.
	 * 
	 * @param koordinata
	 * @return True ako polje postoji.
	 */
	public boolean postojiPolje(Point koordinata) {
		return mapaSvijeta.containsKey(koordinata);
	}
	
	public SadrzajPolja posjetiPolje(IAgent agent, int x, int y) {
		Point koordinata = new Point(x, y);
		
		StvarnoPolje polje = mapaSvijeta.get(koordinata);
		if (polje == null) {
			// Ovo se nikad ne bi trebalo dogoditi
			return SadrzajPolja.IMAGINARNO_POLJE;
		}
		
		if (polje.getTip().equals(SadrzajPolja.CUDOVISTE)
				|| polje.getTip().equals(SadrzajPolja.JAMA)) {
			agent.ubij();
		}
		
		return polje.getTip();
	}
	
	public Dimension getVelicinaSvijeta() {
		return new Dimension(sirina, visina);
	}
}
