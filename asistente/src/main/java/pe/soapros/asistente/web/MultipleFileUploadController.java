package pe.soapros.asistente.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.Empresa;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.form.MultipleFileUploadForm;
import pe.soapros.asistente.funcionality.ConvertImageToText;
import pe.soapros.asistente.service.EmpresaManager;
import pe.soapros.asistente.service.FileUploadManager;
import pe.soapros.asistente.service.TipoDocumentoManager;

@Controller
public class MultipleFileUploadController {

	protected final Log logger = LogFactory.getLog(getClass());

	private String pathfile = "C:/Temp/";

	@Autowired
	private FileUploadManager fileUploadManager;

	@Autowired
	private EmpresaManager empresaManager;
	
	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;

	public void setFileUploadManager(FileUploadManager fileUploadManager) {
		this.fileUploadManager = fileUploadManager;
	}

	@RequestMapping(value = "/loadMultipleFileUploadMA")
	public String loadMultipleFileUploadMA(Map<String, Object> model) {
		return "MultipleFileUploadMA";
	}

	@RequestMapping(value = "/uploadMultipleFilesMA", method = RequestMethod.POST)
	public String handleFileUploadMA(
			@ModelAttribute("multipleFileUploadForm") MultipleFileUploadForm multipleFileUploadForm, Model model)
			throws IOException {

		List<MultipartFile> files = multipleFileUploadForm.getFiles();

		// long id = multipleFileUploadForm.getEmpresa();
		//
		// System.out.println("Id Empresa: " + id);
		//
		// Empresa emp = this.empresaManager.buscarById(id);

		logger.info(" Files count " + files.size());

		Path basePath = Paths.get(this.pathfile);
		Path path = Files.createTempDirectory(basePath, "upload");

		Date fecha = new Date();
		
		try {

			UploadFile archivo;
			for (int i = 0; i < files.size(); i++) {				
				
				archivo = new UploadFile();
				archivo.setFileName(files.get(i).getOriginalFilename());
				archivo.setDatos(files.get(i).getBytes());
				archivo.setFecha(fecha);
				
				ConvertImageToText convertImage = new ConvertImageToText();

				logger.debug("Se llama la conversion de texto");
				// metodo que desempaqueta, limpia y convierte en texto
				List<TipoDocumento> lstDctos = convertImage.detectDocumentText(files.get(i), path.toString());
				logger.debug("Terminó la conversion de texto");
				
				// obtener los datos de la empresa, usnado el primer elemento de la lista
				TipoDocumento tipo = lstDctos.get(0);
				
				logger.debug("Se crea la empresa");
				Empresa empresa = new Empresa();
				empresa.setIdDoc(tipo.getIdEmpresa());
				empresa.setNombre(tipo.getEmpresa());
				empresa.setFecha(fecha);
				
				logger.debug("se crea el registro del archivo");
				
				archivo.setEmpresa(empresa);
				
				empresa.addFile(archivo);				
				
				
				
				// guardar en la base de datos
				// files.get(i).transferTo(new File(filePath +
				// files.get(i).getOriginalFilename()));
				//this.fileUploadManager.saveArchivo(archivo);
				
				for(TipoDocumento tipdoc: lstDctos) {
					tipdoc.setArchivo(archivo);
					//this.tipoDocumentoManager.crearTipoDocumento(tipdoc);
					tipdoc.setArchivo(archivo);
					tipdoc.setFechacreacion(fecha);
					archivo.addTipos(tipdoc);
					
				}
				
				this.empresaManager.crearEmpresa(empresa);
				logger.debug("Se guardó en la base de datoss");
				
				
			}
		} catch (Exception e) {
			return "Error While uploading your files " + e.getMessage();
		}

		model.addAttribute("files", files);
		return "result";

	}

	@RequestMapping(value = "/loadMultipleFileUpload")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String now = (new Date()).toString();
		// logger.info("Returning hello view with " + now);
		//
		// Map<String, Object> myModel = new HashMap<String, Object>();
		// myModel.put("now", now);
		// myModel.put("empresas", this.empresaManager.getEmpresas());

		// return new ModelAndView("MultipleFileUpload", "model", myModel);
		return new ModelAndView("MultipleFileUpload");
	}

	/*
	 * @RequestMapping(value = "/loadMultipleFileUpload") public String
	 * loadMultipleFileUpload(Map<String, Object> model) { return
	 * "MultipleFileUpload"; }
	 */

}
