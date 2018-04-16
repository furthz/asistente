package pe.soapros.asistente.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.Empresa;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.service.EmpresaManager;
import pe.soapros.asistente.service.FileUploadManager;

@Controller
public class ListarEmpresaController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private EmpresaManager empresaManager;

	@Autowired
	private FileUploadManager fileManager;

	@RequestMapping(value = "/listaempresas.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String now = (new Date()).toString();
		logger.info("Returning hello view with " + now);

		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("now", now);
//		List<Empresa> lstEmpresas = this.empresaManager.getEmpresas();
//		
//		for(Empresa emp: lstEmpresas) {
//			emp.getFiles();
//		}
		
		List<UploadFile> lstFiles = this.fileManager.getFilesWithSoons();
		
		
		
//		myModel.put("empresas", lstEmpresas);
		myModel.put("files", lstFiles);

		return new ModelAndView("listarempresas", "model", myModel);
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
	

}
