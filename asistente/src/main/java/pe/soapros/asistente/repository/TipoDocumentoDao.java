package pe.soapros.asistente.repository;

import java.util.List;

import pe.soapros.asistente.domain.TipoDocumento;

public interface TipoDocumentoDao {

	public List<TipoDocumento> getTiposDocumentos();
	
	public List<TipoDocumento> getTiposDocumentosById(Long id);
	
	public void saveTipoDocumento(TipoDocumento tipo);
	
	public TipoDocumento getId(Long id);
	
}
