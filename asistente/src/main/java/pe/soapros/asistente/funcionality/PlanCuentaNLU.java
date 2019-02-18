package pe.soapros.asistente.funcionality;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.google.common.io.Files;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.util.Util;

public class PlanCuentaNLU {

	private NaturalLanguageUnderstanding service;
	
	private Propiedades propiedades;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public PlanCuentaNLU() {
		
	}
	
	
	
	public Propiedades getPropiedades() {
		return propiedades;
	}



	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}



	public PlanCuenta consultarCuentas(String archivo, boolean swRegex) throws IOException {
		
//		logger.debug("Servicio NLU: " + propiedades.getPlanCuentasPassNLU() + " - " + propiedades.getPlanCuentasPassNLU());
//		logger.debug("ModeloID: " + propiedades.getPlanCuentasModeloNLU());
		
		IamOptions options = new IamOptions.Builder()
			    .apiKey("DacsRs2F_OZycdP3amtIog0AWmfJN4w0ciotSOskNbCg")
			    .build();
		
		/*service = new NaturalLanguageUnderstanding(
				  "2018-03-16",
				  //propiedades.getPlanCuentasUserNLU(),//"d8e61c23-01bc-4ed7-b13e-855431a4ceaa",
				  //propiedades.getPlanCuentasPassNLU()//"OEwEY17bgaaT"//"Wtq0dtw3cXLG"
				  "d8e61c23-01bc-4ed7-b13e-855431a4ceaa",
				  "OEwEY17bgaaT"
				);*/
		
		service = new NaturalLanguageUnderstanding("2018-11-16", options);
		service.setEndPoint("https://gateway-fra.watsonplatform.net/natural-language-understanding/api");
		
		PlanCuenta plancuenta =  new PlanCuenta();
		
		//String texto = Util.leerArchivoTXT(archivo);
		
		EntitiesOptions entities= new EntitiesOptions.Builder()
				  //.model(this.propiedades.getPlanCuentasModeloNLU()) //"10:8870c7a0-0c79-4923-9d83-61eead0c949f")
				  .model("9b7d1aec-6113-4aba-b4ba-10a81425e52a")
				  .build();

		Features features = new Features.Builder()
				  .entities(entities)
				  .build();
		
		AnalyzeOptions parameters = new AnalyzeOptions.Builder()
				  .text(archivo)
				  .features(features)
				  .build();
		
		AnalysisResults response = service
				  .analyze(parameters)
				  .execute();
		//		System.out.println(response);
		
		if(!swRegex)
			Util.leerPlanCuentas(response.toString(), plancuenta);
		else
			Util.leerPlanCuentasRegex(response.toString(), plancuenta, archivo);
		
		return plancuenta;
	}
	
	
	public static void main(String[] args) throws IOException {
		PlanCuentaNLU planNLU = new PlanCuentaNLU();
		PlanCuenta valor = planNLU.consultarCuentas("C:\\Users\\User\\Desktop\\EE.FF SELECCION\\5105451-2017-9 (1)_cover_1.txt", false);
//		
//		
		System.out.println(valor.toString());
		
//		String cadena = Util.leerArchivoTXT("C:\\Users\\Furth\\Desktop\\listado.txt");
//		String[] arch = cadena.split(".txt");
//		
//		List<Path> archivos = Util.listarFicheros("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\Lista 5", "tif");
//		
//		List<File> enc = new ArrayList<File>();
//		
//		for (String ar: arch) {
//			
//			for(Path p: archivos) {
//				File f = new File(p.toString());
//				
//				if(f.getName().equals(ar + ".tif")) {
//					enc.add(f);
//					break;
//				}
//			}
//			
//		}
//		
//		System.out.println(enc);
//		File tmp;
//		for(File f: enc) {
//			try {
//				tmp = new File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\seleccionado\\lista3\\" + f.getName());
//				Files.move(f, tmp);
//			}catch(Exception e) {
//				
//			}
//		}
//		
//		System.out.println(enc);

	}

}
