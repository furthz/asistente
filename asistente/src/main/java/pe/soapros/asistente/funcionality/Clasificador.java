package pe.soapros.asistente.funcionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;

public class Clasificador {

	private void crearClasificador() throws FileNotFoundException {

		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("67355c3439771662c5da029ee2a00b2cac5dbea9");

		CreateClassifierOptions createClassifierOptions = new CreateClassifierOptions.Builder().name("eeff")
				.addClass("balance", new File("./balance.zip")).addClass("estado", new File("./estado.zip"))
				.addClass("nota", new File("./nota.zip")).negativeExamples(new File("./otro.zip")).build();

		Classifier eeff = service.createClassifier(createClassifierOptions).execute();
		System.out.println(eeff);

	}

	private void clasificarEEFF(File archivo) throws FileNotFoundException {

		VisualRecognition service = new VisualRecognition("2016-05-20");
		service.setApiKey("67355c3439771662c5da029ee2a00b2cac5dbea9");

		List<String> ids = new ArrayList<String>();
		ids.add("");
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder().imagesFile(archivo).classifierIds(ids)
				.imagesFilename(archivo.getName()).threshold((float) 0.6).owners(Arrays.asList("me")).build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);

	}
}
