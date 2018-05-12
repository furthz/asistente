package pe.soapros.asistente.funcionality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegexPlan {

	private String contenido;

	protected final Log logger = LogFactory.getLog(getClass());

	public RegexPlan(String contenido) {
		this.contenido = contenido.toUpperCase();
	}

	/***************************************************
	 * ACTIVO CIRCULANTE
	 * 
	 ****************************************************/

	// EFECTIVO
	public Set<Double> getEfectivo() {

		// List<Double> lst = new ArrayList<Double>();

		// patrones de cuenta efectivo
		List<String> lstPatrones = new ArrayList<String>();
		//lstPatrones.add("DISPONIBLE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("CAJA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("EFECTIVO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		 //lstPatrones.add("BANCO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("AHORRO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("DEPOSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("FONDO DE
		// CARTERA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> efectivo = this.procesarPatrones(lstPatrones);

		return efectivo;

	}

	// INVERSIONES TEMPORALES
	public Set<Double> getInversionTemporal() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("BONOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("CERTIFICADOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("DERECHOS FIDUCIARIOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}

	// CTAS POR COBRAR CLIENTES
	public Set<Double> getCtasCobrarClientes() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones
				.add("CLIENTES NACIONALES Y DEL EXTERIOR[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("CUENTAS POR COBRAR CLIENTES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}

	// PROVISION CARTERA
	public Set<Double> getProvisionCartera() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("PROVISIONES CLIENTES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}

	// INVENTARIOS
	public Set<Double> getInventario() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("MATERIAS PRIMAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PRODUCTOS EN PROCESO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PRODUCTOS TERMINADOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("BIENES PARA LA VENTA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("MATERIALES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		//##OJO## AGREGAR EL PATRONO INVENTARIOS
		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}
	
	//ANTICIPO Y AVANCES
	public Set<Double> getAnticipoAvances() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("ANTICIPOS RECIBIDOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}


	//ACTIVOS BIOLOGICOS
	public Set<Double> getActivosBiologicos() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("ACTIVOS BIOLOGICOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("ACTIVOS BIOLÓGICOS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}
	
	//ANTICIPO DE IMPUESTOS
	public Set<Double> getAnticipoImpuestos() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("INDUSTRIA Y COMERCIO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("RETENCION EN LA FUENTE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("RETENCIÓN EN LA FUENTE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}
	
	
	/**********************************************************************************
	 * ACTIVO NO CIRCULANTE
	 * 
	 ******************************************************************************/
	
	// ACTIVO FIJO
	public Set<Double> getActivoFijoTerreno() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("TERRENO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("RURAL[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("NO DEPRECIABLE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getActivoFijoConstruccionProceso() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("EDIFICIO EN PROCESO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PROPIEDADES EN TRANSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PROPIEDADES EN TRÁNSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PROPIEDAD EN TRANSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PROPIEDAD EN TRÁNSITO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getActivoFijoPropiedadPlanta() {
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("PROPIEDADES PLANTA[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PROPIEDAD, PLANTA[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getActivoFijoMaquinaria() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("MAQUINARIA Y EQUIPO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("ACUEDUCTO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("INSTALACI[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("EQUIPO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getActivoFijoMuebles() {

		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("EQUIPO DE OFICINA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("MUEBLES Y ENSERES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("EQUIPO DE COMPUT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("EQUIPO DE CÓMPUT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	// PATRIMONIO
	// LISTA DE CUENTAS

	public Set<Double> getPatrimonioCapital() {

		// List<Double> lst = new ArrayList<Double>();

		// patrones de cuenta efectivo
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("CAPITAL[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("^PATRIMONIO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("ACCION[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("CUOTA DE INTER[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("APORTES SOCIALES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("APORTE[\\s]*](\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("APORTES DE SOCIO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("FONDO SOCIAL[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PARTES DE INTER[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("ACCION[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		// lstPatrones.add("^APORTE[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> capital = this.procesarPatrones(lstPatrones);

		return capital;

	}

	public Set<Double> getPatrimonioPrima() {
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("PRIMA EN COLOC[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;

	}

	public Set<Double> getPatrimonioSuperavit() {
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("SUPERAVIT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("SUPERÁVIT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("OTROS SUPERÁVIT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("OTROS SUPERAVIT[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getPatrimonioReserva() {
		List<String> lstPatrones = new ArrayList<String>();
		//lstPatrones.add("RESERVA[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getPatrimonioResultadoEjercicio() {
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("UTILIDADES ESTATUTARIAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("RESULTADO DEL EJERCICIO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("GANANCIAS[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PERDIDAS[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("UTILIDAD DEL PERIODO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("RESULTADO DEL PERIODO[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public Set<Double> getPatrimonioResultadoAnteriores() {
		List<String> lstPatrones = new ArrayList<String>();
		lstPatrones.add("UTILIDADES ACUMULADAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones
				.add("RESULTADOS EJERCICIOS ANTERIORES[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("GANANCIAS ACUMULADAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PÉRDIDAS ACUMULADAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");
		lstPatrones.add("PERDIDAS ACUMULADAS[A-Z]*[\\s]+[[A-Z]*[\\s]*]*(\\d+[\\s]*[,|.][\\d+[,|.]*\\d*]+)");

		Set<Double> cuenta = this.procesarPatrones(lstPatrones);

		return cuenta;
	}

	public static void main(String[] args) throws IOException {

		// File archivo = null;
		// FileReader fr = null;
		// BufferedReader br = null;

		String patron = "(\\d{1,3}(\\s*[,|.]\\d{3})*|(\\d+))([,|.]\\d{2})?";
		Pattern pat = Pattern.compile(patron);

		String contenido = "2.142,569,3342.725,000,000";// Util.leerArchivoTXT("D:\\Downloads\\convert-contr63676725.0.txt");
		// System.out.println(contenido);

		// String[] lineas = contenido.split("\\r?\\n");

		// for(String ln: lineas) {
		// System.out.println(ln);

		Matcher mat = pat.matcher(contenido.toUpperCase());

		// boolean find = mat.find();

		// System.out.println(contenido.replaceAll(patron, mat.group() + " "));
		// System.out.println(find);

		while (mat.find()) {
			System.out.println(mat.group());
			// System.out.println(mat.group(1));
			// System.out.println(mat.group(2));

		}

		// }

		// try {
		// // Apertura del fichero y creacion de BufferedReader para poder
		// // hacer una lectura comoda (disponer del metodo readLine()).
		// archivo = new File(
		// // "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// //
		// Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2\\contr63278309\\convert-contr63278309.0.txt");
		// "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2\\contr63372110\\convert-contr63372110.1.txt");
		// fr = new FileReader(archivo);
		// br = new BufferedReader(fr);
		//
		// // Lectura del fichero
		// String linea;
		// while ((linea = br.readLine()) != null) {
		// Matcher mat = pat.matcher(linea.toUpperCase());
		//
		// boolean find = mat.find();
		//
		// // System.out.println(find);
		//
		// if (find) {
		// System.out.println(mat.group(0));
		// System.out.println(mat.group(1));
		// // System.out.println(mat.group(2));
		//
		// }
		//
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	private Set<Double> procesarPatrones(List<String> patrones) {

		List<Double> lst = new ArrayList<Double>();

		try {
			String[] lineas = this.contenido.split("\\r?\\n");
			Pattern pat = null;
			Matcher mat = null;

			for (String linea : lineas) {
				for (String patron : patrones) {
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

}
