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
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.domain.TipoDocumento;

public class Util {

	protected final static Log logger = LogFactory.getLog(Util.class);

	public static void leerPlanCuentas(String json, PlanCuenta plan) {

		JSONObject obj = new JSONObject(json);
		logger.debug("JSON: " + json);

		JSONArray entidades = obj.getJSONArray("entities");
		logger.debug("Entidades: " + entidades);

		boolean swNum = false;
		String etiqueta = "";
		String valor = "";

		for (int i = 0; i < entidades.length(); i++) {

			swNum = false;

			etiqueta = entidades.getJSONObject(i).getString("type");
			logger.debug("Etiqueta: " + etiqueta);
			
			valor = entidades.getJSONObject(i).getString("text");
			logger.debug("Valor: " + valor);

			try {
				Character pto = valor.charAt(valor.length() - 3);
				logger.debug("Caracter: " + pto.toString());
				
				if (pto.toString().equals(",") || pto.toString().equals(".")) {
					swNum = true;
				}
			} catch (Exception e) {
				swNum = false;
				logger.error(e);
			}
			//
			String all = "";
			Pattern pat = Pattern.compile("\\d+(?:[.,]\\d+)?|Free");
			Matcher m = pat.matcher(valor);

			while (m.find()) {

				// System.out.println(m.group(0));
				// System.out.println(" - Coincidencia: " + m.group(0));
				all += m.group(0);
			}
			logger.debug("Numero: " + all);

			// all = all.replace(pto.toString(), ".");

			// valor = valor.replaceAll(" ", "");
			// valor = valor.replaceAll(".", "");
			// valor = valor.replaceAll(",", "");

			String[] valEtiqueta = all.split("\\W+");

			valor = "";
			for (String ss : valEtiqueta) {
				valor += ss;
			}

			if (swNum) {
				valor = valor.substring(0, valor.length() - 2) + "."
						+ valor.substring(valor.length() - 2, valor.length());
				logger.debug("valor: " + valor);
			}

			Double lvalor = 0.0;
			try {
				lvalor = Double.parseDouble(valor);
				logger.debug("valor convertido: " + lvalor);
			} catch (Exception e) {
				logger.error(e);
				// lvalor = (long)-1.00;
			}

			plan.addCuenta(etiqueta, lvalor);

		}

	}

	public static String leerEtiquetaJSON(String json, String etiqueta) {
		String rpta = "";

		JSONObject obj = new JSONObject(json);
		JSONArray entidades = obj.getJSONArray("entities");
		List<String> valores = new ArrayList<String>();

		for (int i = 0; i < entidades.length(); i++) {
			if (entidades.getJSONObject(i).getString("type").equals(etiqueta)) {
				valores.add(entidades.getJSONObject(i).getString("text"));

			}
		}

		boolean swLetras = false;

		List<String> valoresMod = new ArrayList<String>();
		// iterar en cada una de las mismas etiquetas
		for (String val : valores) {
			String[] valEtiqueta = val.split("\\W+");
			String valReconocido = "";
			for (String valRec : valEtiqueta) {

				if (valRec.length() > 1 && Character.isLetter(valRec.charAt(valRec.length() - 1))) {
					valReconocido += " ";

					swLetras = true;
				} else {
					if (swLetras && NumberUtils.isNumber(valRec)) {
						valReconocido += " ";
						swLetras = false;
					} else if (!swLetras && !NumberUtils.isNumber(valRec)) {
						valReconocido += " ";
						swLetras = true;
					}
				}

				valReconocido += valRec;

			}
			valoresMod.add(valReconocido);
		}

		for (String pal : valoresMod) {
			rpta += pal;
		}

		return rpta.trim();
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
		} else if("".equals(valor) || valor == null) {
			tipoDcto.setTipoDoc("Balance");
		}
		else {
			tipoDcto.setTipoDoc(valor);
		}

		// se agrega la empresa
		String empresa = Util.leerEtiquetaJSON(json, "empresa");
		// String[] palEmpresa = empresa.split("\\W+");

		// empresa = "";
		// for(String pal: palEmpresa) {
		// empresa = empresa + pal + " ";
		// }
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

		tipoDcto.setIdEmpresa(id);

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

	public static List<Path> listarFicheros(String path, final String extension) throws IOException {
		final List<Path> archivos = new ArrayList<Path>();
		Files.walk(Paths.get(path)).forEach(new Consumer<Path>() {
			public void accept(Path ruta) {
				if (Files.isRegularFile(ruta) && getFileExtension(new File(ruta.toString())).equals(extension)) {
					// System.out.println(ruta);
					archivos.add(ruta);
				}
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
