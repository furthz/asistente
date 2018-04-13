package pe.soapros.asistente.util;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.codehaus.plexus.util.cli.CommandLineException;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFEncodeParam;

/***
 * Clase para desempaquetar las imagenes contenidas dentro de un TIF
 * 
 * @author Raul Talledo
 * @version 1.0
 */
public class Desempaquetar {

	/***
	 * Método para desempaquetar un TIF
	 * 
	 * @param path
	 *            Ruta donde se encuentra el archivo a desempaquetar
	 * @param filename
	 *            Nombre del archivo
	 * @return Lista con la ruta de cada archivo convertido
	 * @throws IOException
	 */
	public static List<String> doit(String path, String filename) throws IOException {

		FileSeekableStream ss = new FileSeekableStream(path + File.separator + filename);

		ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, null);
		int count = dec.getNumPages();
		TIFFEncodeParam param = new TIFFEncodeParam();
		param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
		param.setLittleEndian(false);

		List<String> lstArchivos = new ArrayList<String>();
		String archivo = "";
		for (int i = 0; i < count; i++) {
			RenderedImage page = dec.decodeAsRenderedImage(i);
			archivo = path + File.separator + "convert-" + filename.substring(0, filename.length() - 3) + i + ".tif";
			File f = new File(archivo);
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(page);
			pb.add(f.toString());
			pb.add("tiff");
			pb.add(param);
			RenderedOp r = JAI.create("filestore", pb);
			//r.dispose();

			lstArchivos.add(archivo);

		}

		return lstArchivos;
	}

	public static List<String> extractMultiPageTiff(String path, String filename) throws IOException {

		List<String> lstArchivos = new ArrayList<String>();
		
		/*
		 * create object of RenderedIamge to produce image data in form of Rasters
		 */
		RenderedImage renderedImage[], page;
		//File file = new File(tiffFilePath);
		File file = new File(path + File.separator + filename);
		/*
		 * SeekabaleStream is use for taking input from file. FileSeekableStream is not
		 * committed part of JAI API.
		 */
		SeekableStream seekableStream = new FileSeekableStream(file);
		ImageDecoder imageDecoder = ImageCodec.createImageDecoder("tiff", seekableStream, null);
		renderedImage = new RenderedImage[imageDecoder.getNumPages()];

		/* count no. of pages available inside input tiff file */
		int count = 0;
		for (int i = 0; i < imageDecoder.getNumPages(); i++) {
			renderedImage[i] = imageDecoder.decodeAsRenderedImage(i);
			count++;
		}

//		/* set output folder path */
//		String outputFolderName;
//		String[] temp = null;
//		temp = tiffFilePath.split("\\.");
//		outputFolderName = temp[0];
//		/*
//		 * create file object of output folder and make a directory
//		 */
//		File fileObjForOPFolder = new File(outputFolderName);
//		fileObjForOPFolder.mkdirs();

		/*
		 * extract no. of image available inside the input tiff file
		 */
		String archivo = "";
		for (int i = 0; i < count; i++) {
			page = imageDecoder.decodeAsRenderedImage(i);
			archivo = path + File.separator + "convert-" + filename.substring(0, filename.length() - 3) + i + ".png";
			File fileObj = new File(archivo);
			/*
			 * ParameterBlock create a generic interface for parameter passing
			 */
			ParameterBlock parameterBlock = new ParameterBlock();
			/* add source of page */
			parameterBlock.addSource(page);
			/* add o/p file path */
			parameterBlock.add(fileObj.toString());
			/* add o/p file type */
			parameterBlock.add("png");
			/* create output image using JAI filestore */
			RenderedOp renderedOp = JAI.create("filestore", parameterBlock);
			//renderedOp.dispose();
			lstArchivos.add(archivo);
		}
		return lstArchivos;
	}

	public List<String> doit(String path, String filename, String dest) throws IOException {

		FileSeekableStream ss = new FileSeekableStream(path + File.separator + filename);

		ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, null);
		int count = dec.getNumPages();
		TIFFEncodeParam param = new TIFFEncodeParam();
		param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
		param.setLittleEndian(false);

		List<String> lstArchivos = new ArrayList<String>();
		String archivo = "";
		for (int i = 0; i < count; i++) {
			RenderedImage page = dec.decodeAsRenderedImage(i);
			archivo = dest + File.separator + "convert-" + filename.substring(0, filename.length() - 3) + i + ".tif";
			File f = new File(archivo);
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(page);
			pb.add(f.toString());
			pb.add("tiff");
			pb.add(param);
			RenderedOp r = JAI.create("filestore", pb);
			r.dispose();

			lstArchivos.add(archivo);

		}

		return lstArchivos;
	}

	private List<Path> listarFicheros(String path) throws IOException {
		List<Path> archivos = new ArrayList<Path>();
		Files.walk(Paths.get(path)).forEach(ruta -> {
			if (Files.isRegularFile(ruta) && this.getFileExtension(new File(ruta.toString())).equals("tif")) {
				// System.out.println(ruta);
				archivos.add(ruta);
			}
		});

		return archivos;
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	public void conversionMasiva(String path, String dest) throws IOException, CommandLineException {

		List<Path> archivos = this.listarFicheros(path);

		String carpetaDest = "";
		String nombreArchivo = "";
		for (Path p : archivos) {
			nombreArchivo = p.getFileName().toString();
			carpetaDest = nombreArchivo.substring(0, nombreArchivo.length() - 4);

			Path pp = Paths.get(dest + File.separator + carpetaDest);

			Files.createDirectory(pp);

			List<String> lstArchivos = this.doit(p.getParent().toString(), nombreArchivo,
					dest + File.separator + carpetaDest);

			String filePathMod = "";

			for (String filePath : lstArchivos) {

				filePathMod = filePath.substring(0, filePath.length() - 3) + "png";

				BatRunner.runProcess(filePath, filePathMod);

				File ff = new File(filePath);
				ff.delete();

			}

			System.out.println(p.getParent());
			System.out.println(p.getFileName());
		}

	}

	public static void main(String[] args) throws IOException, CommandLineException {
		Desempaquetar desemp = new Desempaquetar();
		desemp.conversionMasiva("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\Lista 5",
				"D:\\archivos1");
	}

}
