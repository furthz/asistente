package pe.soapros.asistente.util;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFEncodeParam;

/***
 * Clase para desempaquetar las imagenes contenidas dentro de un TIF
 * @author Raul Talledo
 * @version 1.0
 */
public class Desempaquetar {

	/***
	 * Método para desempaquetar un TIF
	 * @param path Ruta donde se encuentra el archivo a desempaquetar
	 * @param filename Nombre del archivo
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
			r.dispose();
			
			lstArchivos.add(archivo);

		}
		
		return lstArchivos;
	}

}
