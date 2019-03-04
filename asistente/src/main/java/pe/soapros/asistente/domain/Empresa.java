package pe.soapros.asistente.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase para gestionar los datos asociados a la Entidad Empresa
 * 
 * @author Raúl Talledo
 * @version 1.0
 */
@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
	@SequenceGenerator(name = "book_generator", sequenceName = "book_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String nombre;

	private String idDoc;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "empresa", cascade = CascadeType.ALL)
	private Set<UploadFile> files = new HashSet<UploadFile>();

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

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public void addFile(UploadFile file) {
		this.files.add(file);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Set<UploadFile> getFiles() {
		return files;
	}

	public void setFiles(Set<UploadFile> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + ", idDoc=" + idDoc + "]";
	}

}
