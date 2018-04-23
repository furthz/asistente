package pe.soapros.asistente.web;

import java.util.List;
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

		List<PlanCuenta> planes = (List<PlanCuenta>) model.get("planCuentas");

		HSSFSheet sheet = workbook.createSheet("Cuenta");
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

		// pasivo
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

		// patrimonio
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

		for (PlanCuenta plan : planes) {

			int col = 0;
			// for (PlanCuenta plan : plancuentas) {
			HSSFRow aRow = sheet.createRow(rowCount++);

			aRow.createCell(col).setCellValue(plan.getActivo().get("Efectivo"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Inversiones_Temporales"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Ctas_Cobrar_Comerciales"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Provision_Cartera"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Inventarios"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Anticipo_Avances"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Biologicos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Anticipo_Impuesto"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Impuesto_Diferido_Activo"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Ctas_Cobrar_Vinc_Economicos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Mant_Venta"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Diferidos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Derivados"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Otros_Activos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Terrenos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Construccion_Proceso"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Edificios_Mejoras"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Maquinaria_Equipo"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Muebles_Enseres"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Equipo_Transporte"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Leasing"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Otros_Activos_Fijos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Propiedades_Inversion"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Depreciacion_Acumulada"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Biologicos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Intangibles"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Inversiones_Permanentes"));
			col++;
			
			aRow.createCell(col).setCellValue(plan.getActivo().get("Efectivo_Restringido"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Ctas_Cobrar_Vinc_Economicos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Ctas_Cobrar_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Impuesto_Diferido_Activo_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Diferidos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Activos_Derivados_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Otros_Activos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Valorizacion_Inversiones"));
			col++;

			aRow.createCell(col).setCellValue(plan.getActivo().get("Valorizacion_Activos_Fijos"));
			col++;

			// PASIVO
			aRow.createCell(col).setCellValue(plan.getPasivo().get("Sobregiros"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Obligaciones_Bancarias_CP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Leasing_CP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Particulares_CP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Bonos_Papeles_Cciales_CP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Ctas_Pagar_Comerciales"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Otras_Ctas_Pagar"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Ctas_Pagar_Vinc_Economicos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Obligaciones_Laborales"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Impuestos_Pagar"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Impuesto_Diferido_Pasivo"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Dividendos_Pagar"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Diferidos"));
			col++;
			
			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Estim_Provisiones"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Derivados"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Otros_Pasivos"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Obligaciones_Bancarias_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Leasing_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Particulares_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Bonos_Papeles_Cciales_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Obligaciones_Laborales_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Ctas_Pagar_Vinc_Economicos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Diferidos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Estim_Provisiones_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Pasivos_Derivados_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Otros_Pasivos_LP"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPasivo().get("Impueso_Diferido_Pasivo_LP"));
			col++;

			// patrimonio
			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Capital"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Prima_Colocacion_Acciones"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Otros_Superavit_Capital"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Reserva_Legal"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Otras_Reservas"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Resultado_Ejercicio"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Total_Utilidades"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Otro_Resultado_Integral_Acum_ORI"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Valorizacion_Activos_Fijos_Patrimonio"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Valorizacion_Inversiones_Patrimonio"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Otras_Ctas_Patrimoniales"));
			col++;

			aRow.createCell(col).setCellValue(plan.getPatrimonio().get("Revalorizacion_Patrimonio"));
			col++;

		}
		// }

	}

}
