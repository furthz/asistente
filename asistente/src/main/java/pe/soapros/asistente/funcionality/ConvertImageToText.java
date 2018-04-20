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
import java.util.function.BiConsumer;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.google.protobuf.Descriptors.FieldDescriptor;

import pe.soapros.asistente.domain.Documento;
import pe.soapros.asistente.domain.Palabra;
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
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

	protected final Log logger = LogFactory.getLog(getClass());
	
	private Propiedades propiedades;

	public static void main(String[] args) throws IOException, Exception {

		detectDocumentText("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2");
	}

	public static void detectDocumentText(String pathFile) throws Exception, IOException {

		List<Path> archivos = Util.listarFicheros(pathFile, "png");

		String filePathMod = "";


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
			try {
				ImageAnnotatorClient client = ImageAnnotatorClient.create();
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

				
				dcto.formarResultante(input.getParent(), nombre + "txt", true);

			}catch (Exception e) {
			    e.printStackTrace();
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
	public List<TipoDocumento> detectDocumentText(MultipartFile file, String pathFile) throws Exception, IOException {

		logger.debug("detectDocumentText");

		// enviar los archivos a una ruta temporal
		file.transferTo(new File(pathFile + File.separator + file.getOriginalFilename()));
		logger.debug("Se envió los archivos a la carpeta temporal: " + pathFile + File.separator
				+ file.getOriginalFilename());

		Desempaquetar desem = new Desempaquetar();
		// lista de archivos desempaquetados
		List<String> lstArchivos = desem.doit(pathFile, file.getOriginalFilename());
		logger.debug("Archivos desempaquetados");
		logger.debug("Cant archivos: " + lstArchivos.size());

		String filePathMod = "";
		// lista de archivo de texto obtenidos
		List<String> lstArchivosTXT = new ArrayList<String>();
		
		BatRunner batRunner = new BatRunner();
		
		batRunner.setPropiedades(this.propiedades);

		logger.debug("Recorrer los archivos desempaquetados");
		for (String filePath : lstArchivos) {
			logger.debug("Archivo: " + filePath);

			filePathMod = filePath.substring(0, filePath.length() - 3) + "png";
			logger.debug("Archivo a modificar: " + filePathMod);

			batRunner.runProcess(filePath, filePathMod);
			logger.debug("Ejecutar el archivo Python");

			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

			ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePathMod));

			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			logger.debug("Se llamó l servicio de google vision");

			// String contenido = new String();
			Documento dcto = new Documento();
			dcto.setPropiedades(this.propiedades);

			List<Palabra> bloques = new ArrayList<Palabra>();
			try {
				ImageAnnotatorClient client = ImageAnnotatorClient.create();
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
			}catch (Exception e) {
			    logger.error(e);
			}

			logger.debug("Se obtuvo la información liquida");

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

			dcto.formarResultante(pathFile, nombre + "txt", false);
			logger.debug("Se formo el archivo txt: " + pathFile + nombre + "txt");

			lstArchivosTXT.add(pathFile + File.separator + nombre + "txt");
		}

		// verificar todos los archivos TXT resueltos, para ver si son balances u otros
		// tipos

		TipoDocumentoNLU tipoNLU = new TipoDocumentoNLU();
		tipoNLU.setPropiedades(this.propiedades);
		logger.debug("Se creó TipoNLU: " + tipoNLU.toString());
		
		ServiceECM serviceECM = new ServiceECM();
		serviceECM.setPropiedades(this.propiedades);
		logger.debug("Se creo ServiceECM: " + serviceECM.toString());

		List<TipoDocumento> lstTipos = new ArrayList<TipoDocumento>();
		String empresa = "";

		logger.debug("Se inicia la extraccion de los datos encabezados");
		for (String arch : lstArchivosTXT) {
			TipoDocumento tipodoc = tipoNLU.consultarTipoDcto(arch);

			File f = new File(arch);
			String nombre = f.getName();
			nombre = nombre.substring(0, nombre.length() - 3);
			tipodoc.setFilename(nombre + "png");
			tipodoc.setFilenameTxt(nombre + "txt");
			lstTipos.add(tipodoc);

			if (tipodoc.getEmpresa() != null && !tipodoc.getEmpresa().equals("")) {
				empresa = tipodoc.getEmpresa();
			}
			logger.debug("Datos Extraídos: " + tipodoc.toString());

			// Map<String, Object> metadata = new HashMap<String, Object>();
			// metadata.put("estado_financiero:empresa", tipodoc.getEmpresa());
			// metadata.put("estado_financiero:fecha", tipodoc.getFecha());
			// metadata.put("estado_financiero:id", tipodoc.getId());
			// metadata.put("estado_financiero:tipodoc", tipodoc.getTipoDoc());
			// metadata.put("estado_financiero:unidad", tipodoc.getUnidad());
			//
			// String objectID = serviceECM.uploadDocument(pathFile + File.separator +
			// nombre + "png", metadata);
			// tipodoc.setObjectId(objectID);
		}

		logger.debug("Enviar los archivos al ECM");

		for (TipoDocumento tipodoc : lstTipos) {

			tipodoc.setEmpresa(empresa);

			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("estado_financiero:empresa", tipodoc.getEmpresa());
			metadata.put("estado_financiero:fecha", tipodoc.getFecha());
			metadata.put("estado_financiero:id", tipodoc.getIdEmpresa());
			metadata.put("estado_financiero:tipodoc", tipodoc.getTipoDoc());
			metadata.put("estado_financiero:unidad", tipodoc.getUnidad());

			String objectID = serviceECM.uploadDocument(pathFile + File.separator + tipodoc.getFilename(), metadata);
			logger.debug("Se subió el archivo: " + pathFile + tipodoc.getFilename());
			logger.debug("Archivo ID: " + objectID);
			tipodoc.setObjectId(objectID);

			String objectIDTxt = serviceECM.uploadDocument(pathFile + File.separator + tipodoc.getFilenameTxt(), metadata);
			logger.debug("Se subió el archivo: " + pathFile + tipodoc.getFilenameTxt());
			logger.debug("Archivo ID: " + objectIDTxt);
			tipodoc.setObjectIdTxt(objectIDTxt);

		}

		return lstTipos;

	}

	
	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}

//	public static void convert(byte[] imagen) throws Exception {
//
//		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
//
//			ByteString imgBytes = ByteString.copyFrom(imagen);
//
//			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();
//
//			Image img = Image.newBuilder().setContent(imgBytes).build();
//
//			Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
//			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//			requests.add(request);
//
//			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
//			List<AnnotateImageResponse> responses = response.getResponsesList();
//
//			for (AnnotateImageResponse res : responses) {
//				if (res.hasError()) {
//					System.out.printf("Error: %s\n", res.getError().getMessage());
//					return;
//				}
//
//				for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
//					annotation.getAllFields().forEach(new BiConsumer<FieldDescriptor, Object>() {
//						public void accept(FieldDescriptor k, Object v) {
//							System.out.printf("%s : %s\n", k, v.toString());
//						}
//					});
//				}
//
//			}
//
//		}
//	}
}
