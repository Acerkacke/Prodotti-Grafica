package Cramest.Prodotti;

public class Prodotto {
	protected String cod = new String();
	protected String descr = new String();
	protected double prezzo;

	public Prodotto(String cod, String descr, double prezzo) {
		this.cod = cod;
		this.descr = descr;
		this.prezzo = prezzo;
	}
	
	public Prodotto(Prodotto prod) {
		this.cod = prod.cod;
		this.descr = prod.descr;
		this.prezzo = prod.prezzo;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public void applicaSconto() {
		prezzo -= (prezzo/100)*5;
	}

	public String toString() {
		return descr + ", " + prezzo + "€";
	}

	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


}
