package pe.soapros.asistente.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase para gestionar cada una de las palabras del OCR
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class Palabra implements Comparable<Palabra> {

	String valor;
	List<Punto> puntos;

	public Palabra() {
		puntos = new ArrayList<Punto>();
	}

	/**
	 * The bounding box for the word. The vertices are in the order of top-left,
	 * top-right, bottom-right, bottom-left. When a rotation of the bounding box is
	 * detected the rotation is represented as around the top-left corner as defined
	 * when the text is read in the 'natural' orientation. For example: * when the
	 * text is horizontal it might look like: 0----1 | | 3----2 * when it's rotated
	 * 180 degrees around the top-left corner it becomes: 2----3 | | 1----0 and the
	 * vertice order will still be (0, 1, 2, 3).
	 */
	public void addPuntos(int x, int y) {
		Punto p = new Punto();
		p.setX(x);
		p.setY(y);
		puntos.add(p);
	}

	public int getAncho() {
		int ancho = 0;

		if (puntos.size() > 3) {

			// obtener el punto 0, que es la esquina superior izquierda
			int izq = puntos.get(0).getX();

			// ontener el punto 1, que es la esquina superior derecha
			int der = puntos.get(1).getX();

			ancho = der - izq;
		}

		return ancho;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<Punto> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<Punto> puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return "Palabra [valor=" + valor + ", puntos=" + puntos + "]";
	}

	public double calcularTamañoLetra() {
		double rpta = 0.0;

		int taman = valor.length();
		int ancho = puntos.get(1).getX() - puntos.get(0).getX();

		rpta = ancho / taman;

		return rpta;
	}

	public int compareTo(Palabra arg0) {
		Palabra pal = arg0;

		int valDouble = pal.getPuntos().get(0).getY();

		int valCompDouble = this.getPuntos().get(0).getY();

		int rpta = valCompDouble - valDouble;

		return rpta;
	}
	
	public static Comparator<Palabra> PalabraComparatorYX = new Comparator<Palabra>() {
		
		public int compare(Palabra p1, Palabra p2) {
			String svalx1 = "" + p1.getPuntos().get(0).getX();
			String svaly1 = "" + p1.getPuntos().get(0).getY();
			String x1 = svalx1 + svaly1;
			int valx1 = Integer.valueOf(x1);
			
			
			String svalx2 = "" + p2.getPuntos().get(2).getX();
			String svaly2 = "" + p2.getPuntos().get(2).getY();
			
			String x2 = svalx2 + svaly2;
			int valx2 = Integer.valueOf(x2);
			
			return valx1 - valx2;
		}

	};

	public static Comparator<Palabra> PalabraComparatorX = new Comparator<Palabra>() {
		public int compare(Palabra p1, Palabra p2) {
			int valx1 = p1.getPuntos().get(0).getX();
			int valx2 = p2.getPuntos().get(0).getX();

			return valx1 - valx2;
		}
	};
	
	public static Comparator<Palabra> PalabraComparatorX2 = new Comparator<Palabra>() {
		public int compare(Palabra p1, Palabra p2) {
			int valx1 = p1.getPuntos().get(1).getX();
			int valx2 = p2.getPuntos().get(1).getX();

			return valx1 - valx2;
		}
	};


	public static Comparator<Palabra> PalabraComparatorY = new Comparator<Palabra>() {
		public int compare(Palabra p1, Palabra p2) {
			int valx1 = p1.getPuntos().get(0).getY();
			int valx2 = p2.getPuntos().get(0).getY();

			return valx1 - valx2;
		}
	};
	
	public static Comparator<Palabra> PalabraComparatorY2 = new Comparator<Palabra>() {
		public int compare(Palabra p1, Palabra p2) {
			int valx1 = p1.getPuntos().get(1).getY();
			int valx2 = p2.getPuntos().get(1).getY();

			return valx1 - valx2;
		}
	};
	
	

}
