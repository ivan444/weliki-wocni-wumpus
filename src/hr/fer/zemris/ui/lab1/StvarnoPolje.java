package hr.fer.zemris.ui.lab1;

/**
 * Potpuna reprezentacija jednog polja.
 * Sadrži stvarni pogled na svijet.
 */
public class StvarnoPolje {
	private Polje polje;
	private SadrzajPolja tip;
	
	public StvarnoPolje(boolean smrdi, boolean sjaji, boolean propuh, SadrzajPolja tip) {
		this.polje = new Polje(smrdi, sjaji, propuh);
		this.tip = tip;
	}
	
	public StvarnoPolje(SadrzajPolja tip) {
		this.polje = new Polje(false, false, false);
		this.tip = tip;
	}
	
	/**
	 * @return Što polje sadrži.
	 */
	public SadrzajPolja getTip() {
		return tip;
	}

	public boolean isPropuh() {
		return polje.isPropuhuje();
	}

	public boolean isSjaji() {
		return polje.isSjaji();
	}

	public boolean isSmrdi() {
		return polje.isSmrdi();
	}

	public Polje getPolje() {
		return polje;
	}
}
