package hr.fer.zemris.ui.lab1;

public class VisaSila {
	
	public static void main(String[] args) {
		Svijet svijet = new Svijet(20, 10, 0.1, 0.1);
		Agent agent = new Agent(svijet);
		agent.pomakniSe();
	}
}
