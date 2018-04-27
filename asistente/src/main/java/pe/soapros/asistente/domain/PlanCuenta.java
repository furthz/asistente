package pe.soapros.asistente.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class PlanCuenta {
	private HashMap<String, Double> activo;	
	private HashMap<String, Double> pasivo;
	private HashMap<String, Double> patrimonio;
	
	private List<String> indActivo;
	private List<String> indPasivo;
	private List<String> indPatrimonio;
	
	private Empresa empresa;
	
	private List<TipoDocumento> lstTipoDocumento;

	protected final Log logger = LogFactory.getLog(getClass());
	
	public PlanCuenta() {
		activo = new HashMap<String, Double>();
		pasivo = new HashMap<String, Double>();
		patrimonio = new HashMap<String, Double>();		
		
		indActivo = new ArrayList<String>();
		indPasivo = new ArrayList<String>();
		indPatrimonio = new ArrayList<String>();
		
		//se inicio las cuentas de activos
		this.iniciarActivo();
	}
	
	
	public HashMap<String, Double> getActivo() {
		return activo;
	}


	public void setActivo(HashMap<String, Double> activo) {
		this.activo = activo;
	}


	public HashMap<String, Double> getPasivo() {
		return pasivo;
	}


	public void setPasivo(HashMap<String, Double> pasivo) {
		this.pasivo = pasivo;
	}


	public HashMap<String, Double> getPatrimonio() {
		return patrimonio;
	}


	public void setPatrimonio(HashMap<String, Double> patrimonio) {
		this.patrimonio = patrimonio;
	}


	
	public List<String> getIndActivo() {
		return indActivo;
	}


	public void setIndActivo(List<String> indActivo) {
		this.indActivo = indActivo;
	}


	public List<String> getIndPasivo() {
		return indPasivo;
	}


	public void setIndPasivo(List<String> indPasivo) {
		this.indPasivo = indPasivo;
	}


	public List<String> getIndPatrimonio() {
		return indPatrimonio;
	}


	public void setIndPatrimonio(List<String> indPatrimonio) {
		this.indPatrimonio = indPatrimonio;
	}

	

	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	
	public List<TipoDocumento> getLstTipoDocumento() {
		return lstTipoDocumento;
	}


	public void setLstTipoDocumento(List<TipoDocumento> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}


	private void iniciarActivo() {

		//ACTIVOS
		this.activo.put("Efectivo", 0.0);
		this.activo.put("Inversiones_Temporales", 0.0);
		this.activo.put("Ctas_Cobrar_Comerciales", 0.0);
		this.activo.put("Provision_Cartera", 0.0);
		this.activo.put("Inventarios", 0.0); 
		this.activo.put("Anticipo_Avances", 0.0);
		this.activo.put("Activos_Biologicos", 0.0);
		this.activo.put("Anticipo_Impuesto", 0.0);
		this.activo.put("Impuesto_Diferido_Activo",  0.0);
		this.activo.put("Ctas_Cobrar_Vinc_Economicos",  0.0);
		this.activo.put("Activos_Mant_Venta",  0.0);
		this.activo.put("Activos_Diferidos",  0.0);
		this.activo.put("Activos_Derivados",  0.0);
		this.activo.put("Otros_Activos",  0.0);
		this.activo.put("Terrenos",  0.0);
		this.activo.put("Construccion_Proceso",  0.0);
		this.activo.put("Edificios_Mejoras",  0.0);
		this.activo.put("Maquinaria_Equipo",  0.0);
		this.activo.put("Muebles_Enseres",  0.0);
		this.activo.put("Equipo_Transporte",  0.0);
		this.activo.put("Leasing",  0.0);
		this.activo.put("Otros_Activos_Fijos",  0.0);
		this.activo.put("Propiedades_Inversion",  0.0);
		this.activo.put("Depreciacion_Acumulada",  0.0);
		this.activo.put("Activos_Biologicos_LP",  0.0);
		this.activo.put("Intangibles",  0.0);
		this.activo.put("Inversiones_Permanentes",  0.0);
		this.activo.put("Efectivo_Restringido",  0.0);
		this.activo.put("Ctas_Cobrar_Vinc_Economicos_LP",  0.0);
		this.activo.put("Ctas_Cobrar_LP",  0.0);
		this.activo.put("Impuesto_Diferido_Activo_LP",  0.0);
		this.activo.put("Activos_Diferidos_LP",  0.0);
		this.activo.put("Activos_Derivados_LP",  0.0);
		this.activo.put("Otros_Activos_LP",  0.0);
		this.activo.put("Valorizacion_Inversiones",  0.0);
		this.activo.put("Valorizacion_Activos_Fijos",  0.0);	
		
		this.indActivo.add("Efectivo");
		this.indActivo.add("Inversiones_Temporales");
		this.indActivo.add("Ctas_Cobrar_Comerciales");
		this.indActivo.add("Provision_Cartera");
		this.indActivo.add("Inventarios"); 
		this.indActivo.add("Anticipo_Avances");
		this.indActivo.add("Activos_Biologicos");
		this.indActivo.add("Anticipo_Impuesto");
		this.indActivo.add("Impuesto_Diferido_Activo");
		this.indActivo.add("Ctas_Cobrar_Vinc_Economicos");
		this.indActivo.add("Activos_Mant_Venta");
		this.indActivo.add("Activos_Diferidos");
		this.indActivo.add("Activos_Derivados");
		this.indActivo.add("Otros_Activos");
		this.indActivo.add("Terrenos");
		this.indActivo.add("Construccion_Proceso");
		this.indActivo.add("Edificios_Mejoras");
		this.indActivo.add("Maquinaria_Equipo");
		this.indActivo.add("Muebles_Enseres");
		this.indActivo.add("Equipo_Transporte");
		this.indActivo.add("Leasing");
		this.indActivo.add("Otros_Activos_Fijos");
		this.indActivo.add("Propiedades_Inversion");
		this.indActivo.add("Depreciacion_Acumulada");
		this.indActivo.add("Activos_Biologicos_LP");
		this.indActivo.add("Intangibles");
		this.indActivo.add("Inversiones_Permanentes");
		this.indActivo.add("Efectivo_Restringido");
		this.indActivo.add("Ctas_Cobrar_Vinc_Economicos_LP");
		this.indActivo.add("Ctas_Cobrar_LP");
		this.indActivo.add("Impuesto_Diferido_Activo_LP");
		this.indActivo.add("Activos_Diferidos_LP");
		this.indActivo.add("Activos_Derivados_LP");
		this.indActivo.add("Otros_Activos_LP");
		this.indActivo.add("Valorizacion_Inversiones");
		this.indActivo.add("Valorizacion_Activos_Fijos");	
		
		
		//PASIVOS
		this.pasivo.put("Sobregiros", 0.0);
		this.pasivo.put("Obligaciones_Bancarias_CP", 0.0);
		this.pasivo.put("Leasing_CP", 0.0);
		this.pasivo.put("Particulares_CP", 0.0);
		this.pasivo.put("Bonos_Papeles_Cciales_CP", 0.0);
		this.pasivo.put("Ctas_Pagar_Comerciales", 0.0);
		this.pasivo.put("Otras_Ctas_Pagar", 0.0);
		this.pasivo.put("Ctas_Pagar_Vinc_Economicos", 0.0);
		this.pasivo.put("Obligaciones_Laborales", 0.0);
		this.pasivo.put("Impuestos_Pagar", 0.0);
		this.pasivo.put("Impuesto_Diferido_Pasivo", 0.0);
		this.pasivo.put("Dividendos_Pagar", 0.0);
		this.pasivo.put("Pasivos_Diferidos", 0.0);
		this.pasivo.put("Pasivos_Estim_Provisiones", 0.0);
		this.pasivo.put("Pasivos_Derivados", 0.0);
		this.pasivo.put("Otros_Pasivos", 0.0);
		this.pasivo.put("Obligaciones_Bancarias_LP", 0.0);
		this.pasivo.put("Leasing_LP", 0.0);
		this.pasivo.put("Particulares_LP", 0.0);
		this.pasivo.put("Bonos_Papeles_Cciales_LP", 0.0);
		this.pasivo.put("Obligaciones_Laborales_LP", 0.0);
		this.pasivo.put("Ctas_Pagar_Vinc_Economicos_LP", 0.0);
		this.pasivo.put("Pasivos_Diferidos_LP", 0.0);
		this.pasivo.put("Pasivos_Estim_Provisiones_LP", 0.0);
		this.pasivo.put("Pasivos_Derivados_LP", 0.0);
		this.pasivo.put("Otros_Pasivos_LP", 0.0);
		this.pasivo.put("Impueso_Diferido_Pasivo_LP", 0.0);
		
		this.indPasivo.add("Sobregiros");
		this.indPasivo.add("Obligaciones_Bancarias_CP");
		this.indPasivo.add("Leasing_CP");
		this.indPasivo.add("Particulares_CP");
		this.indPasivo.add("Bonos_Papeles_Cciales_CP");
		this.indPasivo.add("Ctas_Pagar_Comerciales");
		this.indPasivo.add("Otras_Ctas_Pagar");
		this.indPasivo.add("Ctas_Pagar_Vinc_Economicos");
		this.indPasivo.add("Obligaciones_Laborales");
		this.indPasivo.add("Impuestos_Pagar");
		this.indPasivo.add("Impuesto_Diferido_Pasivo");
		this.indPasivo.add("Dividendos_Pagar");
		this.indPasivo.add("Pasivos_Diferidos");
		this.indPasivo.add("Pasivos_Estim_Provisiones");
		this.indPasivo.add("Pasivos_Derivados");
		this.indPasivo.add("Otros_Pasivos");
		this.indPasivo.add("Obligaciones_Bancarias_LP");
		this.indPasivo.add("Leasing_LP");
		this.indPasivo.add("Particulares_LP");
		this.indPasivo.add("Bonos_Papeles_Cciales_LP");
		this.indPasivo.add("Obligaciones_Laborales_LP");
		this.indPasivo.add("Ctas_Pagar_Vinc_Economicos_LP");
		this.indPasivo.add("Pasivos_Diferidos_LP");
		this.indPasivo.add("Pasivos_Estim_Provisiones_LP");
		this.indPasivo.add("Pasivos_Derivados_LP");
		this.indPasivo.add("Otros_Pasivos_LP");
		this.indPasivo.add("Impueso_Diferido_Pasivo_LP");
		
		
		//patrimomio
		this.patrimonio.put("Capital", 0.0);
		this.patrimonio.put("Prima_Colocacion_Acciones", 0.0);
		this.patrimonio.put("Otros_Superavit_Capital", 0.0);
		this.patrimonio.put("Reserva_Legal", 0.0);
		this.patrimonio.put("Otras_Reservas", 0.0);
		this.patrimonio.put("Resultado_Ejercicio", 0.0);
		this.patrimonio.put("Total_Utilidades", 0.0);
		this.patrimonio.put("Otro_Resultado_Integral_Acum_ORI", 0.0);
		this.patrimonio.put("Valorizacion_Activos_Fijos_Patrimonio", 0.0);
		this.patrimonio.put("Valorizacion_Inversiones_Patrimonio", 0.0);
		this.patrimonio.put("Otras_Ctas_Patrimoniales", 0.0);
		this.patrimonio.put("Revalorizacion_Patrimonio", 0.0);
		
		
		this.indPatrimonio.add("Capital");
		this.indPatrimonio.add("Prima_Colocacion_Acciones");
		this.indPatrimonio.add("Otros_Superavit_Capital");
		this.indPatrimonio.add("Reserva_Legal");
		this.indPatrimonio.add("Otras_Reservas");
		this.indPatrimonio.add("Resultado_Ejercicio");
		this.indPatrimonio.add("Total_Utilidades");
		this.indPatrimonio.add("Otro_Resultado_Integral_Acum_ORI");
		this.indPatrimonio.add("Valorizacion_Activos_Fijos_Patrimonio");
		this.indPatrimonio.add("Valorizacion_Inversiones_Patrimonio");
		this.indPatrimonio.add("Otras_Ctas_Patrimoniales");
		this.indPatrimonio.add("Revalorizacion_Patrimonio");
		
		
		//PATRIMONIO
		
	}
	
	public void addCuenta(String key, Double val) {
		Double valores = 0.0;
		logger.debug("Ingresar: " + key + " - " + val);
		
		if(activo.get(key) != null) {
			valores = activo.get(key);
			valores = valores + val;
			logger.debug("Agregar Activo: " + key + " valor: " + valores);
			activo.put(key, valores);
		}else if(pasivo.get(key) != null) {
			valores = pasivo.get(key);
			valores = valores + val;
			logger.debug("Agregar Pasivo: " + key + " valor: " + valores);
			pasivo.put(key, valores);
		}else if(patrimonio.get(key)!= null){
			valores = patrimonio.get(key);
			valores = valores + val;
			logger.debug("Agregar Patrimonio: " + key + " valor: " + valores);
			patrimonio.put(key, valores);
		}
		
		
	}

	public JSONObject getJSON() {
		return new JSONObject(this.activo);
	}
	@Override
	public String toString() {
		return "PlanCuenta [activo=" + activo + ", pasivo=" + pasivo + ", patrimonio=" + patrimonio + "]";
	}
	
	
	
}
