package pe.soapros.asistente.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;

import pe.soapros.asistente.domain.Empresa;
import pe.soapros.asistente.service.EmpresaManager;

@Controller
public class UpdateEmpresaController {

	@Autowired
	private EmpresaManager empresaManager;
	
	public RedirectView handelUpdateEmpresaMA(@ModelAttribute("empresa") Empresa empresa, Model model) {

		Empresa em = this.empresaManager.buscarById(empresa.getId());
		
		em.setNombre(empresa.getNombre());
		
		this.empresaManager.crearEmpresa(em);
		
		
		RedirectView rv = new RedirectView();
		rv.setContextRelative(true);
		rv.setUrl("listaempresas.htm");

		return rv;
	}
}
