package pe.soapros.asistente.funcionality;

import java.io.IOException;


import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.util.Util;

public class TipoDocumentoNLU {

	private NaturalLanguageUnderstanding service;
	
	private Propiedades propiedades;
	
	public TipoDocumentoNLU() {
		
		
	}
	
	
	public Propiedades getPropiedades() {
		return propiedades;
	}


	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}


	/**
	 * Método que permite identificar los tipos de documento
	 * @param archivo ruta del archivo TXT extraído
	 * @return TipoDocumento
	 * @throws IOException
	 */
	public TipoDocumento consultarTipoDcto(String archivo) throws IOException {
		 
		service = new NaturalLanguageUnderstanding(
				  "2018-03-16",
				  this.propiedades.getTipodocUserNLU(),//"3560f68d-2272-4ddc-b44f-574b7df520da",
				  this.propiedades.getTipodocPassNLU()//"e2jwg0JLjAxE"
				);
		
		String texto = Util.leerArchivoTXT(archivo);
		
//		System.out.println(texto);
		
		EntitiesOptions entities= new EntitiesOptions.Builder()
				  .model(this.propiedades.getTipodocModeloNLU())//"10:ef6e94f2-cfbf-4482-bc40-677e9e61680b")				  
				  .build();

		Features features = new Features.Builder()
				  .entities(entities)
				  .build();
		
		AnalyzeOptions parameters = new AnalyzeOptions.Builder()
				  .text(texto)
				  .features(features)
				  .build();
		
		AnalysisResults response = service
				  .analyze(parameters)
				  .execute();
				//System.out.println(response);
		
		return Util.getTipoDcto(response.toString());
		
	}
	
	public static void main(String[] args) throws IOException {
		TipoDocumentoNLU dctoNLU = new TipoDocumentoNLU();
		TipoDocumento valor = dctoNLU.consultarTipoDcto("C:\\Users\\User\\Desktop\\EE.FF SELECCION\\5105451-2017-9 (1)_cover_1.txt");
		System.out.println(valor.toString());
//		String cadena = "DICIEMBRE        31   DE   2014";
//		String[] valores = cadena.split("\\W+");
//		
//		for(String val: valores) {
//			System.out.println(val);
//		}
	}
	
}
