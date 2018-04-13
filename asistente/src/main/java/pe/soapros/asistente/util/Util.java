package pe.soapros.asistente.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import pe.soapros.asistente.domain.TipoDocumento;

public class Util {

	protected final static Log logger = LogFactory.getLog(Util.class);

	public static String leerEtiquetaJSON(String json, String etiqueta) {
		JSONObject obj = new JSONObject(json);
		JSONArray entidades = obj.getJSONArray("entities");
		String valor = "";

		for (int i = 0; i < entidades.length(); i++) {
			if (entidades.getJSONObject(i).getString("type").equals(etiqueta)) {
				valor = entidades.getJSONObject(i).getString("text");
				break;
			}
		}

		return valor;
	}

	public static TipoDocumento getTipoDcto(String json) {
		logger.debug("TipoDOcumento");
		logger.debug("JSON: " + json);

		TipoDocumento tipoDcto = new TipoDocumento();

		String valor = Util.leerEtiquetaJSON(json, "tipodoc");
		logger.debug("tipodoc: " + valor);

		CharSequence csBalance = "BALANCE";
		CharSequence csEstado = "ESTADO";
		CharSequence csNota = "NOTA";

		// Castear el tipo
		if (valor.toUpperCase().contains(csBalance)) {
			tipoDcto.setTipoDoc("Balance");
		} else if (valor.toUpperCase().contains(csEstado)) {
			tipoDcto.setTipoDoc("Estado Resultados");
		} else if (valor.toUpperCase().contains(csNota)) {
			tipoDcto.setTipoDoc("Notas");
		} else {
			tipoDcto.setTipoDoc(valor);
		}

		// se agrega la empresa
		String empresa = Util.leerEtiquetaJSON(json, "empresa");
		String[] palEmpresa = empresa.split("\\W+");
		
		empresa = "";
		for(String pal: palEmpresa) {
			empresa = empresa + pal + " ";
		}
		logger.debug("empresa: " + empresa);

		tipoDcto.setEmpresa(empresa);

		// se agrega unidad
		String unidad = Util.leerEtiquetaJSON(json, "unidad");
		logger.debug("unidad: " + unidad);

		tipoDcto.setUnidad(unidad);

		// se agrega la fech
		String fecha = Util.leerEtiquetaJSON(json, "fecha");
		logger.debug("fecha: " + fecha);

		tipoDcto.setFecha(fecha);

		// se agrega el id
		String id = Util.leerEtiquetaJSON(json, "id");
		logger.debug("id: " + id);

		tipoDcto.setId(id);

		return tipoDcto;
	}

	public static String leerArchivoTXT(String archivo) throws IOException {
		String texto = new String();

		String cadena = "";

		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			texto = texto.concat(cadena);
		}
		b.close();

		return texto;
	}

	public static List<Path> listarFicheros(String path, String extension) throws IOException {
		List<Path> archivos = new ArrayList<Path>();
		Files.walk(Paths.get(path)).forEach(ruta -> {
			if (Files.isRegularFile(ruta) && getFileExtension(new File(ruta.toString())).equals(extension)) {
				// System.out.println(ruta);
				archivos.add(ruta);
			}
		});

		return archivos;
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}