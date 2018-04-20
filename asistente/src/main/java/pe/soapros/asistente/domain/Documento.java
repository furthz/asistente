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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase para trabajar las entidades extraídas del OCR
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */

public class Documento {

	private List<Palabra> palabras;

	private List<Palabra> bloques;

	private int anchoRenglon = 15;

	private double anchoCol = 4;

	private boolean swHorizontal = false;
	
	private Propiedades propiedades;
	
	protected final Log logger = LogFactory.getLog(getClass());

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
	
	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
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

	private int calcularCols() {

		logger.debug("Calcular cols");
		
		HashMap<Palabra, Integer> lstCols = new HashMap<Palabra, Integer>();

		Collections.sort(bloques, Palabra.PalabraComparatorX);

		Palabra inicio = this.bloques.get(0);
		logger.debug("inicio: " + inicio);

		for (Palabra pal : this.bloques) {
			logger.debug("pal: " + pal);
			// se verificó el eje horizontal
			if ((pal.getPuntos().get(0).getX() <= inicio.getPuntos().get(0).getX() + 5
					&& pal.getPuntos().get(0).getX() >= inicio.getPuntos().get(0).getX() - 5)
					|| (pal.getPuntos().get(1).getX() <= inicio.getPuntos().get(1).getX() + 5
							&& pal.getPuntos().get(1).getX() >= inicio.getPuntos().get(1).getX() - 5)) {

				logger.debug("condicion bloque: " + pal);


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
				logger.debug("condicion inicio: " + pal);
				inicio = pal;
			}
		}

		logger.debug("columnas: " + lstCols);
		
		Iterator<Entry<Palabra, Integer>> it = lstCols.entrySet().iterator();
		int col = 0;
		while (it.hasNext()) {
			Entry<Palabra, Integer> pair = it.next();
			Integer value = (Integer) pair.getValue();
			if (value >= 2) {
				col++;
			}
		}

		return col;
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
	public void formarResultante(String pathFile, String fileName, boolean swPrueba) throws IOException {

		logger.debug("resultante");
		
		// calcular las columnas
		double factor = 0;
		int col = this.calcularCols();
		
		if(col==0)
			col = 2;
		logger.debug("columnas: " + col);

		if (this.swHorizontal) {
			this.anchoRenglon = 6;
			if (col > 4) {
				factor = 1.5;
			} else {
				factor = 3;
			}
		} else {
			this.anchoRenglon = Integer.valueOf(this.propiedades.getAnchoRenglon());
			if(col >=8) {
				col = 4;
				factor = 2.5;
			}
			else if (col >= 4 && col < 8) {
				col = 4;
				factor = 2;
			} else if (col == 1) {
				col = 2;
				factor = 4;
			} else {
				factor = 6;

			}
		}
				
		this.anchoCol = col * factor;

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

			double anchoCol = this.anchoCol;

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

				for (int k = 0; k < colEspacio - 2; k++) {
					cadena.append(" ");
					// cadena = cadena.concat(" ");
					colActual++;
				}
				logger.debug("Se agregaron espacions blancos: " + (colEspacio - 2));
				
				
				//si ha habido un numero antes, verificar que no siga una "," o un "." de no ser asi agregar un espacio en blanco
				
				if(swNum && pp.getValor().contains(",") && pp.getValor().length()==1) {					
					logger.debug("Se continua escribiendo un numero");
				}else if(swNum && pp.getValor().contains(".") && pp.getValor().length()==1) {
					logger.debug("Se continua escribiendo un numero");
				}else if(swNum && StringUtils.isNumeric(pp.getValor())){
					logger.debug("Se continua escribiendo un numero");
				}
				else if (swNum && !StringUtils.isNumeric(pp.getValor()) && pp.getValor().length()>1) {
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
					//caso para letras unicas
				}else if(pp.getValor().length() == 1 && Character.isLetter(pp.getValor().charAt(pp.getValor().length() - 1))) {
					cadena.append(" ");
					colActual++;
					logger.debug("Se agregó un espacio en blanco");
					//en el caso que sea solo un numero
				}else if(StringUtils.isNumeric(pp.getValor())) {
					swNum = true;
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
