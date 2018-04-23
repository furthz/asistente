package pe.soapros.asistente.web;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.soapros.asistente.domain.PlanCuenta;
import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.domain.TipoDocumento;
import pe.soapros.asistente.domain.UploadFile;
import pe.soapros.asistente.funcionality.PlanCuentaNLU;
import pe.soapros.asistente.funcionality.ServiceECM;
import pe.soapros.asistente.service.FileUploadManager;
import pe.soapros.asistente.service.TipoDocumentoManager;
import pe.soapros.asistente.util.Util;

@Controller
public class ResultadoController {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Propiedades propiedades; // = new Propiedades();

	private String pathfile = "";

	private List<PlanCuenta> cuentas;

	@Autowired
	private FileUploadManager fileManager;

	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;

	@RequestMapping(value = "/resultados.htm")
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

		cuentas = new ArrayList<PlanCuenta>();
		
		cuentas.add(plan.consultarCuentas(contenido));

		HashMap<String, Object> objetos = new HashMap<String, Object>();
		objetos.put("plan", cuentas);
		objetos.put("imagen", encodedString);

		return new ModelAndView("resultados", "objetos", objetos);
	}

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public ModelAndView downloadExcel() {

		return new ModelAndView("excelView", "planCuentas", this.cuentas);
	}

	@RequestMapping(value = "/downloadExcelAll", method = RequestMethod.GET)
	public ModelAndView downloadExcelAll() throws IOException {

		this.pathfile = propiedades.getTemporal();
		Path basePath = Paths.get(this.pathfile);
		Path path = Files.createTempDirectory(basePath, "download");
		
		ServiceECM service = new ServiceECM();
		service.setPropiedades(this.propiedades);
		
		PlanCuentaNLU plan = new PlanCuentaNLU();
		plan.setPropiedades(this.propiedades);
		
		List<List<TipoDocumento>> ejes = new ArrayList<List<TipoDocumento>>();

		List<UploadFile> ejecuciones = this.fileManager.getFilesWithSoons();
		
		List<PlanCuenta> planes = new ArrayList<PlanCuenta>();

		for (UploadFile f : ejecuciones) {
			List<TipoDocumento> temp = this.tipoDocumentoManager.getTiposDocumentosById(f.getId());
			ejes.add(temp);
		}
		
		for(List<TipoDocumento> lst: ejes) {
			String contenido = "";
			String ruta = "";
			String rutaImagen = "";

			for (TipoDocumento tipo : lst) {
				if (tipo.getTipoDoc().trim().equals("Balance")) {

					ruta = service.downloadFileById(tipo.getObjectIdTxt().trim(), tipo.getFilenameTxt().trim(),
							path.toString().trim());
					logger.debug("ruta : " + ruta);				

					contenido += Util.leerArchivoTXT(ruta);
				}
			}
			
			PlanCuenta planc = plan.consultarCuentas(contenido);
			
			planes.add(planc);
		}

		return new ModelAndView("excelView", "planCuentas", planes);
	}

}
