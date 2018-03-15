package pe.soapros.asistente.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MultipleFileUploadForm {

	private long empresa;
	
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(long idEmpresa) {
		this.empresa = idEmpresa;
	}

	
}
