package Cramest.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CaricaFile {
	String filePath;

	public CaricaFile(String path){
		filePath = path;
	}
	
	public String getFile(){
		return readFile();
	}
	
	protected String readFile() {
		BufferedReader br = null;
		String risultato = "";
		try {
			br = new BufferedReader(new FileReader(filePath));
			String linea;
			while ((linea = br.readLine()) != null){
				risultato += linea + "\n";
			}

			br.close();

			return risultato;
		} catch (IOException e) {
			e.printStackTrace();
			return "ERRORE";
		}

	}
}
