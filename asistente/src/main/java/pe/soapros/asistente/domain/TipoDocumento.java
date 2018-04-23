package pe.soapros.asistente.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tipoarchivos")
public class TipoDocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipos_generator")
	@SequenceGenerator(name = "tipos_generator", sequenceName = "tipo_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER) // (optional = false, fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "idarchivo")
	private UploadFile archivo;

	private String empresa;
	private String idEmpresa;
	private String tipoDoc;
	private String fecha;
	private String unidad;
	private String objectId;
	private String filename;
	private String objectIdTxt;
	private String filenameTxt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechacreacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UploadFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadFile archivo) {
		this.archivo = archivo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String id) {
		this.idEmpresa = id;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getObjectIdTxt() {
		return objectIdTxt;
	}

	public void setObjectIdTxt(String objectIdTxt) {
		this.objectIdTxt = objectIdTxt;
	}

	public String getFilenameTxt() {
		return filenameTxt;
	}

	public void setFilenameTxt(String filenameTxt) {
		this.filenameTxt = filenameTxt;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	@Override
	public String toString() {
		return "TipoDocumento [empresa=" + empresa + ", id=" + idEmpresa + ", tipoDoc=" + tipoDoc + ", fecha=" + fecha
				+ ", unidad=" + unidad + ", objectId=" + objectId + ", filename=" + filename + ", objectIdTxt="
				+ objectIdTxt + ", filenameTxt=" + filenameTxt + "]";
	}

}
