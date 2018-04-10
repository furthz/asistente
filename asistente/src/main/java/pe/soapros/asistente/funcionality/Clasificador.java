package pe.soapros.asistente.funcionality;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;

public class Clasificador {

	public static void main(String[] args) throws FileNotFoundException {
		// crearClasificador();

		// File f = new File(
		// "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\scripts\\convert-contr92672159.0.png");
//		clasificarEEFF();
		updateClasificador();
	}

	private static void crearClasificador() throws FileNotFoundException {

		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("67355c3439771662c5da029ee2a00b2cac5dbea9");

		CreateClassifierOptions createClassifierOptions = new CreateClassifierOptions.Builder().name("eeff")
				.addClass("balance", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Balances.zip"))
				.addClass("estado", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Estados.zip"))
				.addClass("nota", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Notas.zip"))
				.negativeExamples(new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Otros.zip"))
				.build();

		Classifier eeff = service.createClassifier(createClassifierOptions).execute();
		System.out.println(eeff);

	}

	private static void clasificarEEFF() throws FileNotFoundException {

		VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);

		service.setApiKey("67355c3439771662c5da029ee2a00b2cac5dbea9");

		InputStream imagesStream = new FileInputStream(
				"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\scripts\\convert-contr55031814.4.png");
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder().imagesFile(imagesStream)
				.imagesFilename("convert-contr55031814.4.png")
				.parameters("{\"classifier_ids\": [\"eeff_436552270\"] ,\"threshold\": 0.5}").build();


		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);


	}
	
	private static void updateClasificador() throws FileNotFoundException {
		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("67355c3439771662c5da029ee2a00b2cac5dbea9");
		String classifierId = "eeff_436552270";

		InputStream negativeExamples = new FileInputStream("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Otros1.zip");
		UpdateClassifierOptions options = new UpdateClassifierOptions.Builder()
		  .classifierId(classifierId)
		  //.addClass("balance", new File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Balances3.zip"))
		  //.addClass("estado", new File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Estados1.zip"))
		  .addClass("nota", new File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Notas3.zip"))
		  .negativeExamples(negativeExamples)
		  .negativeExamplesFilename("Otros1.zip")
		  .build();

		Classifier eeff = service.updateClassifier(options).execute();
		System.out.println(eeff);
	}
}
