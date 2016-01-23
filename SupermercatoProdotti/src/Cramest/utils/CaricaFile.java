package Cramest.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CaricaFile {
	String filePath;

	public CaricaFile(String path){
		filePath = path;
	}
	
	public String getFile(){
		return readFileTot();
	}
	
	public ArrayList<String> getFileRighe(){
		return readFileRighe();
	}
	
	protected String readFileTot() {
		BufferedReader br = null;
		String risultato = "";
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			String linea;
			while ((linea = br.readLine()) != null){
				risultato += (linea + "\n");
			}
			br.close();
			return risultato;
		} catch (IOException e) {
			e.printStackTrace();
			return "ERRORE";
		}
	}
	
	protected ArrayList<String> readFileRighe() {
		BufferedReader br = null;
		ArrayList<String> righe = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(filePath));
			String linea;
			while ((linea = br.readLine()) != null){
				righe.add(linea);
			}

			br.close();

			return righe;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
