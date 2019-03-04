package pe.soapros.asistente.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.service.TipoDocumentoManager;

@Controller
public class UpdateTipoDocumentoController {

	@Autowired
	private TipoDocumentoManager tipoManager;
	
	@RequestMapping(value = "/updateTipoDocumentoMA.htm", method = RequestMethod.POST)
	public ModelAndView handelUpdateTipoDocumentoMA(@ModelAttribute("tipoDocumento") TipoDocumento tipoDocumento, Model model) {
		
		
		tipoManager.crearTipoDocumento(tipoDocumento);
		
		//String rpta = "listatipodocumentos.htm?id=" + tipoDocumento.getId();
		
		 ModelAndView modelAndView = new ModelAndView("listatipodocumentos");
		    modelAndView.addObject("id", tipoDocumento.getId());
		
		return modelAndView;
		
	}
}
