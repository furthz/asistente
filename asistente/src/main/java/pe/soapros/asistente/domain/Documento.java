package pe.soapros.asistente.domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Clase para trabajar las entidades extra�das del OCR
 * 
 * @author Ra�l Talledo
 * @version 1.0
 *
 */

public class Documento {

	private List<Palabra> palabras;

	private List<Palabra> bloques;

	private int anchoRenglon = 15;

	private double anchoCol = 4;

	private boolean swHorizontal = false;

	public Documento() {
		palabras = new ArrayList<Palabra>();
	}

	public List<Palabra> getBloques() {
		return bloques;
	}

	public void setBloques(List<Palabra> bloques) {
		this.bloques = bloques;
	}

	public boolean isSwHorizontal() {
		return swHorizontal;
	}

	public void setSwHorizontal(boolean swHorizontal) {
		this.swHorizontal = swHorizontal;
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

		// sumar el tama�o de cada letra
		double suma = 0;

		for (Palabra pal : palabras) {
			suma = suma + pal.calcularTama�oLetra();
		}

		promedioLetra = suma / cantPalabras;

		return promedioLetra;
	}

	public double promedioPalabraRenglon(List<Palabra> renglon) {
		double rpta = 0;

		int cantPalabras = renglon.size();

		double suma = 0;

		for (Palabra pal : renglon) {
			suma = suma + pal.calcularTama�oLetra();
		}

		rpta = suma / cantPalabras;

		return rpta;
	}

	private int calcularCols() {

		HashMap<Palabra, Integer> lstCols = new HashMap<Palabra, Integer>();

		Collections.sort(bloques, Palabra.PalabraComparatorX);

		// Collections.sort(bloques, Palabra.PalabraComparatorY);

		Palabra inicio = this.bloques.get(0);

		for (Palabra pal : this.bloques) {
			// se verific� el eje horizontal
			if ((pal.getPuntos().get(0).getX() <= inicio.getPuntos().get(0).getX() + 4
					&& pal.getPuntos().get(0).getX() >= inicio.getPuntos().get(0).getX() - 4)
					|| (pal.getPuntos().get(1).getX() <= inicio.getPuntos().get(1).getX() + 4
							&& pal.getPuntos().get(1).getX() >= inicio.getPuntos().get(1).getX() - 4)) {

				// verificar si estamos dentro de los l�mites verticales
				// if((pal.getPuntos().get(1).getY() >= inicio.getPuntos().get(1).getY()) &&
				// (pal.getPuntos().get(2).getY() <= inicio.getPuntos().get(2).getY())) {

				if (lstCols.get(inicio) == null) {
					lstCols.put(inicio, 1);
				} else {
					int cant = lstCols.get(inicio) + 1;
					lstCols.put(inicio, cant);
				}

				// }else {
				// inicio = pal;
				// }

			} else {
				inicio = pal;
			}
		}

		Iterator<Entry<Palabra, Integer>> it = lstCols.entrySet().iterator();
		int col = 0;
		while (it.hasNext()) {
			Entry<Palabra, Integer> pair = it.next();
			Integer value = (Integer) pair.getValue();
			if (value > 2) {
				col++;
			}
		}

		return col;
	}

	/**
	 * M�todo que permite ordenar el archivo resultando, acercandolo al formato
	 * definido en la imagen
	 * 
	 * @param pathFile
	 *            Ruta Destino del archivo resultante
	 * @param fileName
	 *            Nombre del archivo
	 * @throws IOException
	 */
	public void formarResultante(String pathFile, String fileName, boolean swPrueba) throws IOException {

		// calcular las columnas
		double factor = 0;
		int col = this.calcularCols();

		if (this.swHorizontal) {
			this.anchoRenglon = 6;
			if (col > 4) {
				factor = 1.5;
			} else {
				factor = 3;
			}
		} else {
			this.anchoRenglon = 14;
			if (col >= 4) {
				col = 4;
				factor = 2;
			} else if (col == 1) {
				col = 2;
				factor = 4;
			} else {
				factor = 2.5;

			}
		}

		this.anchoCol = col * factor;

		// ordenar por la coordenada Y todas las palabras detectadas
		Collections.sort(palabras, Palabra.PalabraComparatorY);

		// arreglo de renglones
		List<List<Palabra>> lstRenglones = new ArrayList<List<Palabra>>();

		// tama�o del renglon
		double inc = this.anchoRenglon;

		// valor inicial de la posici�n del renglon m�s el valor increental
		double minY = palabras.get(0).getPuntos().get(0).getY();

		// lista de palabras dentro de cada renglong
		List<Palabra> palabrasInRenglon = new ArrayList<Palabra>();

		// recorrer todas las palabras detectadas
		for (Palabra pal : palabras) {

			// si la posisci�n Y es superior al renglon, se a�ade el renglon identificado
			if (pal.getPuntos().get(0).getY() > minY) {

				// ordenar dentro de cada renglon por la coordenada X
				Collections.sort(palabrasInRenglon, Palabra.PalabraComparatorX);

				// se agreg� el renglon identificado
				lstRenglones.add(palabrasInRenglon);

				// se reinicia las palabras dentro de cada renglon
				palabrasInRenglon = new ArrayList<Palabra>();

				// se incrementa el liminte del sgte renglon
				minY = pal.getPuntos().get(0).getY() + inc;

			}

			// se a�ade las palabras dentro de cada renglon
			palabrasInRenglon.add(pal);

		}

		// agregar el ultimo renglon
		// ordenar dentro de cada renglon por la coordenada X
		Collections.sort(palabrasInRenglon, Palabra.PalabraComparatorX);

		// se agreg� el renglon identificado
		lstRenglones.add(palabrasInRenglon);

		// String pathfile = "c:/Temp/";		

		String archivo = new String();
		StringBuilder cadena;
		
		// recorrer los renglones
		for (List<Palabra> pals : lstRenglones) {
			
			cadena = new StringBuilder();
			// String cadena = new String();

			double anchoCol = this.anchoCol;

			int colActual = 0;

			for (Palabra pp : pals) {
				// obtener la columna de la izquierda de cada palabra, y determinar en qu�
				// columna estar
				int izqCol = pp.getPuntos().get(0).getX();

				// Identificaci�n de las columnas
				int colIzq = (int) Math.ceil(izqCol / anchoCol);

				// calcular cuantas columnas existen en blanco respecto a la �ltima palabra
				int colEspacio = colIzq - colActual;

				// determinar la cantidad de palabras o columnas reales de la palabra
				int cantLetras = pp.getValor().length();

				for (int k = 0; k < colEspacio - 2; k++) {
					cadena.append(" ");
					// cadena = cadena.concat(" ");
					colActual++;
				}

				// se agrega el valor de la palabra
				cadena.append(pp.getValor());
				// cadena = cadena.concat(pp.getValor());

				colActual = colActual + cantLetras;

				if (pp.getValor().length() > 1
						&& Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
					// cadena = cadena.concat(" ");
					colActual++;
				}

			} // fin del renglon

			colActual = 0;

			archivo = archivo.concat(cadena.toString() + "\r\n");
			if(swPrueba) {
				archivo = archivo.concat("\n");
			}
			// out.println(cadena.toString() + "\r\n");
			// cadena.delete(0, cadena.length());

		}

		//
		PrintStream out = new PrintStream(pathFile + File.separator + fileName, "UTF-8");
		out.print(archivo);
		out.flush();
		out.close();
	}
}
