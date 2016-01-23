package Cramest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper {

	/**
	 * UnZippatore
	 * 
	 * @param zipFile
	 *            file da unzippare
	 * @param output
	 *            cartella in cui lasciare il file zip unzippato
	 */
	public static void UnZip(String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];
		
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				
				File newFile = new File(outputFolder + File.separator + fileName);
				
				if (ze.isDirectory()) {	//SE QUELLO CHE HO UNZIPPATO E' UNA CARTELLA CREA UNA CARTELLA
					
					String temp = newFile.getCanonicalPath();	//PRENDI LA CARTELLA CON LINK ASSOLUTO
					
					new File(temp).mkdir();	//CREA LA CARTELLA
					
				} else {
					
					FileOutputStream FOS = new FileOutputStream(newFile);
					
					int linea;
					
					while ((linea = zis.read(buffer)) > 0) {
						
						FOS.write(buffer, 0, linea);
						
					}
					FOS.close();
				}
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}