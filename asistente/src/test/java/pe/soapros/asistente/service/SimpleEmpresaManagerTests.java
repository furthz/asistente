package pe.soapros.asistente.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pe.soapros.asistente.domain.Empresa;

public class SimpleEmpresaManagerTests {
	
	
	private SimpleEmpresaManager empresaManager;
	
	private static int EMPRESA_COUNT = 1;

	@Before
    public void setUp() throws Exception {
		empresaManager = new SimpleEmpresaManager();
	}
	
	@Test
    public void testGetEmpresas() {
        List<Empresa> empresas = empresaManager.getEmpresas();
        assertNotNull(empresas);        
        assertEquals(EMPRESA_COUNT, empresaManager.getEmpresas().size());    
        
    }
	
}
