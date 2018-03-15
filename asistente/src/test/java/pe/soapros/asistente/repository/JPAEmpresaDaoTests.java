package pe.soapros.asistente.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.soapros.asistente.domain.Empresa;

public class JPAEmpresaDaoTests {

	private ApplicationContext context;
	private EmpresaDao empresaDao;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:test-context.xml");
		empresaDao = (EmpresaDao) context.getBean("empresaDao");
	}
	
	@Test
	public void testSaveProduct() {
		Empresa emp1 = new Empresa();
		emp1.setNombre("SOA");
		//emp1.setId(1);
		empresaDao.saveEmpresa(emp1);
	}
	
	@Test
	public void testListEmpresas() {
		List<Empresa> lstEmpresas = this.empresaDao.getEmpresaList();
		for(Empresa e : lstEmpresas) {
			System.out.println(e.getNombre());
		}
		assertEquals(lstEmpresas.size(), 1, 0);	
	}
}
