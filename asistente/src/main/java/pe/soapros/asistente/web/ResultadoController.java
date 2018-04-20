package pe.soapros.asistente.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.funcionality.PlanCuentaNLU;
import pe.soapros.asistente.funcionality.ServiceECM;
import pe.soapros.asistente.service.TipoDocumentoManager;
import pe.soapros.asistente.util.Util;

@Controller
public class ResultadoController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private Propiedades propiedades; // = new Propiedades();

	private String pathfile = ""; 
	
	private PlanCuenta cuentas;
	
	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;

	@RequestMapping(value = "/resultados.htm")
	public ModelAndView handleRequest(@RequestParam("id") Long id) throws ServletException, IOException {

		logger.debug("Carpeta temporal: " + propiedades.getTemporal());
		
		this.pathfile = propiedades.getTemporal();
		
		List<TipoDocumento> lstFiles = this.tipoDocumentoManager.getTiposDocumentosById(id);
		
		ServiceECM service = new ServiceECM();
		service.setPropiedades(this.propiedades);
		
		Path basePath = Paths.get(this.pathfile);
		Path path = Files.createTempDirectory(basePath, "download");
		
		
		String contenido = "";
		String ruta = "";
		String rutaImagen = "";
		
		for (TipoDocumento tipo : lstFiles) {
			if (tipo.getTipoDoc().trim().equals("Balance")) {
				
				ruta = service.downloadFileById(tipo.getObjectIdTxt().trim(), tipo.getFilenameTxt().trim(), path.toString().trim());
				logger.debug("ruta : " + ruta);
				
				rutaImagen = service.downloadFileById(tipo.getObjectId().trim(), tipo.getFilename().trim(), path.toString().trim());
				logger.debug("ruta imagen: " + rutaImagen);
				
				contenido += Util.leerArchivoTXT(ruta);
			}
		}

		//
		PlanCuentaNLU plan = new PlanCuentaNLU();
		plan.setPropiedades(this.propiedades);
		
		cuentas = plan.consultarCuentas(contenido);
		
		HashMap<String, Object> objetos = new HashMap<String, Object>();
		objetos.put("plan", cuentas);
		objetos.put("imagen", rutaImagen);

		return new ModelAndView("resultados", "objetos", objetos);
	}
	
	 @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	 public ModelAndView downloadExcel() {
		 
		 return new ModelAndView("excelView", "planCuentas", this.cuentas);
	 }

}
