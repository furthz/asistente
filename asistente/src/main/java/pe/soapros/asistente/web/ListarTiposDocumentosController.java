package pe.soapros.asistente.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.service.TipoDocumentoManager;

@Controller
public class ListarTiposDocumentosController {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;

	@RequestMapping(value = "/listatipodocumentos.htm")
	public ModelAndView handleRequest(@RequestParam("id") Long id) throws ServletException, IOException {

		//System.out.println(id);
		List<TipoDocumento> lstFiles = this.tipoDocumentoManager.getTiposDocumentosById(id);

		return new ModelAndView("listatipodocumentos", "tipos", lstFiles);
	}

}
