package pe.soapros.asistente.service;

import java.io.Serializable;
import java.util.List;

import pe.soapros.asistente.domain.TipoDocumento;


public interface TipoDocumentoManager extends Serializable{

	public List<TipoDocumento> getEmpresas();
	
	public void crearTipoDocumento(TipoDocumento tipo);
}
