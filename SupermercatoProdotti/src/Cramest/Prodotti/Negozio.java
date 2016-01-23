
package Cramest.Prodotti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Cramest.utils.Data;

public class Negozio {

	private static Prodotto[] inventario = {
			new Alimentare("4134", "Una Mela", 0.20, new Data(24, 1, 2016)),
			new Alimentare("4634", "Una Pera", 0.20, new Data(30, 1, 2016)),
			new Alimentare("4164", "Una Banana", 0.20, new Data(20, 1, 2016)),
			new NonAlimentare("4324", "Carta igenica", 1.00, "carta"),
			new NonAlimentare("6427", "Bottiglia d'acqua", 1.00, "plastica"),
			new NonAlimentare("9547", "Batteria", 1.00, "metallo pesante"),
			};

	public static ListaSpesa carrello;

	public static void main(String[] args) {
		InputStreamReader ISR = new InputStreamReader(System.in);
		BufferedReader BR = new BufferedReader(ISR);
		System.out.println("--- BENVENUTO AL SUPERMARKET ---");
		carrello = new ListaSpesa();
		System.out.println("Hai per caso la tessera?s/n");
		String SN = leggiStringa(BR);
		if (SN.equals("s")) {
			System.out.println("Ottimo! Perche' adesso e' periodo di SALDI");
			applicaSconti();
		} else {
			System.out.println("Non importa");
		}
		int sel = 0;
		while (sel != 99) {
			stampaProdotti();
			sel = leggiInt(BR);
			if (sel != 99) {
				if (sel >= 0 && sel <= 10) {
					carrello.aggiungiProdotto(inventario[sel]);
				} else {
					System.out.println(carrello.toString());
				}
			} else {
				System.out.println("Il totale del tuo carrello e': " + carrello.calcolaTOT());
			}
		}
	}

	private static void applicaSconti() {
		for (int i = 0; i < inventario.length; i++) {
			inventario[i].applicaSconto();
		}
	}

	private static void stampaProdotti() {
		for (int i = 0; i < inventario.length; i++) {
			System.out.println(i + " - " + inventario[i].getDescr() + " prezzo: " + inventario[i].getPrezzo());
		}
		System.out.println("99 - per uscire");
	}

	private static int leggiInt(BufferedReader br) {
		return Integer.parseInt(leggiStringa(br));
	}

	private static String leggiStringa(BufferedReader br) {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("ERRORE");
			return leggiStringa(br);
		}
	}
}
