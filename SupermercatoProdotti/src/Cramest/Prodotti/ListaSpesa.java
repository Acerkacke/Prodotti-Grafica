
package Cramest.Prodotti;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaSpesa {

	private static ArrayList<Prodotto> carrello;
	
	public ListaSpesa(){
		carrello = new ArrayList<Prodotto>();
	}
	public ListaSpesa(Prodotto[] prodotti){
		carrello = new ArrayList<Prodotto>(Arrays.asList(prodotti));
	}
	public ListaSpesa(ArrayList<Prodotto> prodotti){
		carrello = new ArrayList<Prodotto>(prodotti);
	}
	
	public void aggiungiProdotto(Prodotto prodotto){
			carrello.add(new Prodotto(prodotto));
	}
	public void eliminaProdotto(Prodotto prodotto){
		carrello.remove(prodotto);
	}
	public void eliminaProdotto(int index){
		carrello.remove(index);
	}
	public void svuota(){
		carrello.clear();
	}
	
	public double calcolaTOT(){
		double totale = 0;
		for(int i=0;i<carrello.size();i++){
			totale += carrello.get(i).prezzo;
		}
		return totale;
	}
	
	public Prodotto getProdotto(int index){
		return carrello.get(index);
	}
	
	@Override
	public String toString() {
		String stringa = "";
		for(int i=0;i<carrello.size();i++){
			stringa += carrello.get(i).toString() + "\n";
		}
		return stringa;
	}
	
	public void applicaSconti(){
		for(int i=0;i<carrello.size();i++){
			carrello.get(i).applicaSconto();
		}
	}
	
	public int lenght(){
		return carrello.size();
	}
	
	public int size(){
		return carrello.size();
	}
	
	public int count(){
		return carrello.size();
	}
}
