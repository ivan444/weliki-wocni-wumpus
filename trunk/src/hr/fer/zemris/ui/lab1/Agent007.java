package hr.fer.zemris.ui.lab1;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Realizacija agenta.
 */
public class Agent007 implements IAgent {
	private Set<Point> posjecenaPolja;
	private Set<Point> sigurnaNeposjecenaPolja;
	private Set<Point> sigurnaCudovista;
	private Set<Point> sigurneJame;
	private Set<Point> smrdljivaPolja;
	private Set<Point> vjetrovitaPolja;
	private Set<Point> sjajnaPolja;
	private Set<Point> potencijalneJame;
	private Set<Point> potencijalnaCudovista;
	private Set<Point> potencijalnaZlata;
	private Queue<Point> definiraniPut;
	private Svijet svijet;
	private Point pozicija;
	private boolean zivim;
	private boolean nasaoZlato;
	
	/**
	 * Konstruktor.
	 * 
	 * @param svijet Svijet u kojemu agent jest.
	 */
	public Agent007(Svijet svijet) {
		posjecenaPolja = new HashSet<Point>();
		sigurnaNeposjecenaPolja = new HashSet<Point>();
		sigurnaCudovista = new HashSet<Point>();
		sigurneJame = new HashSet<Point>();
		smrdljivaPolja = new HashSet<Point>();
		vjetrovitaPolja = new HashSet<Point>();
		sjajnaPolja = new HashSet<Point>();
		potencijalneJame = new HashSet<Point>();
		potencijalnaCudovista = new HashSet<Point>();
		potencijalnaZlata = new HashSet<Point>();
		definiraniPut = new LinkedList<Point>();
		this.svijet = svijet;
		this.zivim = true;
		this.nasaoZlato = false;
		this.pozicija = new Point(1, 1);
		promijeniPoziciju(1, 1, false);
		
		promotriOkolis();
	}
	
	/**
	 * Prikupljanje znanja o okolini na temelju 
	 * znanja o polju na kojemu se agent nalazi.
	 */
	private void promotriOkolis() {
		Point koordinata = new Point(pozicija.x, pozicija.y);
		
		Point[] susjedne = new Point[4];
		// koordinate polja gore, dolje, lijevo, desno
		susjedne[0] = new Point (pozicija.x, pozicija.y+1);
		susjedne[1] = new Point (pozicija.x, pozicija.y-1);
		susjedne[2] = new Point (pozicija.x-1, pozicija.y);
		susjedne[3] = new Point (pozicija.x+1, pozicija.y);
		
		if (this.posjecenaPolja.contains(pozicija)) {
			return;
			
		} else {
			this.posjecenaPolja.add(koordinata);
			this.sigurnaNeposjecenaPolja.remove(koordinata);
			this.potencijalnaCudovista.remove(koordinata);
			this.potencijalnaZlata.remove(koordinata);
			this.potencijalneJame.remove(koordinata);
		}
		
		Polje opis = svijet.opisi(koordinata);
		if (opis.isPropuhuje()) {
			vjetrovitaPolja.add(koordinata);
		}
		if (opis.isSjaji()) {
			sjajnaPolja.add(koordinata);
		}
		if (opis.isSmrdi()) {
			smrdljivaPolja.add(koordinata);
		}
		
		ispraviPretpostavke(koordinata, susjedne);
		
		if (sjajnaPolja.contains(koordinata)) {
			Set<Point> zlato = new HashSet<Point>();
			dopuniZnanje(koordinata, zlato, "zlato", potencijalnaZlata,	sigurnaCudovista, sigurneJame);
			if (!zlato.isEmpty()) {
				definiraniPut.clear();
				definiraniPut.offer(zlato.iterator().next());
			}
		}
		
		if (!smrdljivaPolja.contains(koordinata) && !vjetrovitaPolja.contains(koordinata)) {
			for (int i = 0; i < susjedne.length; i++) {
				if (!svijet.postojiPolje(susjedne[i])) continue;
				if (!posjecenaPolja.contains(susjedne[i])) {
					sigurnaNeposjecenaPolja.add(susjedne[i]);
					CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Polje ("
							+ susjedne[i].x + ", " + susjedne[i].y + ") je sigurno za posjetiti.");
				}
			}
			
		} else {
			Set<Point> emptySet = new HashSet<Point>();
			if (smrdljivaPolja.contains(koordinata)) {
				dopuniZnanje(koordinata, sigurnaCudovista, "čudovište", potencijalnaCudovista,
						sigurneJame, emptySet);
			}
			if (vjetrovitaPolja.contains(koordinata)) {
				dopuniZnanje(koordinata, sigurneJame, "jama", potencijalneJame,
						sigurnaCudovista, emptySet);
			}
		}
	}
	
	private void dopuniZnanje(Point koordinata, Set<Point> ulazniSkup, String tip,
			Set<Point> potencijalniSkup, Set<Point> poznatiSkup1, Set<Point> poznatiSkup2) {
		Point[] susjedne = new Point[4];
		susjedne[0] = new Point (koordinata.x, koordinata.y+1);
		susjedne[1] = new Point (koordinata.x, koordinata.y-1);
		susjedne[2] = new Point (koordinata.x-1, koordinata.y);
		susjedne[3] = new Point (koordinata.x+1, koordinata.y);
		
		for (int i = 0; i < susjedne.length; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			if (posjecenaPolja.contains(susjedne[i]) || sigurnaNeposjecenaPolja.contains(susjedne[i])
					|| poznatiSkup1.contains(susjedne[i]) || poznatiSkup2.contains(susjedne[i])) {
				susjedne[i] = null;
			}
		}
		
		int brojPreostalih = 0;
		for (int i = 0; i < susjedne.length; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			if (susjedne[i] != null) {
				brojPreostalih++;
			}
		}
		
		for (int i = 0; i < susjedne.length; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			if (susjedne[i] != null) {
				if (brojPreostalih == 1) {
					ulazniSkup.add(susjedne[i]);
					CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Na polju (" + susjedne[i].x
							+ ", " + susjedne[i].y + ") se nalazi " + tip + "!");
				} else {
					potencijalniSkup.add(susjedne[i]);
					CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Na polju (" + susjedne[i].x
							+ ", " + susjedne[i].y + ") se možda nalazi " + tip + ".");
				}
			}
		}
		
	}

	/**
	 * Ukloni neka polja za koja se pretpostavljalo da sadrže čudovište,
	 * jamu ili zlato.
	 *  
	 * @param koordinata Trenutna pozicija na kojoj je agent.
	 * @param susjedne Pozicije susjedne agentu.
	 */
	private void ispraviPretpostavke(Point koordinata, Point[] susjedne) {
		for (int i = 0; i < susjedne.length; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			if (potencijalnaCudovista.contains(susjedne[i]) && !smrdljivaPolja.contains(koordinata)) {
				potencijalnaCudovista.remove(susjedne[i]);
				CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Čudovište se ipak ne nalazi na ("
								+ susjedne[i].x + ", " + susjedne[i].y + ")");
			}
			
			if (potencijalneJame.contains(susjedne[i]) && !vjetrovitaPolja.contains(koordinata)) {
				potencijalneJame.remove(susjedne[i]);
				CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Jama se ipak ne nalazi na ("
						+ susjedne[i].x + ", " + susjedne[i].y + ")");
			}
			
			if (potencijalnaZlata.contains(susjedne[i]) && !sjajnaPolja.contains(koordinata)) {
				potencijalnaZlata.remove(susjedne[i]);
				CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Zlato se ipak ne nalazi na ("
						+ susjedne[i].x + ", " + susjedne[i].y + ")");
			}
		}
	}

	public void pomakniSe() {
		if (!zivim) {
			CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Nemoguće. Agent je mrtav.");
			return;
		}
		if (nasaoZlato && pozicija.x == 1 && pozicija.y == 1) {
			CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Nema potrebe. Imamo zlato!");
			return;
		}
		
		// koordinate polja na koje cemo se pomaknuti
		Point sljedecaKoordinata = new Point();
		boolean odabraoPolje = false;
		
		if (!definiraniPut.isEmpty()) {
			sljedecaKoordinata = definiraniPut.poll();
			odabraoPolje = true;
		}
		
		// trenutna koordinata na kojoj se nalazimo
		Point koordinata = new Point(pozicija.x, pozicija.y);
		
		Point[] susjedne = new Point[4];
		
		// koordinate polja gore, dolje, lijevo, desno
		susjedne[0] = new Point (koordinata.x, koordinata.y+1);
		susjedne[1] = new Point (koordinata.x, koordinata.y-1);
		susjedne[2] = new Point (koordinata.x-1, koordinata.y);
		susjedne[3] = new Point (koordinata.x+1, koordinata.y);
		
		// Odaberi sljedece polje po prioritetu kretanja. 
		//
		// prvo neposjecena susjedna, sigurna
		// zatim neposjecena NEsusjedna, sigurna
		// zatim neposjecena nesigurna :(
		//
		// postavlja koordinate polja kojega agent posjecuje u iducem koraku u tocku
		// ---- Point sljedecaKoordinata ----
		
		// prvotno susjedna sigurna polja i potencijalna zlata!!
		for (int i = 0; i < susjedne.length && !odabraoPolje; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			
			// sigurna i u skupu potencijalnaZllistata
			if (sigurnaNeposjecenaPolja.contains(susjedne[i]) && 
					potencijalnaZlata.contains(susjedne[i])) { 
				sljedecaKoordinata.x = susjedne[i].x;
				sljedecaKoordinata.y = susjedne[i].y;
				odabraoPolje = true;
				break;
			}
		}
		
		// Ostala potencijalna zlata i sigurna polja
		for (Point potZlato : potencijalnaZlata) {
			if (sigurnaNeposjecenaPolja.contains(potZlato)) {
				odiNaPolje(potZlato.x, potZlato.y);
				sljedecaKoordinata = definiraniPut.poll();
				odabraoPolje = true;
				break;
			}
		}
		
		// susjedna sigurna polja !!
		for (int i = 0; i < susjedne.length && !odabraoPolje; i++) {
			if (!svijet.postojiPolje(susjedne[i])) continue;
			
			if (sigurnaNeposjecenaPolja.contains(susjedne[i])) {
				sljedecaKoordinata.x = susjedne[i].x;
				sljedecaKoordinata.y = susjedne[i].y;
				odabraoPolje = true;
				break;
			}
		}
		
		// Ostala sigurna neposjećena
		if (!odabraoPolje && !sigurnaNeposjecenaPolja.isEmpty()) {
			Point nesusjedna = sigurnaNeposjecenaPolja.iterator().next();
			odiNaPolje(nesusjedna.x, nesusjedna.y);
			sljedecaKoordinata = definiraniPut.poll();
			odabraoPolje = true;
		}
				
		// a ako ni sada nismo nasli sigurno polje, agent odlazi na
		// bilo koje polje iz liste potencijalnih zlata, jama ili cudovista.
		if (!odabraoPolje) {
			Point odabrano = null;
			if (!potencijalnaZlata.isEmpty()) {
				odabrano = potencijalnaZlata.iterator().next();
				
			} else if (!potencijalneJame.isEmpty()) {
				odabrano = potencijalneJame.iterator().next();
				
			} else if (!potencijalnaCudovista.isEmpty()) {
				odabrano = potencijalnaCudovista.iterator().next();
			}
			
			if (odabrano == null) {
				// Ne bi se smjelo dogoditi, ali da se pokrije i taj slučaj.
				odabrano = new Point(1,1);
			}
			odiNaPolje(odabrano.x, odabrano.y);
			sljedecaKoordinata = definiraniPut.poll();
			odabraoPolje = true;
		}
				
			
		SadrzajPolja sadrzaj = svijet.posjetiPolje(this, sljedecaKoordinata.x, sljedecaKoordinata.y);
				
		promijeniPoziciju(sljedecaKoordinata.x, sljedecaKoordinata.y, false);
				
		if (isZivim()) {
			promotriOkolis(); // nakon svakog pomaka ponovno promatra okolis i obogacuje bazu znanja
		}
		
		if (sadrzaj.equals(SadrzajPolja.ZLATO)) {
			nasaoZlato = true;
			definiraniPut.clear();
			odiNaPolje(1, 1);
			CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Imamo zlato!");
		}
		
		if (nasaoZlato) {
			potencijalnaZlata.clear();
			sjajnaPolja.clear();
		}
		
		CentralnaInformacijskaAgencija.getCIA().obavijestiOPromjeni();
	}
	
	/**
	 * Mijenjanje pozicije agenta (i obavljanje svih poslova koji se pri
	 * pomaku moraju obaviti!).<br\>
	 * Obavezno koristiti ovu metodu, a ne direktno mijenjati poziciju.
	 * 
	 * @param x X koordinata pozicije na koju mičemo agenta.
	 * @param y Y koordinata pozicije na koju mičemo agenta.
	 * @param obavijesti True ako želimo obavijestiti o promjeni.
	 */
	private void promijeniPoziciju(int x, int y, boolean obavijesti) {
		this.pozicija.setLocation(x, y);
		if (obavijesti) {
			CentralnaInformacijskaAgencija.getCIA().obavijestiOPromjeni();
		}
	}
	
	public Point getPozicija() {
		return this.pozicija;
	}
	
	/**
	 * Pomiče agenta na neko posjećeno polje .
	 * 
	 * @param x X koordinata polja na koje želimo pomaknuti agenta.
	 * @param y Y koordinata polja na koje želimo pomaknuti agenta.
	 * @return True ako agent može pristupiti točki ({@code x}, {@code y}).
	 */
	@SuppressWarnings("unchecked")
	private boolean odiNaPolje(int x, int y) {
		Set<Point> posjecene = new HashSet<Point>();
		
		Point odrediste = new Point(x, y);
		if (izgradnjaPuta(pozicija.x, pozicija.y, odrediste, definiraniPut, posjecene)) {
			Collections.reverse((List<Point>) definiraniPut);
			return true;
			
		} else {
			return false;
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
	private boolean izgradnjaPuta(int x, int y, Point odrediste, Queue<Point> put, Set<Point> posjecene) {
		Point radna = new Point(x, y);
		posjecene.add(new Point(x, y));
		
		radna.x = x+1;
		radna.y = y;
		if (radna.equals(odrediste)) {
			put.offer(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (izgradnjaPuta(x+1, y, odrediste, put, posjecene)) {
				put.offer(radna);
				return true;
			}
		}
		
		radna.x = x-1;
		radna.y = y;
		if (radna.equals(odrediste)) {
			put.offer(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (izgradnjaPuta(x-1, y, odrediste, put, posjecene)) {
				put.offer(radna);
				return true;
			}
		}
		
		radna.x = x;
		radna.y = y+1;
		if (radna.equals(odrediste)) {
			put.offer(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (izgradnjaPuta(x, y+1, odrediste, put, posjecene)) {
				put.offer(radna);
				return true;
			}
		}
		
		radna.x = x;
		radna.y = y-1;
		if (radna.equals(odrediste)) {
			put.offer(odrediste);
			return true;
		}
		if (!posjecene.contains(radna) && this.posjecenaPolja.contains(radna)) {
			if (izgradnjaPuta(x, y-1, odrediste, put, posjecene)) {
				put.offer(radna);
				return true;
			}
		}
		
		return false;
	}
	
	public void ubij() {
		this.zivim = false;
		CentralnaInformacijskaAgencija.getCIA().dodajPoruku("Agent je mrtav");
	}

	public boolean isZivim() {
		return zivim;
	}

	public boolean isNasaoZlato() {
		return nasaoZlato;
	}
	
	/**
	 * Agentov opis polja.
	 * 
	 * @param polje Koordinate polja koje agent opisuje.
	 * @return Opis polja.
	 */
	public AgentPolje opisiPolje(Point polje) {
		AgentPolje opis = new AgentPolje();
		
		opis.setPosjeceno(this.posjecenaPolja.contains(polje));
		opis.setSigurnoNeposjeceno(this.sigurnaNeposjecenaPolja.contains(polje));
		opis.setSmrdljivo(this.smrdljivaPolja.contains(polje));
		opis.setVjetrovito(this.vjetrovitaPolja.contains(polje));
		opis.setSjajno(this.sjajnaPolja.contains(polje));
		opis.setPotencijalnaJama(this.potencijalneJame.contains(polje));
		opis.setPotencijalnoCudoviste(this.potencijalnaCudovista.contains(polje));
		opis.setPotencijalnoZlato(this.potencijalnaZlata.contains(polje));
		opis.setCudoviste(this.sigurnaCudovista.contains(polje));
		opis.setJama(this.sigurneJame.contains(polje));
		
		return opis;
	}
}
