
package Cramest.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SalvaFile {
	private String pathFile;
	
	public SalvaFile(String path){
		pathFile = path;
	}
	public String getPath(){
		return pathFile;
	}
	public boolean salva(int numero){
		return scriviSuFile(numero);
	}
	public boolean salva(double numero){
		return scriviSuFile(numero);
	}
	public boolean salva(String valore){
		return scriviSuFile(valore);
	}
	private boolean scriviSuFile(int n){
		return scriviSuFile(String.valueOf(n));
	}
	
	private boolean scriviSuFile(double d){
		return scriviSuFile(String.valueOf(d));
	}
	
	private boolean scriviSuFile(float f){
		return scriviSuFile(String.valueOf(f));
	}
	
	private boolean scriviSuFile(String content){
		try {
			File file = new File(pathFile);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
