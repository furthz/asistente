package pe.soapros.asistente.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar cada una de las palabras extraídas del OCR
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class Palabra implements Comparable{

	String valor;	
	List<Punto> puntos;
	
	public Palabra() {
		puntos = new ArrayList<Punto>();
	}
	
	/**
	   * The bounding box for the word.
	   * The vertices are in the order of top-left, top-right, bottom-right,
	   * bottom-left. When a rotation of the bounding box is detected the rotation
	   * is represented as around the top-left corner as defined when the text is
	   * read in the 'natural' orientation.
	   * For example:
	   *   * when the text is horizontal it might look like:
	   *      0----1
	   *      |    |
	   *      3----2
	   *   * when it's rotated 180 degrees around the top-left corner it becomes:
	   *      2----3
	   *      |    |
	   *      1----0
	   *   and the vertice order will still be (0, 1, 2, 3).
	   *   */
	public void addPuntos(int x, int y) {
		Punto p = new Punto();
		p.setX(x);
		p.setY(y);
		puntos.add(p);
	}
	
	public int getAncho() {
		int ancho = 0;
		
		if (puntos.size()>3) {
			
			//obtener el punto 0, que es la esquina superior izquierda
			int izq = puntos.get(0).getX();
			
			//ontener el punto 1, que es la esquina superior derecha
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

	@Override
	public int compareTo(Object arg0) {
		Palabra pal = (Palabra) arg0;
		
		String val = "" + pal.getPuntos().get(0).getY() + pal.getPuntos().get(0).getX();
		int valDouble = Integer.valueOf(val);
		
//		String val1 = "" + pal.getPuntos().get(3).getY() + pal.getPuntos().get(3).getX();
//		int val1Double = Integer.valueOf(val1);
		
		String valComp = "" + this.getPuntos().get(0).getY() + this.getPuntos().get(0).getX();
		int valCompDouble = Integer.valueOf(valComp);
		
//		String val1Comp = "" + this.getPuntos().get(3).getY() + this.getPuntos().get(3).getX();
//		int val1CompDouble = Integer.valueOf(val1Comp);
		
		//valCompDouble = (val1CompDouble + val1CompDouble) / 2;
		
		//valDouble = (valDouble + val1Double) / 2;
		
		
		int rpta = valCompDouble - valDouble; 
		return rpta;
	}
	
	
	
}
