package pe.soapros.asistente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.repository.FileUploadDao;

@Component
public class FileUploadManagerImpl implements FileUploadManager{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FileUploadDao fileUploadDao;
	
	public void setFileUploadDao(FileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}
	
	public void saveArchivo(UploadFile file) {
		this.fileUploadDao.save(file);
	}

}
