package pe.soapros.asistente.domain;

import java.util.Arrays;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "archivos")
public class UploadFile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_generator")
	@SequenceGenerator(name = "file_generator", sequenceName = "file_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String fileName;
	private byte[] datos;

	@ManyToOne(fetch = FetchType.EAGER) // (optional = false, fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name = "idempresa")
	private Empresa empresa;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "archivo", cascade = CascadeType.ALL)
	private Set<TipoDocumento> tipos = new HashSet<TipoDocumento>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "filename")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_data")
	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] data) {
		this.datos = data;
	}

	public void addTipos(TipoDocumento tipo) {
		this.tipos.add(tipo);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "UploadFile [id=" + id + ", fileName=" + fileName + ", data=" + Arrays.toString(datos) + "]";
	}

}
