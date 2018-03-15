package pe.soapros.asistente.repository;

import java.util.List;

import pe.soapros.asistente.domain.Empresa;

public interface EmpresaDao {

	public List<Empresa> getEmpresaList();
	
	public void saveEmpresa(Empresa emp);
	
	public Empresa buscarById(long id);
}
