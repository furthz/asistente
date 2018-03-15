package pe.soapros.asistente.service;

import java.io.Serializable;
import java.util.List;

import pe.soapros.asistente.domain.Empresa;

public interface EmpresaManager extends Serializable{
	
	public List<Empresa> getEmpresas();
	
	public void crearEmpresa(Empresa emp);
	
	public Empresa buscarById(long id);
}
