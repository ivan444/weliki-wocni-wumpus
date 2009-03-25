package hr.fer.zemris.ui.lab1;

import hr.fer.zemris.ui.lab1.GUI.IChangeListener;

import java.util.HashSet;
import java.util.Set;

public class CentralnaInformacijskaAgencija {
	private Set<IChangeListener> klijentiPoruka;
	private Set<IChangeListener> pratiteljiStanjaSvijeta;
	
	public CentralnaInformacijskaAgencija() {
		this.klijentiPoruka = new HashSet<IChangeListener>();
		this.pratiteljiStanjaSvijeta = new HashSet<IChangeListener>();
	}
	
	public void dodajPoruku(String poruka) {
		prodajaInformacija(poruka);
	}
	
	public void obavijestiOPromjeni() {
		promjenaStanjaSvijeta();
	}
	
	public void pretplataNaPoruke(IChangeListener klijent) {
		this.klijentiPoruka.add(klijent);
	}
	
	public void pretplataNaStanjeSvijeta(IChangeListener klijent) {
		this.pratiteljiStanjaSvijeta.add(klijent);
	}
	
	/** Pomoćna privatna klasa za konstrukciju singleton razreda. */
	private static class SingletonCIA {
		private final static CentralnaInformacijskaAgencija INSTANCE = new CentralnaInformacijskaAgencija();
	}

	/** Dohvat obavještavača. */
	public static CentralnaInformacijskaAgencija getCIA() {
		return SingletonCIA.INSTANCE;
	}
	
	private void prodajaInformacija(String informacija) {
		for (IChangeListener klijent : klijentiPoruka) {
			klijent.interakcija(informacija);
		}
	}
	
	private void promjenaStanjaSvijeta() {
		for (IChangeListener klijent : pratiteljiStanjaSvijeta) {
			klijent.interakcija();
		}
	}
}
