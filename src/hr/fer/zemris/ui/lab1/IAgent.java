package hr.fer.zemris.ui.lab1;

import java.awt.Point;

/**
 * 
 */
public interface IAgent {
	public void ubij();
	public void pomakniSe();
	public Point getPozicija();
	public boolean isZivim();
	public boolean isNasaoZlato();
	public AgentPolje opisiPolje(Point polje);
}
