package pe.soapros.asistente.domain;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase para trabajar las entidades extraídas del OCR
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class Documento {
	List<Palabra> palabras;
	
	
	public Documento() {
		palabras = new ArrayList<Palabra>();
	}
	
	public void addPalabra(Palabra palabra) {
		palabras.add(palabra);
	}
	
	public void ordernarDocumento() {
		//ordenar primero por la coordenada "y" de menor a mayor
		Collections.sort(palabras);
		
	}
	
	public void formarResultante() throws IOException {
		
		String pathfile = "c:/Temp/";
		
		PrintStream out = new PrintStream(pathfile + "respuesta33.txt");
		StringBuffer cadena = new StringBuffer();
		int inc = 10;
		int minY = palabras.get(0).getPuntos().get(0).getY();
		for (Palabra pal:palabras) {
			minY = minY + inc;
			cadena.append(pal.valor);
			cadena.append(" ");
			if(pal.getPuntos().get(0).getY()> minY) {
				minY = pal.getPuntos().get(0).getY();
				out.printf(cadena.toString() + "\n");
			}
		}
	}
}
