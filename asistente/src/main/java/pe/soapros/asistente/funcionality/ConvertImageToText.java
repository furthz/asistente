package pe.soapros.asistente.funcionality;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
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
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.Punto;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.util.Desempaquetar;
import pe.soapros.asistente.util.Util;

/***
 * Clase que encapsula las funcionalidades para convertir una imagen en texto
 * 
 * @author Raúl Talledo
 * @version 2.0
 *
 */
public class ConvertImageToText {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private Propiedades propiedades;
	
	private List<Punto> bloques1;
	
	private List<Punto> bloques2;
	
	public ConvertImageToText() {
		bloques1 = new ArrayList<Punto>();
		bloques2 = new ArrayList<Punto>();
	}

	public static void main(String[] args) throws IOException, Exception {
		
		ConvertImageToText itext = new ConvertImageToText();

		//System.out.println(args[0]);
		itext.detectDocumentText("C:\\Users\\User\\Desktop\\Fase2");	
		
		
	}

	public void detectDocumentText(String pathFile) throws Exception, IOException {

		List<Path> archivos = Util.listarFicheros(pathFile, "jpg");

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

//			List<Palabra> bloques = new ArrayList<Palabra>();
			logger.debug("Inicio del llamado a Google Vision");
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
					logger.debug("se recupera todas las paginas");
					
					for (Page page : annotation.getPagesList()) {
						logger.debug("#PAG: " + page.getBlocksList());
						
						String pageText = "";
						for (Block block : page.getBlocksList()) {
							//agrgar los puntos superiores de los bloques
							Punto pt1 = new Punto();
							pt1.setX(block.getBoundingBox().getVertices(0).getX());
							pt1.setY(block.getBoundingBox().getVertices(0).getY());
							bloques1.add(pt1);
							
							Punto pt2 = new Punto();
							pt2.setX(block.getBoundingBox().getVertices(1).getX());
							pt2.setY(block.getBoundingBox().getVertices(1).getY());
							bloques2.add(pt2);
							
							
							logger.debug("#BLOCK: " + block.getBoundingBox());
							
							String blockText = "";
							for (Paragraph para : block.getParagraphsList()) {
								logger.debug("#PARAG: " + para.getBoundingBox());
								
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
	

				//dcto.setBloques(bloques);
				
				int bloques = this.nroBloques();
				
				dcto.setBloques(bloques);

				File f = new File(filePathMod);
				String nombre = f.getName();
				nombre = nombre.substring(0, nombre.length() - 3);

				
				dcto.formarResultante(input.getParent(), nombre + "txt");

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
	public List<TipoDocumento> detectDocumentText(List<MultipartFile> files, String pathFile) throws Exception, IOException {

		logger.debug("detectDocumentText");		

		//Desempaquetar las imagenes que vienen en el TIF
		Desempaquetar desem = new Desempaquetar();
		List<String> lstArchivos = new ArrayList<String>();
		
		for(MultipartFile file: files) {			
			// enviar los archivos a una ruta temporal
			file.transferTo(new File(pathFile + File.separator + file.getOriginalFilename()));
			logger.debug("Se envió los archivos a la carpeta temporal: " + pathFile + File.separator
					+ file.getOriginalFilename());
			
			
			List<String> lstArchs = desem.doit(pathFile, file.getOriginalFilename(), pathFile, true);
			logger.debug("Archivos desempaquetados");
			logger.debug("Cant archivos: " + lstArchivos.size());
			lstArchivos.addAll(lstArchs);
		}
		

		
		String filePathMod = "";
		
		// lista de archivo de texto obtenidos
		List<String> lstArchivosTXT = new ArrayList<String>();
		
		//clase para ejecutar el archivo en python
		BatRunner batRunner = new BatRunner();		
		batRunner.setPropiedades(this.propiedades);
		

		logger.debug("Recorrer los archivos desempaquetados");
		for (String filePath : lstArchivos) {
			logger.debug("Archivo: " + filePath);

			filePathMod = filePath.substring(0, filePath.length() - 3) + "jpg";
			logger.debug("Archivo a modificar: " + filePathMod);

			batRunner.runProcess(filePath, filePathMod);
			logger.debug("Ejecutar el archivo Python");
			
			batRunner.runProcess(filePathMod, filePathMod);
			
			batRunner.runProcess(filePathMod, filePathMod);

			List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();
			logger.debug("se carga AnnotateImageRequest");
			
			ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePathMod));
			logger.debug("Bytes");

			Image img = Image.newBuilder().setContent(imgBytes).build();
			logger.debug("Img");
			
			Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			logger.debug("Feature");
			
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			logger.debug("Request");
			
			requests.add(request);
			logger.debug("Se llamó l servicio de google vision");

			Documento dcto = new Documento();
			dcto.setPropiedades(this.propiedades);
			logger.debug("Se asignó las propiedades");

			try {
				ImageAnnotatorClient client = ImageAnnotatorClient.create();
				logger.debug("Se creo el client");
				
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				logger.debug("Se creo el response");
				
				List<AnnotateImageResponse> responses = response.getResponsesList();
				logger.debug("se creo las respuestas");
				
				client.close();
				logger.debug("Se cerró el client");
				
				for (AnnotateImageResponse res : responses) {
					
					if (res.hasError()) {
						throw new Exception("Error");
					}

					TextAnnotation annotation = res.getFullTextAnnotation();
					for (Page page : annotation.getPagesList()) {
						String pageText = "";
						for (Block block : page.getBlocksList()) {
							
							//agrgar los puntos superiores de los bloques
							Punto pt1 = new Punto();
							pt1.setX(block.getBoundingBox().getVertices(0).getX());
							pt1.setY(block.getBoundingBox().getVertices(0).getY());
							bloques1.add(pt1);
							
							Punto pt2 = new Punto();
							pt2.setX(block.getBoundingBox().getVertices(1).getX());
							pt2.setY(block.getBoundingBox().getVertices(1).getY());
							bloques2.add(pt2);
							
							
							logger.debug("#BLOCK: " + block.getBoundingBox());
							
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
			}catch (Exception e) {
			    logger.error(e);
			}

			logger.debug("Se obtuvo la información liquida");

			File f = new File(filePathMod);
			String nombre = f.getName();
			nombre = nombre.substring(0, nombre.length() - 3);

			BufferedImage image = ImageIO.read(f);

			/*
			if (image.getHeight() > 1000) {
				dcto.setSwHorizontal(false);
			} else {
				dcto.setSwHorizontal(true);
			}*/
			
			int bloques = this.nroBloques();
			
			dcto.setBloques(bloques);
			
			dcto.formarResultante(pathFile, nombre + "txt");
			logger.debug("Se formo el archivo txt: " + pathFile + nombre + "txt");

			lstArchivosTXT.add(pathFile + File.separator + nombre + "txt");
		}

		// verificar todos los archivos TXT resueltos, para ver si son balances u otros
		// tipos
	
		//agregar la clasificacion de imagenes
		
		
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
			tipodoc.setFilename(nombre + "jpg");
			tipodoc.setFilenameTxt(nombre + "txt");
			lstTipos.add(tipodoc);

			if (tipodoc.getEmpresa() != null && !tipodoc.getEmpresa().equals("")) {
				empresa = tipodoc.getEmpresa();
			}
			logger.debug("Datos Extraídos: " + tipodoc.toString());


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
	
	private int nroBloques() {
		logger.debug("nroBloques");		
		int nroBloques = 0;
		
		List<Integer> conteoBloques = new ArrayList<Integer>();
		
		int ejeY = 0;
		int cont = 1;
		
		logger.debug("Recorrer todos los puntos de los bloques");
		for(int i = 0; i < this.bloques1.size(); i++) {
			
			logger.debug("#Bloque " + i );
			
			Punto pt1 = this.bloques1.get(i);
			logger.debug("Punto1: " + pt1);
			
			Punto pt2 = this.bloques2.get(i);
			logger.debug("Punto2: " + pt2);
			
			//verificar si los puntos son muy cercanos en el eje Y (+/- 5)
			if (pt1.equalsY(pt2)) {
				logger.debug("Los puntos estan a la misma altura");
				
				int diff = ejeY - pt1.getY(); 
				logger.debug("Diff: " + diff);
				
				if(( diff >= 0 && diff <= 5) || (diff < 0 && diff  >= -5)){
					logger.debug("La diferencia es +/- 5pts");
					cont++;
				}else {
					logger.debug("La diferencia es más de +/-5pts");
					ejeY = pt1.getY();
					conteoBloques.add(cont);
					cont = 1;
				}
				
			}
		}
		
		//calcular el maximo en la coleccion
		nroBloques = Collections.max(conteoBloques);
		logger.debug("Nro bloques: " + nroBloques);
		
		return nroBloques;
	}
}
