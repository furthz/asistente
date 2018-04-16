package pe.soapros.asistente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.repository.TipoDocumentoDao;

@Component
public class TipoDocumentoManagerImpl implements TipoDocumentoManager{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoDocumentoDao tipoDocumentoDao;
	
	@Override
	public List<TipoDocumento> getTiposDocumentos() {
		List<TipoDocumento> lst = this.tipoDocumentoDao.getTiposDocumentos();
		return lst;
	}

	@Override
	public void crearTipoDocumento(TipoDocumento tipo) {
		this.tipoDocumentoDao.saveTipoDocumento(tipo);
		
	}

	@Override
	public List<TipoDocumento> getTiposDocumentosById(Long id) {

		return this.tipoDocumentoDao.getTiposDocumentosById(id);
	}

}
