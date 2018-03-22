package pe.soapros.asistente.domain;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase para trabajar las entidades extraídas del OCR
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class Documento {
	List<Palabra> palabras;
	double promedioLetra;

	public Documento() {
		palabras = new ArrayList<Palabra>();
	}

	public void addPalabra(Palabra palabra) {
		palabras.add(palabra);
	}

	public void ordernarDocumento() {
		// ordenar primero por la coordenada "y" de menor a mayor
		Collections.sort(palabras);

	}

	public double getPromedioLetra() {
		double promedioLetra = 0.0;

		int cantPalabras = palabras.size();

		// sumar el tamaño de cada letra
		double suma = 0;

		for (Palabra pal : palabras) {
			suma = suma + pal.calcularTamañoLetra();
		}

		promedioLetra = suma / cantPalabras;

		return promedioLetra;
	}

	public double promedioPalabraRenglon(List<Palabra> renglon) {
		double rpta = 0;

		int cantPalabras = renglon.size();

		double suma = 0;

		for (Palabra pal : renglon) {
			suma = suma + pal.calcularTamañoLetra();
		}

		rpta = suma / cantPalabras;

		return rpta;
	}

	public void formarResultante() throws IOException {

		// ordenar por la coordenada Y todas las palabras detectadas
		Collections.sort(palabras, Palabra.PalabraComparatorY);

		// arreglo de renglones
		List<List<Palabra>> lstRenglones = new ArrayList<List<Palabra>>();

		// tamaño del renglon
		int inc = 15;

		// valor inicial de la posición del renglon más el valor increental
		int minY = palabras.get(0).getPuntos().get(0).getY() + inc;

		// lista de palabras dentro de cada renglong
		List<Palabra> palabrasInRenglon = new ArrayList<Palabra>();

		// recorrer todas las palabras detectadas
		for (Palabra pal : palabras) {

			// si la posisción Y es superior al renglon, se añade el renglon identificado
			if (pal.getPuntos().get(0).getY() > minY) {

				// ordenar dentro de cada renglon por la coordenada X
				Collections.sort(palabrasInRenglon, Palabra.PalabraComparatorX);

				// se agregó el renglon identificado
				lstRenglones.add(palabrasInRenglon);

				// se reinicia las palabras dentro de cada renglon
				palabrasInRenglon = new ArrayList<Palabra>();

				// se incrementa el liminte del sgte renglon
				minY = pal.getPuntos().get(0).getY() + inc;

			}

			// se añade las palabras dentro de cada renglon
			palabrasInRenglon.add(pal);

		}

		String pathfile = "c:/Temp/";

		PrintStream out = new PrintStream(pathfile + "respuesta33.txt");

		StringBuffer cadena = new StringBuffer();

		// recorrer los renglones
		for (List<Palabra> pals : lstRenglones) {

			int anchoCol = 8;
			int colActual = 0;

			for (Palabra pp : pals) {
				// obtener la columna de la izquierda de cada palabra, y determinar en qué
				// columna estar
				int izqCol = pp.getPuntos().get(0).getX();

				// calcular cuantas columnas existen respecto al punto izquierdo
				double calCol = (Math.ceil(izqCol / anchoCol)) - colActual;

				for (int k = 0; k < calCol - 1; k++) {
					cadena.append(" ");
				}

				colActual = colActual + (int) calCol;

				// se agrega el valor de la palabra

				cadena.append(pp.getValor());

				if (pp.getValor().length()>1 && Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
				}

				// determinar el ancho de la palabra y cuantas columnas ocupa

				calCol = Math.ceil(pp.getAncho() / anchoCol)+ 1;

				colActual = colActual + (int) calCol;

			}

			out.println(cadena.toString());

			cadena.delete(0, cadena.length());			

		}

		System.out.println("Fin");

	}
}
