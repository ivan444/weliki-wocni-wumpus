package hr.fer.zemris.ui.lab1;

/**
 * Temeljna reprezentacija jednog polja.
 * Sadrži agentov pogled na svijet. 
 */
public class Polje {
	private boolean smrdi;
	private boolean sjaji;
	private boolean propuhuje;
	
	public Polje() {
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param smrdi Smrdi li polje.
	 * @param sjaji Sjaji li polje.
	 * @param propuhuje Puše li na polju.
	 * @param tip Što polje sadrži.
	 */
	public Polje(boolean smrdi, boolean sjaji, boolean propuhuje) {
		super();
		this.smrdi = smrdi;
		this.sjaji = sjaji;
		this.propuhuje = propuhuje;
	}

	public boolean isSmrdi() {
		return smrdi;
	}

	public void setSmrdi(boolean smrdi) {
		this.smrdi = smrdi;
	}

	public boolean isSjaji() {
		return sjaji;
	}

	public void setSjaji(boolean sjaji) {
		this.sjaji = sjaji;
	}

	public boolean isPropuhuje() {
		return propuhuje;
	}

	public void setPropuhuje(boolean propuh) {
		this.propuhuje = propuh;
	}
	
}
