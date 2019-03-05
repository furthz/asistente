package pe.soapros.asistente.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.service.TipoDocumentoManager;

@Controller
public class UpdateTipoDocumentoController {

	@Autowired
	private TipoDocumentoManager tipoManager;
	
	@RequestMapping(value = "/updateTipoDocumentoMA.htm", method = RequestMethod.POST)
	public RedirectView handelUpdateTipoDocumentoMA(@ModelAttribute("tipoDocumento") TipoDocumento tipoDocumento, Model model) {
		
		TipoDocumento tipo = tipoManager.getId(tipoDocumento.getId());
		
		tipo.setTipoDoc(tipoDocumento.getTipoDoc());
		
		tipoManager.crearTipoDocumento(tipo);
		
		//String rpta = "listatipodocumentos.htm?id=" + tipoDocumento.getId();
		
		 RedirectView rv = new RedirectView();
		 rv.setContextRelative(true);
		 rv.setUrl("listatipodocumentos.htm?id=" + tipo.getArchivo().getId());
		
		//return modelAndView;
		    
		return rv;
		
	}
}
