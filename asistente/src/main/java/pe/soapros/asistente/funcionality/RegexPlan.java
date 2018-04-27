package pe.soapros.asistente.funcionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.soapros.asistente.util.Util;

public class RegexPlan {

	private String contenido;

	protected final Log logger = LogFactory.getLog(getClass());

	public RegexPlan(String contenido) {
		this.contenido = contenido;
	}

	public Set<Double> getEfectivo() {

		List<Double> lst = new ArrayList<Double>();

		// patrones de cuenta efectivo
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("DISPONIBLE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("CAJA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("EFECTIVO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("BANCO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("AHORRO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("DEPOSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("FONDO DE CARTERA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		
		try {

			//archivo = new File(this.contenido);

			//fr = new FileReader(archivo);
			//br = new BufferedReader(fr);

			String[] lineas = this.contenido.split("\\r?\\n");
			//String linea;
			Pattern pat = null;
			Matcher mat = null;
			for(String linea : lineas) {

				for (String patron : lstPatrones) {
					pat = Pattern.compile(patron);
					mat = pat.matcher(linea.toUpperCase());

					boolean find = mat.find();
					boolean swDecimal = false;
					// System.out.println(find);

					if (find) {
						String valor = mat.group(1);
						Character ptoDec = valor.charAt(valor.length() - 3);
						
						if (ptoDec.toString().equals(",") || ptoDec.toString().equals(".")) {
							swDecimal = true;
						}
						
						String[] valEtiqueta = valor.split("\\W+");
						
						valor = "";
						for (String ss : valEtiqueta) {
							valor += ss;
						}

						if (swDecimal) {
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
						
						lst.add(lvalor);
					
						
					}

				}
			}

		} catch (Exception e) {
			logger.error(e);
		}

		Set<Double> sethash = new HashSet<Double>();
		
		sethash.addAll(lst);
		
		return sethash;

	}

	public static void main(String[] args) throws IOException {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		String patron = "(\\d{1,3}(\\s*[,|.]\\d{3})*|(\\d+))([,|.]\\d{2})?";
		Pattern pat = Pattern.compile(patron);

		String contenido = "2.142,569,3342.725,000,000";//Util.leerArchivoTXT("D:\\Downloads\\convert-contr63676725.0.txt");
		//System.out.println(contenido);
		
		
		//String[] lineas = contenido.split("\\r?\\n");
	
		//for(String ln: lineas) {
			//System.out.println(ln);
			
			Matcher mat = pat.matcher(contenido.toUpperCase());

			//boolean find = mat.find();

			//System.out.println(contenido.replaceAll(patron, mat.group() + " "));
			// System.out.println(find);

			while (mat.find()) {
				System.out.println(mat.group());
				//System.out.println(mat.group(1));
				// System.out.println(mat.group(2));

			}

			
		//}
		
//		try {
//			// Apertura del fichero y creacion de BufferedReader para poder
//			// hacer una lectura comoda (disponer del metodo readLine()).
//			archivo = new File(
//					// "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
//					// Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2\\contr63278309\\convert-contr63278309.0.txt");
//					"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2\\contr63372110\\convert-contr63372110.1.txt");
//			fr = new FileReader(archivo);
//			br = new BufferedReader(fr);
//
//			// Lectura del fichero
//			String linea;
//			while ((linea = br.readLine()) != null) {
//				Matcher mat = pat.matcher(linea.toUpperCase());
//
//				boolean find = mat.find();
//
//				// System.out.println(find);
//
//				if (find) {
//					System.out.println(mat.group(0));
//					System.out.println(mat.group(1));
//					// System.out.println(mat.group(2));
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

}
