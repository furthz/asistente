package pe.soapros.asistente.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<Path> listarFicheros(String path, String extension) throws IOException {
		List<Path> archivos = new ArrayList<Path>();
		Files.walk(Paths.get(path)).forEach(ruta-> {
			if (Files.isRegularFile(ruta) && getFileExtension(new File(ruta.toString()) ).equals(extension)) {
		        //System.out.println(ruta);
				archivos.add(ruta);
		    }
		});
		
		return archivos;
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
