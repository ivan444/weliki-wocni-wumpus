package hr.fer.zemris.ui.lab1;

/**
 * Agentova percepcija polja.
 */
public class AgentPolje {
	private boolean posjeceno;
	private boolean sigurnoNeposjeceno;
	private boolean smrdljivo;
	private boolean vjetrovito;
	private boolean sjajno;
	private boolean potencijalnaJama;
	private boolean potencijalnoCudoviste;
	private boolean potencijalnoZlato;
	
	public AgentPolje() {
	}

	public boolean isPosjeceno() {
		return posjeceno;
	}

	public void setPosjeceno(boolean posjeceno) {
		this.posjeceno = posjeceno;
	}

	public boolean isSigurnoNeposjeceno() {
		return sigurnoNeposjeceno;
	}

	public void setSigurnoNeposjeceno(boolean sigurnoNeposjeceno) {
		this.sigurnoNeposjeceno = sigurnoNeposjeceno;
	}

	public boolean isSmrdljivo() {
		return smrdljivo;
	}

	public void setSmrdljivo(boolean smrdljivo) {
		this.smrdljivo = smrdljivo;
	}

	public boolean isVjetrovito() {
		return vjetrovito;
	}

	public void setVjetrovito(boolean vjetrovito) {
		this.vjetrovito = vjetrovito;
	}

	public boolean isSjajno() {
		return sjajno;
	}

	public void setSjajno(boolean sjajno) {
		this.sjajno = sjajno;
	}

	public boolean isPotencijalnaJama() {
		return potencijalnaJama;
	}

	public void setPotencijalnaJama(boolean potencijalnaJama) {
		this.potencijalnaJama = potencijalnaJama;
	}

	public boolean isPotencijalnoCudoviste() {
		return potencijalnoCudoviste;
	}

	public void setPotencijalnoCudoviste(boolean potencijalnoCudoviste) {
		this.potencijalnoCudoviste = potencijalnoCudoviste;
	}

	public boolean isPotencijalnoZlato() {
		return potencijalnoZlato;
	}

	public void setPotencijalnoZlato(boolean potencijalnoZlato) {
		this.potencijalnoZlato = potencijalnoZlato;
	}
}
