package pe.soapros.asistente.domain;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PlanCuenta {
	private HashMap<String, Long> activo;	
	private HashMap<String, Long> pasivo;
	private HashMap<String, Long> patrimonio;

	protected final Log logger = LogFactory.getLog(getClass());
	
	public PlanCuenta() {
		activo = new HashMap<String, Long>();
		pasivo = new HashMap<String, Long>();
		patrimonio = new HashMap<String, Long>();		
		
		//se inicio las cuentas de activos
		this.iniciarActivo();
	}
	
	
	public HashMap<String, Long> getActivo() {
		return activo;
	}


	public void setActivo(HashMap<String, Long> activo) {
		this.activo = activo;
	}


	public HashMap<String, Long> getPasivo() {
		return pasivo;
	}


	public void setPasivo(HashMap<String, Long> pasivo) {
		this.pasivo = pasivo;
	}


	public HashMap<String, Long> getPatrimonio() {
		return patrimonio;
	}


	public void setPatrimonio(HashMap<String, Long> patrimonio) {
		this.patrimonio = patrimonio;
	}


	private void iniciarActivo() {

		//ACTIVOS
		this.activo.put("efectivo", (long) 0.0);
		this.activo.put("inversiones_temporale", (long)0.0);
		this.activo.put("cuentas_cobrar_comerciales", (long) 0.0);
		this.activo.put("provision_cartera", (long) 0.0);
		this.activo.put("inventarios", (long) 0.0); 
		this.activo.put("anticipo_avances", (long) 0.0);
		this.activo.put("activos_biologicos", (long) 0.0);
		this.activo.put("anticipo_impuestos", (long) 0.0);
		this.activo.put("impuesto_diferido_activo", (long) 0.0);
		this.activo.put("ctas_cobrar_economicos", (long) 0.0);
		this.activo.put("activos_mant_venta", (long) 0.0);
		this.activo.put("activos_diferidos", (long) 0.0);
		this.activo.put("activos_derivados", (long) 0.0);
		this.activo.put("otros_activos", (long) 0.0);
		this.activo.put("terrenos", (long) 0.0);
		this.activo.put("construccion_proceso", (long) 0.0);
		this.activo.put("edificios_mejora", (long) 0.0);
		this.activo.put("maquinaria_equipo", (long) 0.0);
		this.activo.put("muebles_enseres", (long) 0.0);
		this.activo.put("equipo_transporte", (long) 0.0);
		this.activo.put("leasing", (long) 0.0);
		this.activo.put("otros_activos_fijos", (long) 0.0);
		this.activo.put("propiedades_inversion", (long) 0.0);
		this.activo.put("depreciacion_acumulada", (long) 0.0);
		this.activo.put("activos_biologicos_lp", (long) 0.0);
		this.activo.put("intangibles", (long) 0.0);
		this.activo.put("inversiones_permanentes", (long) 0.0);
		this.activo.put("efectivo_restringido", (long) 0.0);
		this.activo.put("ctas_cobrar_economicos_lp", (long) 0.0);
		this.activo.put("ctas_cobrar_lp", (long) 0.0);
		this.activo.put("impuestos_diferido_activo_lp", (long) 0.0);
		this.activo.put("activos_diferidos_lp", (long) 0.0);
		this.activo.put("activos_derivados_lp", (long) 0.0);
		this.activo.put("otros_activos_lp", (long) 0.0);
		this.activo.put("valorizacion_inversiones", (long) 0.0);
		this.activo.put("valorizacion_activos_fijos", (long) 0.0);	
		
		
		//PASIVOS
		this.pasivo.put("sobregiros", (long)0.0);
		this.pasivo.put("obligaciones_bancarias_CP", (long)0.0);
		this.pasivo.put("leasing_cp", (long)0.0);
		this.pasivo.put("particulares_cp", (long)0.0);
		this.pasivo.put("bonos_papeles_cciales_cp",(long) 0.0);
		this.pasivo.put("ctas_pagar_comerciales",(long) 0.0);
		this.pasivo.put("otras_ctas_pagar",(long) 0.0);
		this.pasivo.put("ctas_pagar_vinc_economicos",(long) 0.0);
		this.pasivo.put("obligaciones_laborales",(long) 0.0);
		this.pasivo.put("impuestos_pagar",(long) 0.0);
		this.pasivo.put("impuesto_diferido_pasivo",(long) 0.0);
		this.pasivo.put("dividendos_pagar",(long) 0.0);
		this.pasivo.put("pasivos_diferidos",(long) 0.0);
		this.pasivo.put("pasivos_estim_provisiones",(long) 0.0);
		this.pasivo.put("pasivos_derivados",(long) 0.0);
		this.pasivo.put("otros_pasivos",(long) 0.0);
		this.pasivo.put("obligaciones_bancarias_lp",(long) 0.0);
		this.pasivo.put("leasing_lp",(long) 0.0);
		this.pasivo.put("particulares_lp",(long) 0.0);
		this.pasivo.put("bonos_papeles_cciales_lp",(long) 0.0);
		this.pasivo.put("obligaciones_laborales_lp",(long) 0.0);
		this.pasivo.put("ctas_pagar_vinc_economicos_lp",(long) 0.0);
		this.pasivo.put("pasivos_diferidos_lp",(long) 0.0);
		this.pasivo.put("pasivos_estim_provisiones_lp",(long) 0.0);
		this.pasivo.put("pasivos_derivados_lp",(long) 0.0);
		this.pasivo.put("otros_pasivos_lp",(long) 0.0);
		this.pasivo.put("impuesto_diferido_pasivo_lp",(long) 0.0);
		
		
		//patrimomio
		this.patrimonio.put("capital",(long) 0.0);
		this.patrimonio.put("prima_colocacion_acciones",(long) 0.0);
		this.patrimonio.put("otros_superavit_capital",(long) 0.0);
		this.patrimonio.put("reserva_legal",(long) 0.0);
		this.patrimonio.put("otras_reservas",(long) 0.0);
		this.patrimonio.put("resultado_ejercicio",(long) 0.0);
		this.patrimonio.put("total_utilidades",(long) 0.0);
		this.patrimonio.put("otro_resultado_integral_acum_ori",(long) 0.0);
		this.patrimonio.put("valorizacion_activos_fijos_patrimonio",(long) 0.0);
		this.patrimonio.put("valorizacion_inversiones_patrimonio",(long) 0.0);
		this.patrimonio.put("otras_ctas_patrimoniales",(long) 0.0);
		this.patrimonio.put("revalorizacion_patrimonio",(long) 0.0);
		
		
		
		
		//PATRIMONIO
		
	}
	
	public void addCuenta(String key, Long val) {
		Long valores = (long)0.0;
		logger.debug("Ingresar: " + key + " - " + val);
		
		if(activo.get(key) != null) {
			valores = activo.get(key);
			valores += val;
			activo.put(key, valores);
		}else if(pasivo.get(key) != null) {
			valores = pasivo.get(key);
			valores += val;
			pasivo.put(key, valores);
		}else if(patrimonio.get(key)!= null){
			valores = patrimonio.get(key);
			valores += val;
			patrimonio.put(key, valores);
		}
		
		
	}

	@Override
	public String toString() {
		return "PlanCuenta [activo=" + activo + ", pasivo=" + pasivo + ", patrimonio=" + patrimonio + "]";
	}
	
	
	
}
