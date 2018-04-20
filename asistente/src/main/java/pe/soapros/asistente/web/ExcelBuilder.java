package pe.soapros.asistente.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import pe.soapros.asistente.domain.PlanCuenta;

public class ExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		PlanCuenta plan = (PlanCuenta) model.get("planCuentas");

		HSSFSheet sheet = workbook.createSheet("Java Books");
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		int i = 0;
		// create header row
		HSSFRow header = sheet.createRow(0);

		header.createCell(i).setCellValue("Efectivo");
		header.getCell(i).setCellStyle(style);
		i++;

		header.createCell(i).setCellValue("Inversiones Temporales");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x cobrar comerciales");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Provisión cartera (-)");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Inventarios");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Anticipo y avances");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos biológicos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Anticipo de impuesto");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Impuesto diferido activo");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x cobrar vinc económicos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos mant para la venta");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos diferidos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos derivados");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros activos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Terrenos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Construcción en proceso");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Edificios y mejoras");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Maquinaria y equipo");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Muebles y enseres");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Equipo de transporte");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Leasing");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros activos fijos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Propiedades de Inversión");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Depreciación acumulada");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos biológicos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Intangibles");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Inversiones Permanentes");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Efectivo restringido");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x cobrar vinc económicos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x cobrar LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Impuesto diferido activo LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos diferidos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Activos derivados LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros activos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Valorización de inversiones");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Valorización de activos fijos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		//pasivo
		header.createCell(i).setCellValue("Sobregiros");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Obligaciones bancarias CP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Leasing CP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Particulares CP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Bonos y papeles cciales CP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x pagar comerciales");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otras ctas x pagar");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x pagar vinc económicos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Obligaciones laborales");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Impuestos x pagar");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Impuesto diferido pasivo");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Dividendos x pagar");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Pasivos diferidos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		
		header.createCell(i).setCellValue("Pasivos estim y provisiones");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Pasivos derivados");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros pasivos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Obligaciones bancarias LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Leasing LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Particulares LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Bonos y papeles cciales LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Obligaciones laborales LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Ctas x pagar vinc económicos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Pasivos diferidos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Pasivos estim y provisiones LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Pasivos derivados LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros pasivos LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Impueso diferido pasivo LP");
		header.getCell(i).setCellStyle(style);
		i++;
		
		//patrimonio
		header.createCell(i).setCellValue("Capital");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Prima en colocación de acciones");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otros superávit de capital");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Reserva Legal");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otras reservas");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Resultado del ejercicio");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Total utilidades");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otro resultado integral acum ORI");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Valorización de activos fijos");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Valorización de inversiones");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Otras ctas patrimoniales");
		header.getCell(i).setCellStyle(style);
		i++;
		
		header.createCell(i).setCellValue("Revalorización del patrimonio");
		header.getCell(i).setCellStyle(style);
		i++;
		
		
		// create data rows
        int rowCount = 1;
        int col = 0; 
        //for (PlanCuenta plan : plancuentas) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("efectivo"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("inversiones_temporale"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("cuentas_cobrar_comerciales"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("provision_cartera"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("inventarios"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("anticipo_avances"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_biologicos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("anticipo_impuestos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("impuesto_diferido_activo"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("ctas_cobrar_economicos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_mant_venta"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_diferidos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_derivados"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("otros_activos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("terrenos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("construccion_proceso"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("edificios_mejora"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("maquinaria_equipo"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("muebles_enseres"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("equipo_transporte"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("leasing"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("otros_activos_fijos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("propiedades_inversion"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("depreciacion_acumulada"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_biologicos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("intangibles"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("efectivo_restringido"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("ctas_cobrar_economicos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("ctas_cobrar_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("impuestos_diferido_activo_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_diferidos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("activos_derivados_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("otros_activos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("valorizacion_inversiones"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getActivo().get("valorizacion_activos_fijos"));
            col++;
            
            //PASIVO
            aRow.createCell(col).setCellValue(plan.getPasivo().get("sobregiros"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("obligaciones_bancarias_CP"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("leasing_cp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("particulares_cp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("bonos_papeles_cciales_cp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("ctas_pagar_comerciales"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("otras_ctas_pagar"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("ctas_pagar_vinc_economicos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("obligaciones_laborales"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("impuestos_pagar"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("impuesto_diferido_pasivo"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("dividendos_pagar"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("pasivos_diferidos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("pasivos_derivados"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("otros_pasivos"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("obligaciones_bancarias_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("leasing_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("particulares_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("bonos_papeles_cciales_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("obligaciones_laborales_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("ctas_pagar_vinc_economicos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("pasivos_diferidos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("pasivos_estim_provisiones_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("pasivos_derivados_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("otros_pasivos_lp"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPasivo().get("impuesto_diferido_pasivo_lp"));
            col++;
            
            //patrimonio
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("capital"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("prima_colocacion_acciones"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("otros_superavit_capital"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("reserva_legal"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("otras_reservas"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("resultado_ejercicio"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("total_utilidades"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("otro_resultado_integral_acum_ori"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("valorizacion_activos_fijos_patrimonio"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("valorizacion_inversiones_patrimonio"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("otras_ctas_patrimoniales"));
            col++;
            
            aRow.createCell(col).setCellValue(plan.getPatrimonio().get("revalorizacion_patrimonio"));
            col++;
            	
            
        //}
        

	}

}
