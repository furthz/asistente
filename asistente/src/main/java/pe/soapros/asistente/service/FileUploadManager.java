package pe.soapros.asistente.service;

import java.io.Serializable;

import pe.soapros.asistente.domain.UploadFile;

public interface FileUploadManager extends Serializable{
	
	public void saveArchivo(UploadFile file);
	
}
