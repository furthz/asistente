package pe.soapros.asistente.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import pe.soapros.asistente.funcionality.RegexPlan;

public class Util {

	protected final static Log logger = LogFactory.getLog(Util.class);

	/**
	 * 
	 * @param json
	 * @param cadena
	 */
	public static void leerPlanCuentasRegex(String json, PlanCuenta plan, String archivo) {
		// List<List<String>> a = new ArrayList<List<String>>();

		List<Double> lstEfectivo = new ArrayList<Double>();
		
		List<Double> lstCapital = new ArrayList<Double>();
		List<Double> lstPrima = new ArrayList<Double>();
		List<Double> lstSuperavit = new ArrayList<Double>();
		List<Double> lstReserva = new ArrayList<Double>();
		List<Double> lstResultado = new ArrayList<Double>();
		List<Double> lstTotalUtilidades = new ArrayList<Double>();
		
		//activo fijo
		List<Double> lstTerrenos = new ArrayList<Double>();
		List<Double> lstContProceso = new ArrayList<Double>();
		List<Double> lstEdificios = new ArrayList<Double>();
		List<Double> lstMaquinaria = new ArrayList<Double>();
		List<Double> lstMuebles = new ArrayList<Double>();
		List<Double> lstTransporte = new ArrayList<Double>();
		
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
			//Pattern pat = Pattern.compile("\\d+(?:[.,]\\d+)?|Free"); //OJO CORREGIR EL PATRON ADECUADO DEL NUMERO PARA OBTENER SOLO UNA COLUMNA
			Pattern pat = Pattern.compile("(\\d{1,3}(\\s{0,1}[,|.]\\d{3})+)(\\s{0,1}[,|.]\\d{2})?");
			
			//Valor: 33.156.863          34.613.408      33.760.000
			Matcher m = pat.matcher(valor);

			while (m.find()) {
				all += m.group();
			}
			logger.debug("Numero: " + all);

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

			//buscar etiquetas regex
			
			if(etiqueta.equals("Efectivo")){
				lstEfectivo.add(lvalor);
				
			}else if(etiqueta.equals("Capital")){
				lstCapital.add(lvalor);				
			}else if(etiqueta.equals("Prima_Colocacion_Acciones")) {
				lstPrima.add(lvalor);
			}else if(etiqueta.equals("Otros_Superavit_Capital")) {
				lstSuperavit.add(lvalor);
			}else if(etiqueta.equals("Reserva_Legal")) {
				lstReserva.add(lvalor);
			}else if(etiqueta.equals("Resultado_Ejercicio")) {
				lstResultado.add(lvalor);
			}else if(etiqueta.equals("Total_Utilidades")) {
				lstTotalUtilidades.add(lvalor);
			}else if(etiqueta.equals("Terrenos")) {
				lstTerrenos.add(lvalor);
			}else if(etiqueta.equals("Construccion_Proceso")) {
				lstContProceso.add(lvalor);			
			}else if(etiqueta.equals("Edificios_Mejoras")) {
				lstEdificios.add(lvalor);
			}
			else if(etiqueta.equals("Maquinaria_Equipo")) {
				lstMaquinaria.add(lvalor);
			}else if(etiqueta.equals("Muebles_Enseres")) {
				lstMuebles.add(lvalor);
			}else if(etiqueta.equals("Equipo_Transporte")) {
				lstTransporte.add(lvalor);
			}
			
			else {
				plan.addCuenta(etiqueta, lvalor);
			}
			
//			if (!etiqueta.equals("Efectivo")) {
//				plan.addCuenta(etiqueta, lvalor);
//			} else {
//				lstEfectivo.add(lvalor);
//			}

		}

		RegexPlan regex = new RegexPlan(archivo);

		Set<Double> lstEfectivosRegex = regex.getEfectivo();		
		limpiarValores(lstEfectivosRegex, lstEfectivo, "Efectivo", plan);
		
		//PATRIMONIO
		
		Set<Double> lstCapitalRegex = regex.getPatrimonioCapital();
		limpiarValores(lstCapitalRegex, lstCapital, "Capital", plan);
		
		Set<Double> lstPrimaRegex = regex.getPatrimonioPrima();
		limpiarValores(lstPrimaRegex, lstPrima, "Prima_Colocacion_Acciones", plan);
		
		Set<Double> lstSuperavitRegex = regex.getPatrimonioSuperavit();
		limpiarValores(lstSuperavitRegex, lstSuperavit, "Otros_Superavit_Capital", plan);
		
		Set<Double> lstReservaRegex = regex.getPatrimonioReserva();
		limpiarValores(lstReservaRegex, lstReserva, "Reserva_Legal", plan);
		
		Set<Double> lstResultadoRegex = regex.getPatrimonioResultadoEjercicio();
		limpiarValores(lstResultadoRegex, lstResultado, "Resultado_Ejercicio", plan);
		
		Set<Double> lstTotalRegex = regex.getPatrimonioResultadoAnteriores();
		limpiarValores(lstTotalRegex, lstTotalUtilidades, "Total_Utilidades", plan);
		
		//ACTIVO FIJO
		Set<Double> lstTerrenosRegex = regex.getActivoFijoTerreno();
		limpiarValores(lstTerrenosRegex, lstTerrenos, "Terrenos", plan);
		
		Set<Double> lstContProcesoRegex = regex.getActivoFijoConstruccionProceso();
		limpiarValores(lstContProcesoRegex, lstContProceso, "Construccion_Proceso", plan);
		
		Set<Double> lstEdificiosRegex = regex.getActivoFijoConstruccionProceso();
		limpiarValores(lstEdificiosRegex, lstEdificios, "Edificios_Mejoras", plan);
		
		Set<Double> lstMaquinariaRegex = regex.getActivoFijoConstruccionProceso();
		limpiarValores(lstMaquinariaRegex, lstMaquinaria, "Maquinaria_Equipo", plan);
		
		Set<Double> lstMueblesRegex = regex.getActivoFijoConstruccionProceso();
		limpiarValores(lstMueblesRegex, lstMuebles, "Muebles_Enseres", plan);
		
		Set<Double> lstTransporteRegex = regex.getActivoFijoConstruccionProceso();
		limpiarValores(lstTransporteRegex, lstTransporte, "Equipo_Transporte", plan);
		
		

	}
	
	private static void limpiarValores(Set<Double> regexs, List<Double> lista, String etiqueta, PlanCuenta plan){
		
		logger.debug("Regex : " + etiqueta + " " + regexs);
		logger.debug("IA : " + etiqueta + " " + lista);
		
		for(Double cuenta: regexs) {
			lista.add(cuenta);
		}
		
		Set<Double> sethash = new HashSet<Double>();
		sethash.addAll(lista);
		
		for(Double c: sethash) {
			plan.addCuenta(etiqueta, c);
		}
	}

	/**
	 * 
	 * @param json
	 * @param plan
	 */
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
				all += m.group(0);
			}
			logger.debug("Numero: " + all);

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
			// EVITAR VALORES DUPLICADOS

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

		// CharSequence csBalance = "BALANCE";
		// CharSequence csEstado = "ESTADO";
		// CharSequence csNota = "NOTA";

		String ptrBalance = "BALANCE\\s*\\W+";
		String ptrBalance1 = "ESTADOS DE SITUACION\\s*\\W+";
		String ptrEstado = "ESTADO\\s*\\W+";
		String ptrNota = "NOTA\\s*\\W+";

		Pattern pat = null;
		Matcher mat = null;

		pat = Pattern.compile(ptrBalance);
		mat = pat.matcher(valor.toUpperCase());

		boolean find = mat.find();

		if (find) {
			tipoDcto.setTipoDoc("Balance");
		} else {
			pat = Pattern.compile(ptrBalance1);
			mat = pat.matcher(valor.toUpperCase());
			boolean findEs = mat.find();

			if (findEs) {
				tipoDcto.setTipoDoc("Balance");
			} else {

				pat = Pattern.compile(ptrEstado);
				mat = pat.matcher(valor.toUpperCase());

				boolean findEstado = mat.find();

				if (findEstado) {
					tipoDcto.setTipoDoc("Estado Resultados");
				} else {

					pat = Pattern.compile(ptrNota);
					mat = pat.matcher(valor.toUpperCase());

					boolean findNota = mat.find();

					if (findNota) {
						tipoDcto.setTipoDoc("Notas");
					} else {

						if ("".equals(valor) || valor == null) {
							tipoDcto.setTipoDoc("Balance");
						} else {
							tipoDcto.setTipoDoc(valor);
						}

					}

				}
			}

		}

		// Castear el tipo
		// if (valor.toUpperCase().contains(csBalance)) {
		// tipoDcto.setTipoDoc("Balance");
		// } else if (valor.toUpperCase().contains(csEstado)) {
		// tipoDcto.setTipoDoc("Estado Resultados");
		// } else if (valor.toUpperCase().contains(csNota)) {
		// tipoDcto.setTipoDoc("Notas");
		// } else if("".equals(valor) || valor == null) {
		// tipoDcto.setTipoDoc("Balance");
		// }
		// else {
		// tipoDcto.setTipoDoc(valor);
		// }

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
			texto = texto.concat(cadena + "\n");
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
