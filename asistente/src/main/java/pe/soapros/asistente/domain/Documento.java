package pe.soapros.asistente.domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Clase para trabajar las entidades extraídas del OCR
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */

@Configuration
@PropertySource("classpath:propiedades.properties")
public class Documento {

	private List<Palabra> palabras;

	@Value("${anchorenglon}")
	private int anchoRenglon = 15;

	@Value("${anchocol}")
	private int anchoCol = 8;

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

	/**
	 * Método que permite ordenar el archivo resultando, acercandolo al formato
	 * definido en la imagen
	 * 
	 * @param pathFile
	 *            Ruta Destino del archivo resultante
	 * @param fileName
	 *            Nombre del archivo
	 * @throws IOException
	 */
	public void formarResultante(String pathFile, String fileName) throws IOException {

		// ordenar por la coordenada Y todas las palabras detectadas
		Collections.sort(palabras, Palabra.PalabraComparatorY);

		// arreglo de renglones
		List<List<Palabra>> lstRenglones = new ArrayList<List<Palabra>>();

		// tamaño del renglon
		int inc = this.anchoRenglon;

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

		// String pathfile = "c:/Temp/";

		PrintStream out = new PrintStream(pathFile + File.separator + fileName);

		StringBuffer cadena = new StringBuffer();

		// recorrer los renglones
		for (List<Palabra> pals : lstRenglones) {

			int anchoCol = this.anchoCol;
			int colActual = 0;

			for (Palabra pp : pals) {
				// obtener la columna de la izquierda de cada palabra, y determinar en qué
				// columna estar
				int izqCol = pp.getPuntos().get(0).getX();
				int derCol = pp.getPuntos().get(1).getX();

				// Identificación de las columnas
				// int colDer = (int)Math.ceil(derCol / anchoCol);
				int colIzq = (int) Math.ceil(izqCol / anchoCol);

				// calcular cuantas columnas existen en blanco respecto a la última palabra
				int colEspacio = colIzq - colActual;

				// determinar la cantidad de palabras o columnas reales de la palabra
				int cantLetras = pp.getValor().length();

				for (int k = 0; k < colEspacio - 2; k++) {
					cadena.append(" ");
					colActual++;
				}

				// se agrega el valor de la palabra
				cadena.append(pp.getValor());

				colActual = colActual + cantLetras;

				if (pp.getValor().length() > 1
						&& Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
					colActual++;
				}

			}

			colActual = 0;

			out.println(cadena.toString());
			cadena.delete(0, cadena.length());

		}

		//
		out.close();
	}
}
