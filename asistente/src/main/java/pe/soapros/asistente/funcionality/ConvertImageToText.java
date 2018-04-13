package pe.soapros.asistente.funcionality;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.util.BatRunner;
import pe.soapros.asistente.util.Desempaquetar;
import pe.soapros.asistente.util.Util;

/***
 * Clase que encapsula las funcionalidades para convertir una imagen en texto
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class ConvertImageToText {

	public static void main(String[] args) throws IOException, Exception {

		detectDocumentText("D:\\archivos1\\contr63442652");
	}

	public static void detectDocumentText(String pathFile) throws Exception, IOException {

		List<Path> archivos = Util.listarFicheros(pathFile, "png");

		String filePathMod = "";

		// filePathMod = "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\scripts\\convert-contr92672159.0.png";

		for (Path p : archivos) {
			
			filePathMod = p.toString();

			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

			ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePathMod));

			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// String contenido = new String();
			Documento dcto = new Documento();

			List<Palabra> bloques = new ArrayList<Palabra>();
			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();
				client.close();

				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						throw new Exception("Error");
					}

					// For full list of available annotations, see http://g.co/cloud/vision/docs
					TextAnnotation annotation = res.getFullTextAnnotation();
					for (Page page : annotation.getPagesList()) {
						String pageText = "";
						for (Block block : page.getBlocksList()) {

							Palabra blc = new Palabra();
							blc.addPuntos(block.getBoundingBox().getVertices(0).getX(),
									block.getBoundingBox().getVertices(0).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(1).getX(),
									block.getBoundingBox().getVertices(1).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(2).getX(),
									block.getBoundingBox().getVertices(2).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(3).getX(),
									block.getBoundingBox().getVertices(3).getY());

							bloques.add(blc);

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
									palabra.addPuntos(word.getBoundingBox().getVertices(0).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(1).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(2).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(3).getX(),
											word.getBoundingBox().getVertices(0).getY());

									dcto.addPalabra(palabra);
								}

								blockText = blockText + paraText;
							}
							pageText = pageText + blockText;
						}
					}

				}
				// }

				File input = new File(filePathMod);
				BufferedImage image = ImageIO.read(input);

				if (image.getHeight() > 1000) {
					dcto.setSwHorizontal(false);
				} else {
					dcto.setSwHorizontal(true);
				}

				dcto.setBloques(bloques);

				File f = new File(filePathMod);
				String nombre = f.getName();
				nombre = nombre.substring(0, nombre.length() - 3);
				
				dcto.formarResultante(pathFile, nombre + "txt");

			}

		}

	}

	/**
	 * Método que convierte una imagen subida a un archivo de texto
	 * 
	 * @param file
	 *            Archivo subido por la página
	 * @param pathFile
	 *            Ruta destino donde se guardan los archivos y los textos
	 *            convertidos
	 * @throws Exception
	 * @throws IOException
	 */
	public void detectDocumentText(MultipartFile file, String pathFile) throws Exception, IOException {

		// enviar los archivos a una ruta temporal
		file.transferTo(new File(pathFile + File.separator + file.getOriginalFilename()));

		//lista de archivos desempaquetados
		List<String> lstArchivos = Desempaquetar.doit(pathFile, file.getOriginalFilename());

		String filePathMod = "";

		//lista de archivo de texto obtenidos
		List<String> lstArchivosTXT = new ArrayList<String>();
		
		for (String filePath : lstArchivos) {

			// llamar al script para rotar la imagen

			filePathMod = filePath.substring(0, filePath.length() - 3) + "png";

			BatRunner.runProcess(filePath, filePathMod);

			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

			ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePathMod));

			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// String contenido = new String();
			Documento dcto = new Documento();

			List<Palabra> bloques = new ArrayList<Palabra>();
			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();
				client.close();

				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						throw new Exception("Error");
					}

					// For full list of available annotations, see http://g.co/cloud/vision/docs
					TextAnnotation annotation = res.getFullTextAnnotation();
					for (Page page : annotation.getPagesList()) {
						String pageText = "";
						for (Block block : page.getBlocksList()) {
							String blockText = "";

							Palabra blc = new Palabra();
							blc.addPuntos(block.getBoundingBox().getVertices(0).getX(),
									block.getBoundingBox().getVertices(0).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(1).getX(),
									block.getBoundingBox().getVertices(1).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(2).getX(),
									block.getBoundingBox().getVertices(2).getY());
							blc.addPuntos(block.getBoundingBox().getVertices(3).getX(),
									block.getBoundingBox().getVertices(3).getY());

							bloques.add(blc);

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
									palabra.addPuntos(word.getBoundingBox().getVertices(0).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(1).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(2).getX(),
											word.getBoundingBox().getVertices(0).getY());
									palabra.addPuntos(word.getBoundingBox().getVertices(3).getX(),
											word.getBoundingBox().getVertices(0).getY());

									dcto.addPalabra(palabra);
								}

								blockText = blockText + paraText;
							}
							pageText = pageText + blockText;
						}
					}

				}
			}

			
			dcto.setBloques(bloques);
			File f = new File(filePathMod);
			String nombre = f.getName();
			nombre = nombre.substring(0, nombre.length() - 3);
			
			BufferedImage image = ImageIO.read(f);

			if (image.getHeight() > 1000) {
				dcto.setSwHorizontal(false);
			} else {
				dcto.setSwHorizontal(true);
			}
			
			dcto.formarResultante(pathFile, nombre + "txt");

			lstArchivosTXT.add(pathFile + File.separator +  nombre + "txt");
		}
		
		//verificar todos los archivos TXT resueltos, para ver si son balances u otros tipos
		
		TipoDocumentoNLU tipoNLU = new TipoDocumentoNLU();
		ServiceECM serviceECM = new ServiceECM();
		
		for(String arch: lstArchivosTXT) {
			TipoDocumento tipodoc = tipoNLU.consultarTipoDcto(arch);
			File f = new File(arch);
			String nombre = f.getName();
			nombre = nombre.substring(0, nombre.length() - 3);
			
			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("estado_financiero:empresa", tipodoc.getEmpresa());
			metadata.put("estado_financiero:fecha", tipodoc.getFecha());
			metadata.put("estado_financiero:id", tipodoc.getId());
			metadata.put("estado_financiero:tipodoc", tipodoc.getTipoDoc());
			metadata.put("estado_financiero:unidad", tipodoc.getUnidad());
			
			String objectID = serviceECM.uploadDocument(pathFile + File.separator + nombre + "png", metadata);
			tipodoc.setObjectId(objectID);
		}

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
