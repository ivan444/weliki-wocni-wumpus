package hr.fer.zemris.ui.lab1;

import java.awt.Point;

/**
 * Agent koji služi za nesmetani obilazak svijeta (ne može umrijeti).
 */
public class PseudoAgent implements IAgent {

	@Override
	public Point getPozicija() {
		return null;
	}

	@Override
	public boolean isNasaoZlato() {
		return false;
	}

	@Override
	public boolean isZivim() {
		return true;
	}

	@Override
	public AgentPolje opisiPolje(Point polje) {
		return null;
	}

	@Override
	public void pomakniSe() {
	}

	@Override
	public void ubij() {
		// ništ... nema... CN!
	}

}
