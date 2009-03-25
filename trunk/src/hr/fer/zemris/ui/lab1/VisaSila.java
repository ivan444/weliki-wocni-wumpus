package hr.fer.zemris.ui.lab1;

import hr.fer.zemris.ui.lab1.GUI.PogledUSvijet;

import java.awt.Point;

public class VisaSila {
	private Svijet svijet;
	private IAgent agent;
	private IAgent besmrtniAgent; // CN
	
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
		besmrtniAgent = new PseudoAgent();
	}
	
	public void pomakniAgenta() {
		agent.pomakniSe();
	}
	
	/**
	 * Inicijalizacija aplikacije i stvaranje početnog svijeta.
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

	public Svijet getSvijet() {
		return svijet;
	}

	public IAgent getAgent() {
		return agent;
	}
	
	public SadrzajPolja opisiPolje(Point polje) {
		return opisiPolje(polje.x, polje.y);
	}
	
	/**
	 * Stvarni opis polja.
	 * 
	 * @param x X koordinata polja čiji opis želimo.
	 * @param y Y koordinata polja čiji opis želimo.
	 * @return Polje kakvo jest.
	 */
	public SadrzajPolja opisiPolje(int x, int y) {
		return svijet.posjetiPolje(besmrtniAgent, x, y);
	}
}
