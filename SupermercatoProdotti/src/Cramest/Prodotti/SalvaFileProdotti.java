package Cramest.Prodotti;

import Cramest.utils.SalvaFile;

public class SalvaFileProdotti extends SalvaFile{

	public SalvaFileProdotti(String path) {
		super(path);
	}

	public boolean salvaProdotto(Prodotto prodotto){
		if(prodotto != null){
			String codice = prodotto.cod;
			String descrizione = prodotto.descr;
			double prezzo = prodotto.prezzo;
			salva(codice+separatore+descrizione+separatore+prezzo);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean salvaAlimentare(Alimentare prodotto){
		if(prodotto != null){
			String codice = prodotto.cod;
			String descrizione = prodotto.descr;
			double prezzo = prodotto.prezzo;
			Data scadenza = prodotto.scadenza;
			salva(codice+separatore+descrizione+separatore+prezzo+separatore+scadenza);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean salvaNonAlimentare(NonAlimentare prodotto){
		if(prodotto != null){
			String codice = prodotto.cod;
			String descrizione = prodotto.descr;
			double prezzo = prodotto.prezzo;
			String materiale = prodotto.materiale;
			salva(codice+separatore+descrizione+separatore+prezzo+separatore+materiale);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean salvaListaSpesa(ListaSpesa carrello){
		if(carrello != null){
			if(carrello.size() > 0){
				for(int i=0;i<carrello.size();i++){
					if(carrello.getProdotto(i).getClass() == Alimentare.class){
						salvaAlimentare((Alimentare)carrello.getProdotto(i));
					}else if(carrello.getProdotto(i).getClass() == NonAlimentare.class){
						salvaNonAlimentare((NonAlimentare)carrello.getProdotto(i));
					}else{
						salvaProdotto(carrello.getProdotto(i));
					}
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
