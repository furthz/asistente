package pe.soapros.asistente.domain;

/**
 * Clase para gestionar los puntos X,Y de cada palabra obtenida en el OCR
 * @author Raúl Talledo
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
	
	public boolean equalsY(Punto obj) {
		boolean rpta = false;
		
		int dif = this.getY() - obj.getY();
		
		if( dif == 0) {
			rpta = true;
		} else if(dif > 0 && dif <= 5) {
			rpta = true;
		} else if(dif < 0 && dif >= -5) {
			rpta = true;
		}
		return rpta;
	}
	
	
}
