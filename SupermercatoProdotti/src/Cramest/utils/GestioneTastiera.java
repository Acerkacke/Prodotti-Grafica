package Cramest.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GestioneTastiera {

	public static int leggiInt() throws Exception{
		try{
			return Integer.parseInt(leggiStringa());
		}catch(Exception e){
			throw new Exception("Impossibile convertire la stringa in intero");
		}
	}
	public static double leggiDouble() throws Exception{
		try{
			return Double.parseDouble(leggiStringa());
		}catch(Exception e){
			throw new Exception("Impossibile convertire la stringa in double");
		}
	}
	public static double leggiFloat() throws Exception{
		try{
			return Float.parseFloat(leggiStringa());
		}catch(Exception e){
			throw new Exception("Impossibile convertire la stringa in float");
		}
	}
	public static String leggiStringa() {
		BufferedReader br = creaBR();
		try{
			return br.readLine();
		}catch(Exception e){
			System.out.println("ERRORE, REINSERIRE");
			return leggiStringa();
		}
	}
	private static BufferedReader creaBR(){
		InputStreamReader isr = new InputStreamReader(System.in);
		return new BufferedReader(isr);
	}
}
