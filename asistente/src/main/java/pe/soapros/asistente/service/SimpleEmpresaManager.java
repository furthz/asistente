package pe.soapros.asistente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.soapros.asistente.domain.Empresa;
import pe.soapros.asistente.repository.EmpresaDao;

@Component
public class SimpleEmpresaManager implements EmpresaManager{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmpresaDao empresaDao;
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public List<Empresa> getEmpresas() {
		List<Empresa> lst = empresaDao.getEmpresaList();
		return lst;
	}

	public void crearEmpresa(Empresa emp) {
		this.empresaDao.saveEmpresa(emp);
		
	}

	public Empresa buscarById(long id) {		
		return this.empresaDao.buscarById(id);
	}

	public List<Empresa> buscarByName(String texto) {
		return this.empresaDao.buscarByName(texto);
	}

}
