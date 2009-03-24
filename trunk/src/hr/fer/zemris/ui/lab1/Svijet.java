package hr.fer.zemris.ui.lab1;

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
		mapaSvijeta = GeneratorSvijeta.stvoriSvijet(visina, sirina, pCudovista, pJama);
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
	 * @return
	 */
	public boolean postojiPolje(Point koordinata) {
		return mapaSvijeta.containsKey(koordinata);
	}

	public SadrzajPolja posjetiPolje(Agent agent, int x, int y) {
		Point koordinata = new Point(x, y);
		StvarnoPolje polje = mapaSvijeta.get(koordinata);
		
		if (polje.getTip().equals(SadrzajPolja.CUDOVISTE)
				|| polje.getTip().equals(SadrzajPolja.JAMA)) {
			agent.ubij();
		}
		
		return polje.getTip();
	}
}
