package pe.soapros.asistente.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.service.FileUploadManager;

@Controller
public class FindEmpresaController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private FileUploadManager fileManager;

	@RequestMapping(value = "/findEmpresa.htm", method = RequestMethod.POST)
	public ModelAndView handleFileUploadMA(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletRequestBindingException {

		String texto = ServletRequestUtils.getStringParameter(request, "texto");
		//String texto = buscarForm.getTexto();
		logger.debug("Buscar por: " + texto);

		List<UploadFile> lstFiles = this.fileManager.findByEmpresaNombre(texto.toUpperCase());

		PagedListHolder<UploadFile> pageList = new PagedListHolder<UploadFile>(lstFiles);

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		pageList.setPage(page);
		pageList.setPageSize(20);

		return new ModelAndView("listarempresas", "pageList", pageList);

	}
}
