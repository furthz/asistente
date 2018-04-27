package pe.soapros.asistente.service;

import java.io.Serializable;
import java.util.List;

import pe.soapros.asistente.domain.UploadFile;

public interface FileUploadManager extends Serializable{
	
	public void saveArchivo(UploadFile file);
	
	public List<UploadFile> getFiles();
	
	public List<UploadFile> getFilesWithSoons();
	
	public UploadFile findByName(String name);
}
