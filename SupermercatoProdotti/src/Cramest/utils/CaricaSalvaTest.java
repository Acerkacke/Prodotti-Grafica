package Cramest.utils;

public class CaricaSalvaTest {
	public static void main(String[] args){
		SalvaFile sf = new SalvaFile("C:\\Users\\cremaluca\\Desktop\\salvataggio1.txt");
		CaricaFile cf = new CaricaFile("C:\\Users\\cremaluca\\Desktop\\salvataggio1.txt");
		sf.salva(5);
		sf.salva(8);
		System.out.println(cf.getFile());
	}
}
