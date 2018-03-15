package pe.soapros.asistente.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.soapros.asistente.domain.Empresa;
import pe.soapros.asistente.service.EmpresaManager;

@Controller
@RequestMapping(value="/crearempresa.htm")
public class CrearEmpreaController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private EmpresaManager empresaManager;
	
	
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
	
	public EmpresaManager getEmpresaManager() {
		return this.empresaManager;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@Valid Empresa empresa, BindingResult result)
    {
        if (result.hasErrors()) {
            return "crearempresa";
        }
		
        String nombre = empresa.getNombre();       
        
        logger.info("Empresa a crear " + nombre + "%.");

        empresaManager.crearEmpresa(empresa);
        //productManager.increasePrice(increase);

        return "redirect:/listaempresas.htm";
    }
	
	@RequestMapping(method = RequestMethod.GET)
    protected Empresa formBackingObject(HttpServletRequest request) throws ServletException {
        Empresa empresa = new Empresa();        
        return empresa;
    }

}
