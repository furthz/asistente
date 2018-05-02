package pe.soapros.asistente.repository;

import java.util.List;

import pe.soapros.asistente.domain.UploadFile;

public interface FileUploadDao {
	void save(UploadFile uploadFile);
	
	List<UploadFile> getFiles();
	
	List<UploadFile> getFileWithSoons();
	
	UploadFile findByName(String name);
	
	List<UploadFile> findByEmpresaNombre(String texto);
	
}
