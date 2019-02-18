package pe.soapros.asistente.funcionality;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;

public class Clasificador {

	VisualRecognition service;
	
	public Clasificador() {
		
		service = new VisualRecognition(null); //VisualRecognition.VERSION_DATE_2016_05_20);

		service.setApiKey("f8519a2f81736b12456b9f0d1f221e7dff4f8a11");

	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// crearClasificador();

		// File f = new File(
		// "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\scripts\\convert-contr92672159.0.png");
		 clasificarEEFF();
		//updateClasificador();
		//crearClasificador();
	}

	@SuppressWarnings("unused")
	private static void crearClasificador() throws FileNotFoundException {

		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("f8519a2f81736b12456b9f0d1f221e7dff4f8a11");

		CreateClassifierOptions createClassifierOptions = new CreateClassifierOptions.Builder().name("eeff")
				.addClass("estados", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Estados.zip"))
				.addClass("notas", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Notas.zip"))
				.negativeExamples(new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Otros1.zip"))
				.build();

		Classifier eeff = service.createClassifier(createClassifierOptions).execute();
		System.out.println(eeff);

	}

	@SuppressWarnings("unused")
	private static void clasificarEEFF() throws FileNotFoundException {

		/*
		VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);

		service.setApiKey("f8519a2f81736b12456b9f0d1f221e7dff4f8a11");

		InputStream imagesStream = new FileInputStream("D:\\archivos1\\Fase5\\Destino\\convert-contr69763804.0.png");
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder().imagesFile(imagesStream)
				.imagesFilename("convert-contr69763804.0.png")
				.parameters("{\"classifier_ids\": [\"eeff_1483460197\"] ,\"threshold\": 0.5}").build();

		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);*/

	}

	@SuppressWarnings("unused")
	private static void updateClasificador() throws FileNotFoundException {
		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("f8519a2f81736b12456b9f0d1f221e7dff4f8a11");
		String classifierId = "eeff_1483460197";
		
		UpdateClassifierOptions options = new UpdateClassifierOptions.Builder().classifierId(classifierId)
				// .addClass("balance", new
				// File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente
				// Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Balances3.zip"))
				// .addClass("estado", new
				// File("D:\\Documents\\Proyectos\\Bancolombia\\Asistente
				// Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Estados1.zip"))
				.addClass("notas", new File(
						"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\soa pro\\Entrenamiento\\Notas1.zip")).build();
				//.negativeExamples(negativeExamples).negativeExamplesFilename("Otros1.zip").build();

		Classifier eeff = service.updateClassifier(options).execute();
		System.out.println(eeff);
	}
}
