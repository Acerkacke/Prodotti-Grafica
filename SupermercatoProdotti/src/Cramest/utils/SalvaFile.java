
package Cramest.utils;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SalvaFile {
	String nomeFile;
	
	public void salva(int numero){
		
	}
	
	private void scriviSuFile(){
		List<String> lines = Arrays.asList("The first line", "The second line");
		Path file = Paths.get("the-file-name.txt");
		try{
			Files.write(file, lines, Charset.forName("UTF-8"));
		}catch(Exception e){
			System.out.println("ciao");
		}
	}
}
