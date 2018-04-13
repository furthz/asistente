package pe.soapros.asistente.repository;

import java.util.List;

import pe.soapros.asistente.domain.TipoDocumento;

public interface TipoDocumentoDao {

	public List<TipoDocumento> getEmpresaList();
	
	public void saveTipoDocumento(TipoDocumento tipo);
	
}
