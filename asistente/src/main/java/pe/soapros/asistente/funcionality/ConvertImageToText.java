package pe.soapros.asistente.funcionality;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.protobuf.ByteString;

import pe.soapros.asistente.domain.Documento;
import pe.soapros.asistente.domain.Palabra;

public class ConvertImageToText {
	
	public static void main(String[] args) throws IOException, Exception {
		
		String pathfile = "c:/Temp/";
		
		PrintStream out = new PrintStream(pathfile + "respuesta1.txt");
		
		detectDocumentText(out);
	}
	
	public static void detectDocumentText(PrintStream out) throws Exception, IOException {
		
		String pathfile = "c:/Temp/";

		String filePath = pathfile + "contr70272219.png";

		List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		//String contenido = new String();

		Documento dcto = new Documento();
		
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			client.close();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					out.printf("Error: %s\n", res.getError().getMessage());
					throw new Exception("Error");
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				TextAnnotation annotation = res.getFullTextAnnotation();
				for (Page page : annotation.getPagesList()) {
					String pageText = "";
					for (Block block : page.getBlocksList()) {
						String blockText = "";
						for (Paragraph para : block.getParagraphsList()) {
							String paraText = "";
							
							Palabra palabra;
							for (Word word : para.getWordsList()) {
								String wordText = "";
								for (Symbol symbol : word.getSymbolsList()) {
									wordText = wordText + symbol.getText();
								}
								paraText = paraText + wordText;
								palabra = new Palabra();
								
								palabra.setValor(wordText);
								palabra.addPuntos(word.getBoundingBox().getVertices(0).getX(), word.getBoundingBox().getVertices(0).getY());
								palabra.addPuntos(word.getBoundingBox().getVertices(1).getX(), word.getBoundingBox().getVertices(0).getY());
								palabra.addPuntos(word.getBoundingBox().getVertices(2).getX(), word.getBoundingBox().getVertices(0).getY());
								palabra.addPuntos(word.getBoundingBox().getVertices(3).getX(), word.getBoundingBox().getVertices(0).getY());
								
								dcto.addPalabra(palabra);
								
								out.println("palabra: \n" + wordText);
								out.println("limites: \n" + word.getBoundingBox().getVertices(0)+ "\n");
							}
							// Output Example using Paragraph:
							//out.println("Paragraph: \n" + paraText);
							//out.println("Bounds: \n" + para.getBoundingBox() + "\n");
							blockText = blockText + paraText;
						}
						//pageText = pageText + blockText;
					}
				}
				//out.println(annotation.getText());
				//contenido = annotation.getText();
			}
		}
		//dcto.ordernarDocumento();
		dcto.formarResultante();
		System.out.print("prueba");
	}

	public byte[] detectDocumentText(MultipartFile file, PrintStream out) throws Exception, IOException {

		String pathfile = "c:/Temp/";

		file.transferTo(new File(pathfile + file.getOriginalFilename()));

		String filePath = pathfile + file.getOriginalFilename();

		List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		String contenido = new String();

		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			client.close();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					out.printf("Error: %s\n", res.getError().getMessage());
					throw new Exception("Error");
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				TextAnnotation annotation = res.getFullTextAnnotation();
				for (Page page : annotation.getPagesList()) {
					String pageText = "";
					for (Block block : page.getBlocksList()) {
						String blockText = "";
						for (Paragraph para : block.getParagraphsList()) {
							String paraText = "";
							for (Word word : para.getWordsList()) {
								String wordText = "";
								for (Symbol symbol : word.getSymbolsList()) {
									wordText = wordText + symbol.getText();
								}
								paraText = paraText + wordText;
							}
							// Output Example using Paragraph:
							// out.println("Paragraph: \n" + paraText);
							// out.println("Bounds: \n" + para.getBoundingBox() + "\n");
							blockText = blockText + paraText;
						}
						pageText = pageText + blockText;
					}
				}
				out.println(annotation.getText());
				contenido = annotation.getText();
			}
		}
		
		return contenido.getBytes();
	}

	public static void convert(byte[] imagen) throws Exception {

		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

			ByteString imgBytes = ByteString.copyFrom(imagen);

			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

			Image img = Image.newBuilder().setContent(imgBytes).build();

			Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.printf("Error: %s\n", res.getError().getMessage());
					return;
				}

				for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
					annotation.getAllFields().forEach((k, v) -> System.out.printf("%s : %s\n", k, v.toString()));
				}

			}

		}
	}
}
