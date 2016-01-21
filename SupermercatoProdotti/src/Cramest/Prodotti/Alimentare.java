package Cramest.Prodotti;

public class Alimentare extends Prodotto {

	Data scadenza;

	public Alimentare(String cod, String descr, double prezzo, Data scadenza) {
		super(cod, descr, prezzo);
		this.scadenza = scadenza;
	}
	
	public void applicaSconto() {
		if (scadenza.getDifference(new Data()) < 10) {
			prezzo -= prezzo / 100 * 20;
		}
	}
}
