package pe.soapros.asistente.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Clase para gestionar los datos asociados a la Entidad Empresa
 * @author Raúl Talledo
 * @version 1.0
 */
@Entity
@Table(name="empresas") 
public class Empresa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
	@SequenceGenerator(name="book_generator", sequenceName = "book_seq", allocationSize=5)
	@Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	private String nombre;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	
}
