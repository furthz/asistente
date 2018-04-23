package pe.soapros.asistente.web;

import java.io.IOException;
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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.UploadFile;
//import pe.soapros.asistente.service.EmpresaManager;
import pe.soapros.asistente.service.FileUploadManager;

@Controller
public class ListarEmpresaController {

	protected final Log logger = LogFactory.getLog(getClass());

	//@Autowired
	//private EmpresaManager empresaManager;

	@Autowired
	private FileUploadManager fileManager;

	@RequestMapping(value = "/listaempresas.htm", method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		
		List<UploadFile> lstFiles = this.fileManager.getFilesWithSoons();
		
		PagedListHolder pageList = new PagedListHolder(lstFiles);
		
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		
		pageList.setPage(page);
		pageList.setPageSize(20);
		

		return new ModelAndView("listarempresas", "pageList", pageList);
	}

	//public void setEmpresaManager(EmpresaManager empresaManager) {
	//	this.empresaManager = empresaManager;
	//}

}
