package pe.soapros.asistente.domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase para trabajar las entidades extraídas del OCR y convertirlas en un txt
 * formmateado similar a la imagen
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */

public class Documento {

	// Lista de palabras para formar el txt resultante
	private List<Palabra> palabras;

	// ancho del renglon
	private int anchoRenglon = 13;

	// propiedades
	private Propiedades propiedades;

	// logger
	protected final Log logger = LogFactory.getLog(getClass());

	public Documento() {
		palabras = new ArrayList<Palabra>();
	}

	/**
	 * Método para agregar una palabra a la lista
	 * 
	 * @param palabra
	 */
	public void addPalabra(Palabra palabra) {
		palabras.add(palabra);
	}

	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
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

		logger.debug("resultante");

		// ordenar por la coordenada Y todas las palabras detectadas
		Collections.sort(palabras, Palabra.PalabraComparatorY);

		// arreglo de renglones
		List<List<Palabra>> lstRenglones = new ArrayList<List<Palabra>>();

		// tamaño del renglon
		double inc = this.anchoRenglon;

		// valor inicial de la posición del renglon más el valor increental
		double minY = palabras.get(0).getPuntos().get(0).getY();

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

		// agregar el ultimo renglon
		// ordenar dentro de cada renglon por la coordenada X
		Collections.sort(palabrasInRenglon, Palabra.PalabraComparatorX);

		// se agregó el renglon identificado
		lstRenglones.add(palabrasInRenglon);

		// String pathfile = "c:/Temp/";

		String archivo = new String();
		StringBuilder cadena;

		logger.debug("Formar el TXT");
		boolean swNum = false;
		// recorrer los renglones
		for (List<Palabra> pals : lstRenglones) {
			logger.debug("Renglones: " + pals);
			swNum = false;

			cadena = new StringBuilder();
			// String cadena = new String();

			double anchoCol = 10; // this.anchoCol;

			int colActual = 0;

			for (Palabra pp : pals) {
				logger.debug("Palabra: " + pp);

				// obtener la columna de la izquierda de cada palabra, y determinar en qué
				// columna estar
				int izqCol = pp.getPuntos().get(0).getX();
				logger.debug("Col izq: " + izqCol);

				// Identificación de las columnas
				int colIzq = (int) Math.ceil(izqCol / anchoCol);
				logger.debug("Cant col: " + colIzq);

				// calcular cuantas columnas existen en blanco respecto a la última palabra
				int colEspacio = colIzq - colActual;
				logger.debug("Espacios: " + colEspacio);

				// determinar la cantidad de palabras o columnas reales de la palabra
				int cantLetras = pp.getValor().length();
				logger.debug("Cant letras: " + cantLetras);

				for (int k = 0; k < colEspacio - 1; k++) {
					cadena.append(" ");
					// cadena = cadena.concat(" ");
					colActual++;
				}
				logger.debug("Se agregaron espacions blancos: " + (colEspacio - 2));

				// si ha habido un numero antes, verificar que no siga una "," o un "." de no
				// ser asi agregar un espacio en blanco

				if (swNum && pp.getValor().contains(",") && pp.getValor().length() == 1) {
					logger.debug("Se continua escribiendo un numero");
				} else if (swNum && pp.getValor().contains(".") && pp.getValor().length() == 1) {
					logger.debug("Se continua escribiendo un numero");
				} else if (swNum && StringUtils.isNumeric(pp.getValor())) {
					logger.debug("Se continua escribiendo un numero");
				} else if (swNum && !StringUtils.isNumeric(pp.getValor()) && pp.getValor().length() > 1) {
					cadena.append(" ");
					colActual++;
					logger.debug("Se agregó un espacio en blanco");
					swNum = false;
				}

				// se agrega el valor de la palabra
				cadena.append(pp.getValor());
				logger.debug("Se agrego: " + pp.getValor());
				// cadena = cadena.concat(pp.getValor());

				colActual = colActual + cantLetras;

				if (pp.getValor().length() > 1
						&& Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
					colActual++;
					logger.debug("Se agregó un espacio en blanco");
					// caso para letras unicas
				} else if (pp.getValor().length() == 1
						&& Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
					colActual++;
					logger.debug("Se agregó un espacio en blanco");
					// en el caso que sea solo un numero
				} else if (StringUtils.isNumeric(pp.getValor())) {
					swNum = true;
				}

			} // fin del renglon

			colActual = 0;
			List<String> patrones = new ArrayList<String>();
			patrones.add("(\\d{1,3}(\\s*[,|.]\\d{3})+)(\\s*[,|.]\\d{2})?");
			patrones.add("\\d{1,2}(\\s*[,|.]\\d{2})?\\s*%");
			//String patron = "(\\d{1,3}(\\s*[,|.]\\d{3})+)(\\s*[,|.]\\d{2})?"; //falta el patron porcentajes
			
			String numero = "";
			String numeroMod = "";
			String cadenaMod = cadena.toString();
			
			for(String patron: patrones) {
				numero = "";
				numeroMod = "";
				Pattern pat = Pattern.compile(patron);
				Matcher mat = pat.matcher(cadena.toString());				
				while (mat.find()) {
					numero = mat.group(); 
					System.out.println(numero);
					numeroMod = numero.replaceAll("\\s", "");
					System.out.println(numeroMod);
					cadenaMod = cadenaMod.toString().replaceAll(numero, numeroMod + " ");
				}
			}
			
			archivo = archivo.concat(cadenaMod.toString() + "\r\n\n");


		}

		//
		PrintStream out = new PrintStream(pathFile + File.separator + fileName, "UTF-8");
		out.print(archivo);
		out.flush();
		out.close();
	}
}
