package hr.fer.zemris.ui.lab1;

import hr.fer.zemris.ui.lab1.GUI.PogledUSvijet;

public class VisaSila {
	private Svijet svijet;
	private Agent agent;
	
	/**
	 * Stvaranje novog svijeta.
	 * 
	 * @param sirina
	 * @param visina
	 * @param pCudovista
	 * @param pJama
	 */
	public void stvoriSvijet(int sirina, int visina, double pCudovista, double pJama) {
		svijet = new Svijet(sirina, visina, pCudovista, pJama);
		agent = new Agent(svijet);
	}
	
	public void pomakniAgenta() {
		agent.pomakniSe();
	}
	
	/**
	 * Pokretanje aplikacije i stvaranje početnog svijeta.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		VisaSila.get().stvoriSvijet(20, 10, 0.1, 0.1);
		PogledUSvijet.open();
	}
	
	/** Pomoćna privatna klasa za konstrukciju singleton razreda. */
	private static class SingletonVS {
		private final static VisaSila INSTANCE = new VisaSila();
	}

	/** Dohvat Više sile. */
	public static VisaSila get() {
		return SingletonVS.INSTANCE;
	}
}
