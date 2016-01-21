
package Cramest.utils;

import java.util.Arrays;

public class SalvaFile {
	String nomeFile;
	
	public void salva(int numero){
		
	}
	
	private void scriviSuFile(){
		List<String> lines = Arrays.asList("The first line", "The second line");
		Path file = Paths.get("the-file-name.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
	}
}
