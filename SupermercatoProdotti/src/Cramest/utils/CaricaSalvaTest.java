package Cramest.utils;

import java.util.ArrayList;

import Cramest.Prodotti.*;

public class CaricaSalvaTest {
	public static void main(String[] args){
		//SalvaFileProdotti sf = new SalvaFileProdotti(System.getProperty("user.dir")+"\\salvataggioProdotti.txt");
		CaricaFileProdotti cf = new CaricaFileProdotti(System.getProperty("user.dir")+"\\salvataggioProdotti.txt");
		ArrayList<Prodotto> prodotti = cf.caricaListaProdotti();
		for(int i = 0;i<prodotti.size();i++){
			System.out.println(prodotti.get(i));
		}
		System.out.println();
	}
}
