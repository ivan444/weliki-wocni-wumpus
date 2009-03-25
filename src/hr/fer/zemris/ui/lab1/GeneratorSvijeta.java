package hr.fer.zemris.ui.lab1;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Stvaranje svijeta na osnovu parametara.
 */
public class GeneratorSvijeta {
	
	/**
	 * Stvaranje svijeta.
	 * 
	 * @param sirina Širina novog svijeta.
	 * @param visina Visina novog svijeta.
	 * @param pCudovista Vjerojatnost da će na nekom polju biti čudovište.
	 * @param pJama Vjerojatnost da će na nekom polju biti jama.
	 * @return Mapa stvorenog svijeta.
	 */
	public static Map<Point, StvarnoPolje> stvoriSvijet(int sirina, int visina,
			double pCudovista, double pJama) {
		
		if (visina <= 0 || sirina <= 0) {
			throw new IllegalArgumentException("Širina i visina moraju biti pozitivni brojevi! " +
					"Predali ste (sirina, visina): " + sirina + ", " + visina + ".");
		}
		
		if ((pCudovista < 0.0 || pCudovista > 1.0) || (pJama < 0.0 || pJama > 1.0)) {
			throw new IllegalArgumentException("Vjerojatnosti pojavljivanja čudovišta ili jame mora " +
					"biti u intervalu [0.0, 1.0>! Predali ste (cudoviste, jama): "
					+ pCudovista + ", " + pJama + ".");
		}
		
		if (pJama + pCudovista >= 1.0) {
			throw new IllegalArgumentException("Suma vjerojatnosti pojavljivanja jame ili čudovišta " +
					"mora biti manja od 1!");
		}
		
		Map<Point, StvarnoPolje> mapaSvijeta = new HashMap<Point, StvarnoPolje>();
		List<Atributi> dodatniAtributi = new LinkedList<Atributi>();
		
		long zlatoX = Math.round(sirina * Math.random());
		long zlatoY = Math.round(visina * Math.random());
		
		long brPolja = sirina*visina;
		long brCudovista = Math.round(brPolja*pCudovista);
		long brJama = Math.round(brPolja*pJama);
		long sumaJamaCudovista = brJama + brCudovista;
		
		addPolje(mapaSvijeta, 1, 1, SadrzajPolja.NISTA, dodatniAtributi);
		for (int y = 1; y <= visina; y++) {
			for (int x = 2; x <= sirina; x++) {
				if (zlatoX == x && zlatoY == y) {
					addPolje(mapaSvijeta, x, y, SadrzajPolja.ZLATO, dodatniAtributi);
					continue;
				}
				
				// Podjela: ČČČČČJJJJJNNNNNNNNNNNNNNNNN
				long randomBroj = Math.round(brPolja*Math.random());
				if (randomBroj < brCudovista) {
					addPolje(mapaSvijeta, x, y, SadrzajPolja.CUDOVISTE, dodatniAtributi);
					
				} else if (randomBroj < sumaJamaCudovista) {
					addPolje(mapaSvijeta, x, y, SadrzajPolja.JAMA, dodatniAtributi);
					
				} else {
					addPolje(mapaSvijeta, x, y, SadrzajPolja.NISTA, dodatniAtributi);
				}
			}
		}
		
		generirajAtribute(mapaSvijeta, dodatniAtributi);
		return mapaSvijeta;
	}
	
	private static void addPolje(Map<Point, StvarnoPolje> mapaSvijeta, int x, int y,
			SadrzajPolja tip, List<Atributi> dodatniAtributi) {
		
		mapaSvijeta.put(new Point(x, y), new StvarnoPolje(tip));
		
		if (tip.equals(SadrzajPolja.NISTA)) {
			return;
		}
		
		Atribut atribut = null;
		switch (tip) {
		case ZLATO:
			atribut = Atribut.SJAJ;
			break;
			
		case CUDOVISTE:
			atribut = Atribut.SMRAD;
			break;
			
		case JAMA:
			atribut = Atribut.PROPUH;
			break;
		}
		
		dodatniAtributi.add(new Atributi(atribut, x, y));
		dodatniAtributi.add(new Atributi(atribut, x+1, y));
		dodatniAtributi.add(new Atributi(atribut, x-1, y));
		dodatniAtributi.add(new Atributi(atribut, x, y+1));
		dodatniAtributi.add(new Atributi(atribut, x, y-1));
	}
	
	/**
	 * Dodavanje atributa poljima (sjaj, smrad, propuh).
	 * 
	 * @param mapaSvijeta Mapa stvorenog svijeta.
	 */
	private static void generirajAtribute(Map<Point, StvarnoPolje> mapaSvijeta,
			List<Atributi> dodatniAtributi) {
		
		for (Atributi atribut : dodatniAtributi) {
			StvarnoPolje polje = mapaSvijeta.get(atribut.getKoordinata());
			if (polje == null) continue; // Ako koordinata ne postoji
			
			switch (atribut.getAtribut()) {
			case SJAJ:
				polje.getPolje().setSjaji(true);
				break;
				
			case SMRAD:
				polje.getPolje().setSmrdi(true);
				break;
				
			case PROPUH:
				polje.getPolje().setPropuhuje(true);
				break;
			}
		}
	}


	/**
	 * Tipovi atributa.
	 */
	enum Atribut {
		SJAJ,
		SMRAD,
		PROPUH;
	}
	
	/**
	 * Atributi koje moramo pridružiti polju. 
	 */
	private static class Atributi {
		private Atribut atribut;
		private Point koordinata;
		
		public Atributi(Atribut atribut, int x, int y) {
			this.atribut = atribut;
			this.koordinata = new Point(x, y);
		}
		
		public Atributi(Atribut atribut, Point koordinata) {
			this.atribut = atribut;
			this.koordinata = koordinata;
		}

		public Atribut getAtribut() {
			return atribut;
		}

		public Point getKoordinata() {
			return koordinata;
		}
	}
}
