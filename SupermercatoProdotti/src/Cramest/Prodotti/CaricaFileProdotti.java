package Cramest.Prodotti;

import java.util.ArrayList;
import Cramest.utils.CaricaFile;
import Cramest.utils.Data;

public class CaricaFileProdotti extends CaricaFile {

	public CaricaFileProdotti(String path) {
		super(path);
	}

	public ArrayList<Prodotto> caricaListaProdotti() {
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		ArrayList<String> contenuto = readFileRighe();
		for (int i = 0; i < contenuto.size(); i++) {

			String cont = contenuto.get(i);

			String[] splittati = cont.split("_");
			if (splittati.length >= 3) {
				String cod = splittati[0];
				String descr = splittati[1];
				double prezzo = Double.parseDouble(splittati[2]);

				if (splittati.length == 3) { // ALLORA E' SOLO UN PRODOTTO
					prodotti.add(new Prodotto(cod, descr, prezzo));
				} else { // E' UNO DEI DUE

					String split4 = splittati[3];

					if (split4.matches(".*\\d+.*")) { // STRANO MODO PER
														// CHIEDERE SE
														// CONTIENE NUMERI
						String[] data = split4.split("/");
						Data scadenza = new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
								Integer.parseInt(data[2]));
						prodotti.add(new Alimentare(cod, descr, prezzo, scadenza));

					} else {
						String materiale = split4;
						prodotti.add(new NonAlimentare(cod, descr, prezzo, materiale));
					}
				}
			}else{
				System.out.println("Errore del boh");
			}
		}
		return prodotti;
	}
}
