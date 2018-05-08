package pe.soapros.asistente.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

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
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.form.MultipleFileUploadForm;
import pe.soapros.asistente.funcionality.ConvertImageToText;
import pe.soapros.asistente.service.EmpresaManager;
import pe.soapros.asistente.service.FileUploadManager;

@Controller
public class MultipleFileUploadController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Propiedades propiedades;

	private String pathfile = "";

	@Autowired
	private EmpresaManager empresaManager;

	@Autowired
	private FileUploadManager fileUploadManager;

	@RequestMapping(value = "/uploadMultipleFilesMA.htm", method = RequestMethod.POST)
	public ModelAndView handleFileUploadMA(
			@ModelAttribute("multipleFileUploadForm") MultipleFileUploadForm multipleFileUploadForm, Model model)
			throws IOException {

		this.pathfile = this.propiedades.getTemporal();

		List<MultipartFile> files = multipleFileUploadForm.getFiles();

		logger.info(" Files count " + files.size());

		Path basePath = Paths.get(this.pathfile);
		Path path = Files.createTempDirectory(basePath, "upload");

		Date fecha = new Date();

		String mensaje = "";

		try {

			ConvertImageToText convertImage = new ConvertImageToText();
			convertImage.setPropiedades(this.propiedades);

			UploadFile archivo;
//			for (int i = 0; i < files.size(); i++) {

				// verificar que el archivo no se haya subido anteriormente
				UploadFile file;
				try {
					file = this.fileUploadManager.findByName(files.get(0).getOriginalFilename());
				} catch (javax.persistence.NoResultException e1) {
					file = null;
				}

				//en el caso de que sea procesado por primera vez
				if (file == null) {

					archivo = new UploadFile();
					archivo.setFileName(files.get(0).getOriginalFilename());
					archivo.setDatos("1".getBytes());//files.get(0).getBytes());
					archivo.setFecha(fecha);

					logger.debug("Se llama la conversion de texto");
					// metodo que desempaqueta, limpia y convierte en texto
					List<TipoDocumento> lstDctos = convertImage.detectDocumentText(files, path.toString());
					logger.debug("Terminó la conversion de texto");

					// obtener los datos de la empresa, usnado el primer elemento de la lista
					TipoDocumento tipo = lstDctos.get(0);

					logger.debug("Se crea la empresa");
					Empresa empresa = new Empresa();
					empresa.setIdDoc(tipo.getIdEmpresa());
					empresa.setNombre(tipo.getEmpresa().toUpperCase());
					empresa.setFecha(fecha);

					logger.debug("se crea el registro del archivo");

					archivo.setEmpresa(empresa);

					empresa.addFile(archivo);

					for (TipoDocumento tipdoc : lstDctos) {
						tipdoc.setArchivo(archivo);
						tipdoc.setArchivo(archivo);
						tipdoc.setFechacreacion(fecha);
						archivo.addTipos(tipdoc);

					}

					this.empresaManager.crearEmpresa(empresa);
					mensaje = "Se subieron correctamente";

					logger.debug("Se guardó en la base de datoss");
				} else {
					mensaje = "Este archivo ya ha sido procesado";
				}

//			}
		} catch (Exception e) {
			mensaje = "Hubo un error en el procesamiento de la imagen";
			logger.error(e);
		}

		return new ModelAndView("result", "mensaje", mensaje);
		// return "result";

	}

	@RequestMapping(value = "/loadMultipleFileUpload.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return new ModelAndView("MultipleFileUpload");
	}

}
