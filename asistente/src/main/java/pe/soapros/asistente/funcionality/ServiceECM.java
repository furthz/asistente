package pe.soapros.asistente.funcionality;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Raúl Talledo
 * @version 1.0
 *
 */
public class ServiceECM {

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Return la ruta del archivo descargdo del ECM
	 * @param documentID
	 * @param fileName
	 * @param destinationFolder
	 * @return
	 */
	public String downloadFileById(String documentID, String fileName, String destinationFolder) {		

		String fullPath = destinationFolder + fileName;
		logger.debug("Ruta destino: " + fullPath);
		
		try {					

			Document newDocument = (Document) this.getCmisSession().getObject(documentID);
			logger.info("Documento encontrado: " + newDocument.getId());

			ContentStream cs = newDocument.getContentStream(null);
			
			BufferedInputStream in = new BufferedInputStream(cs.getStream());
			logger.debug("archivo bytes a escribir: " + in.available());
			
			FileOutputStream fos = new FileOutputStream(fullPath);
			logger.debug("archivo destino: " + fos.toString());
			
			OutputStream bufferedOutputStream = new BufferedOutputStream(fos);
			
			logger.debug("se empieza a escribir el archivo destino");
			byte[] buf = new byte[1024];
			int n = 0;
			while ((n = in.read(buf)) > 0) {
				bufferedOutputStream.write(buf, 0, n);
			}
			
			bufferedOutputStream.close();
			fos.close();
			in.close();
			
			logger.debug("Se finalizó la escritura del archivo destino");

		} catch (IOException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
		return fullPath;

	}

	/**
	 * 
	 * @param content
	 * @param metadata
	 * @param fileName
	 * @param mimeType
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException 
	 */
	public String uploadDocument (String path,  Map<String, Object> metadata)//(byte[] content, Map<String, Object> metadata, String fileName, String mimeType)
			throws UnsupportedEncodingException, FileNotFoundException {
		
		logger.debug("inicio subir archivos al ECM");
		

		/*
		 * Map<String, String> sessionParameters = new HashMap<String, String>();
		 * 
		 * sessionParameters.put(SessionParameter.USER, "admin");
		 * sessionParameters.put(SessionParameter.PASSWORD, "S0apros321");
		 * sessionParameters.put(SessionParameter.ATOMPUB_URL,
		 * "http://192.168.1.216:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom"
		 * ); sessionParameters.put(SessionParameter.BINDING_TYPE,
		 * BindingType.ATOMPUB.value());
		 * 
		 * SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		 * 
		 * Session lSession =
		 * sessionFactory.getRepositories(sessionParameters).get(0).createSession();
		 * 
		 */

		// obtener la session de conexión
		Session lSession = this.getCmisSession();
		logger.debug("Se inició la session");

		// obtener el sitio
		Folder folderSitio = this.getDocLibFolder(lSession, "finanzas");
		logger.debug("Se obtuvo el sitio creado para los estados financieros");

		// Folder f = (Folder)
		// lSession.getObjectByPath("/sites/finanzas/documentLibrary");
		String razonSocial = (String) metadata.get("estado_financiero:empresa");
		logger.debug("nombre de la empresa: " + razonSocial);

		//si no se logró obtener la empresa en los archivos a analizar
		if(razonSocial == null || razonSocial.equals("") ) {
			razonSocial = "Otros";
		}
		
		// crear una carpeta por empresa, en el sitio
		Folder newFolder = this.createFolderIfNotExists(lSession, folderSitio, razonSocial);
		logger.debug("Se creo la carpeta con el nombre de la emrpesa");

		/*
		 * Map<String, Object> folderProperties = new HashMap<String, Object>();
		 * folderProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		 * folderProperties.put(PropertyIds.NAME, "testfolder");
		 * 
		 * Folder newFolder = f.createFolder(folderProperties);
		 */

		// Map<String, Object> lProperties = new HashMap<String, Object>();
		// String name = "testdocument.txt";
		 //lProperties.put(PropertyIds.OBJECT_TYPE_ID, "D:estado_financiero:estado_financiero");
		// lProperties.put(PropertyIds.NAME, fileName);
		// lProperties.put("estado_financiero:razon_social", "probando");
		// byte[] content = "CMIS Testdata One".getBytes();
		File file = new File(path);
		logger.debug("archivo: " + file.getPath());
		
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		logger.debug("filemap: " + fileNameMap.toString());
		
		String mimetype = fileNameMap.getContentTypeFor(file.getName());
		logger.debug("mimetype: " + mimetype);
		
		//InputStream stream = new ByteArrayInputStream(content);
		FileInputStream fis = new FileInputStream(file);
		logger.debug("contenido del archivo en bytes");
		
		BigInteger bi = new BigInteger(file.length() + "");
		logger.debug("length: " + bi);
		
		ContentStream contentStream = new ContentStreamImpl(file.getName(), bi, mimetype, fis);
		logger.debug("Contenido a crear");
		
		metadata.put(PropertyIds.OBJECT_TYPE_ID, "D:estado_financiero:estado_financiero");
		metadata.put(PropertyIds.NAME, file.getName());
		logger.debug("metadata: " + metadata.toString());
        
		Document newContent1 = newFolder.createDocument(metadata, contentStream, null);
		logger.debug("Se creoó e documento dentro del ecm");
		
		logger.debug("Id del dcto: " + newContent1.getId());
		
		return newContent1.getId();
	}

	private Folder createFolderIfNotExists(Session cmisSession, Folder parentFolder, String folderName) {
		Folder subFolder = null;
		for (CmisObject child : parentFolder.getChildren()) {
			if (folderName.equalsIgnoreCase(child.getName())) {
				subFolder = (Folder) child;
			}
		}

		if (subFolder == null) {
			Map<String, Object> props = new HashMap<>();
			props.put("cmis:objectTypeId", "cmis:folder");
			props.put("cmis:name", folderName);

			subFolder = parentFolder.createFolder(props);
		}

		return subFolder;
	}

	/**
	 * 
	 * @param cmisSession
	 * @param siteName
	 * @return
	 */
	private Folder getDocLibFolder(Session cmisSession, String siteName) {
		String path = "/sites/" + siteName + "/documentLibrary";
		Folder folder = (Folder) cmisSession.getObjectByPath(path);
		return folder;
	}

	/**
	 * 
	 * @return
	 */
	private Session getCmisSession() {
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();

		Map<String, String> sessionParameters = new HashMap<String, String>();

		sessionParameters.put(SessionParameter.USER, "admin");
		sessionParameters.put(SessionParameter.PASSWORD, "S0apros321");
		sessionParameters.put(SessionParameter.ATOMPUB_URL,
				"http://148.102.51.17:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
		sessionParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		Session lSession = sessionFactory.getRepositories(sessionParameters).get(0).createSession();

		return lSession;

	}

}
