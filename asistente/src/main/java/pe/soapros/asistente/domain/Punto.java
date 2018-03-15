package pe.soapros.asistente.domain;

/**
 * Clase para gestionar los puntos X,Y de cada palabra obtenida en el OCR
 * @author Ra�l Talledo
 * @version 1.0
 *
 */
public class Punto {

	int x;
	int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Punto [x=" + x + ", y=" + y + "]";
	}
	
	
}
