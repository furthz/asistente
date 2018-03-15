package pe.soapros.asistente.repository;

import pe.soapros.asistente.domain.UploadFile;

public interface FileUploadDao {
	void save(UploadFile uploadFile);
}
