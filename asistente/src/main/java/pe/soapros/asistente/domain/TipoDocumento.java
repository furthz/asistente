package pe.soapros.asistente.domain;

public class TipoDocumento {

	private String empresa;
	private String id;
	private String tipoDoc;
	private String fecha;
	private String unidad;
	private String objectId;

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "TipoDocumento [empresa=" + empresa + ", id=" + id + ", tipoDoc=" + tipoDoc + ", fecha=" + fecha
				+ ", unidad=" + unidad + "]";
	}

}
