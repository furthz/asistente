package pe.soapros.asistente.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.funcionality.NotaNLU;
import pe.soapros.asistente.funcionality.PlanCuentaNLU;
import pe.soapros.asistente.funcionality.ServiceECM;
import pe.soapros.asistente.service.TipoDocumentoManager;
import pe.soapros.asistente.util.Util;

@Controller
public class ResultadoWithNotasController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Propiedades propiedades; // = new Propiedades();

	private String pathfile = "";

	private List<PlanCuenta> cuentas;

	// @Autowired
	// private FileUploadManager fileManager;

	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;

	@RequestMapping(value = "/resultadosNotas.htm")
	public ModelAndView handleRequest(@RequestParam("id") Long id) throws ServletException, IOException {

		logger.debug("Carpeta temporal: " + propiedades.getTemporal());

		this.pathfile = propiedades.getTemporal();

		List<List<TipoDocumento>> ejes = new ArrayList<List<TipoDocumento>>();

		List<TipoDocumento> lstFiles = null;

		lstFiles = this.tipoDocumentoManager.getTiposDocumentosById(id);
		ejes.add(lstFiles);

		ServiceECM service = new ServiceECM();
		service.setPropiedades(this.propiedades);

		Path basePath = Paths.get(this.pathfile);
		Path path = Files.createTempDirectory(basePath, "download");

		String contenido = "";
		String ruta = "";

		String contenidoNotas = "";

		String rutaImagen = "";

		for (TipoDocumento tipo : lstFiles) {
			if (tipo.getTipoDoc().trim().equals("Balance")) {

				ruta = service.downloadFileById(tipo.getObjectIdTxt().trim(), tipo.getFilenameTxt().trim(),
						path.toString().trim());
				logger.debug("ruta : " + ruta);

				rutaImagen = service.downloadFileById(tipo.getObjectId().trim(), tipo.getFilename().trim(),
						path.toString().trim());
				logger.debug("ruta imagen: " + rutaImagen);

				contenido += Util.leerArchivoTXT(ruta);

			} else if (tipo.getTipoDoc().trim().equals("Notas")) {

				ruta = service.downloadFileById(tipo.getObjectIdTxt().trim(), tipo.getFilenameTxt().trim(),
						path.toString().trim());
				logger.debug("ruta : " + ruta);

				contenidoNotas += Util.leerArchivoTXT(ruta);

			}
		}

		File file = new File(rutaImagen);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int b;
		byte[] buffer = new byte[1024];
		while ((b = fis.read(buffer)) != -1) {
			bos.write(buffer, 0, b);
		}
		byte[] fileBytes = bos.toByteArray();
		fis.close();
		bos.close();

		byte[] encoded = Base64.encodeBase64(fileBytes);
		String encodedString = new String(encoded);

		//
		PlanCuentaNLU plan = new PlanCuentaNLU();
		plan.setPropiedades(this.propiedades);

		NotaNLU notas = new NotaNLU();
		PlanCuenta pnotas = new PlanCuenta();

		PlanCuenta planc = new PlanCuenta();

		if (!"".equals(contenido)) {
			planc = plan.consultarCuentas(contenido, true);
		}

		if (!"".equals(contenidoNotas)) {
			pnotas = notas.consultarNotas(contenidoNotas);
		}

		// agregar las cuentas detalladas de las notas. se pueden duplicar si ya
		// existen.
		for (Entry<String, Double> elem : pnotas.getActivo().entrySet()) {

			// verificar que sean del activo fijo
			if (elem.getKey().equals("Terrenos") || elem.getKey().equals("Construccion_Proceso")
					|| elem.getKey().equals("Maquinaria_Equipo") || elem.getKey().equals("Muebles_Enseres")
					|| elem.getKey().equals("Equipo_Transporte")) {

				Double valor = planc.getActivo().get(elem.getKey());

				// caso en el que el valor a ingresar de las notas sea igual al que ya exista,
				// en ese caso no se agrega
				if (!valor.equals(elem.getValue())) {
					valor = valor + elem.getValue();
					planc.getActivo().put(elem.getKey(), valor);

					Double valMenos = planc.getActivo().get("Edificios_Mejoras");
					valMenos = valMenos - valor;
					planc.getActivo().put("Edificios_Mejoras", valMenos);
				}
			}else {
				//aqui el otro lote de las reglas ##OJO
			}

		}

		cuentas = new ArrayList<PlanCuenta>();

		cuentas.add(planc);

		HashMap<String, Object> objetos = new HashMap<String, Object>();
		objetos.put("plan", cuentas);
		objetos.put("imagen", encodedString);
		objetos.put("json", cuentas.get(0).getJSON());

		return new ModelAndView("resultados", "objetos", objetos);

	}
}
