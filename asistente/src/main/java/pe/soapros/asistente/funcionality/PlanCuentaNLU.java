package pe.soapros.asistente.funcionality;

import java.io.IOException;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.util.Util;

public class PlanCuentaNLU {

	private NaturalLanguageUnderstanding service;
	
	public PlanCuentaNLU() {
		service = new NaturalLanguageUnderstanding(
				  "2018-03-16",
				  "69f6c95d-797d-45b7-87a9-13bbf1eb49b0",
				  "Wtq0dtw3cXLG"
				);
	}
	
	
	public PlanCuenta consultarCuentas(String archivo) throws IOException {
		
		PlanCuenta plancuenta =  new PlanCuenta();
		
		//String texto = Util.leerArchivoTXT(archivo);
		
		EntitiesOptions entities= new EntitiesOptions.Builder()
				  .model("10:91a8915d-9855-439e-b71b-61800fc6f1d5")				  
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
		
		Util.leerPlanCuentas(response.toString(), plancuenta);
		
		return plancuenta;
	}
	
	
	public static void main(String[] args) throws IOException {
		PlanCuentaNLU planNLU = new PlanCuentaNLU();
		PlanCuenta valor = planNLU.consultarCuentas("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\Node\\convert-contr63062692.0.txt");
		
		
		System.out.println(valor.toString());
//		String cadena = "DICIEMBRE        31   DE   2014";
//		String[] valores = cadena.split("\\W+");
//		
//		for(String val: valores) {
//			System.out.println(val);
//		}
	}

}
