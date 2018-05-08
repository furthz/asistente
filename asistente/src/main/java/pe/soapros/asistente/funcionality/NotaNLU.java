package pe.soapros.asistente.funcionality;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.util.Util;

public class NotaNLU {

	private NaturalLanguageUnderstanding service;
	
	public NotaNLU() {
		
	}
	
	public PlanCuenta consultarNotas(String archivo) {
		
		service = new NaturalLanguageUnderstanding(
				  "2018-03-16",
				  "e97abde1-61ae-4ab7-b2e4-365d52c4c696",
				  "nejsvkrNR7mT"
				);
		
		PlanCuenta plancuenta =  new PlanCuenta();
		
		EntitiesOptions entities= new EntitiesOptions.Builder()
				  .model("10:a940188d-3ad8-4855-9754-97960aa0e60d") //"10:8870c7a0-0c79-4923-9d83-61eead0c949f")				  
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

		Util.leerPlanCuentas(response.toString(), plancuenta);
		
		return plancuenta;
		
	}
}
