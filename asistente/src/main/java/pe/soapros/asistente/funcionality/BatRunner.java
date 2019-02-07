package pe.soapros.asistente.funcionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;
import org.springframework.stereotype.Controller;

import pe.soapros.asistente.domain.Propiedades;
import pe.soapros.asistente.util.Util;

@Controller
public class BatRunner {

	private Propiedades propiedades;

	protected final Log logger = LogFactory.getLog(getClass());

	public BatRunner() {

		logger.debug("Inicio BatRunner");
		
		Propiedades pr = new Propiedades();
		pr.setBatchName("clean.bat");
		pr.setBatchPath("G:\\Mi unidad\\Fuentes\\Java\\AsistenteFinanciero\\asistente\\asistente");
		pr.setWindows("1");
		
		this.propiedades = pr;
	}

	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}

	public void runProcess(String source, String dest) throws CommandLineException, IOException {

		logger.debug("runProcess");
		logger.debug("Batch: " + this.propiedades);

		String batfile = this.propiedades.getBatchName();// "clean.bat";		
		String directory = this.propiedades.getBatchPath(); // "C:\\Users\\Furth\\git\\asistente\\asistente";
		
		try {
			this.runProcess(batfile, directory, source, dest);
		}
		catch(Exception e) {
			System.out.println(e);
		}

	}

	private void runProcess(String batfile, String directory, String source, String dest) throws CommandLineException, IOException {

		if (this.propiedades.getWindows().equals("1")) {
			Commandline commandLine = new Commandline();

			File executable = new File(directory + File.separator + batfile);
			commandLine.setExecutable(executable.getAbsolutePath());
			String[] argumentos = new String[2];
			argumentos[0] = source;
			argumentos[1] = dest;
			logger.debug("Argumentos Batch: " + argumentos);

			commandLine.addArguments(argumentos);
			logger.debug("Se inserta los argumentos");

			WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));
			logger.debug("se crea el llamado");

			WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.out));
			logger.debug("se crea el llamado para el error");

			int returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
			logger.debug("Resultado Ejecutar Batch: " + returnCode);

			if (returnCode != 0) {
				System.out.println("Ocurrió un error");
			}
		} else {
			logger.debug("SO Linux");
			
			//Process procBuildScript = new ProcessBuilder(directory + File.separator + batfile, source + " " + dest).start();
			logger.debug("Proceso Batch Llamado: " + directory + File.separator + batfile + " Parametros: " + source + " " + dest);
			
			ProcessBuilder pb = new ProcessBuilder(directory + File.separator + batfile,  source, dest);
			logger.debug("se inicio el proceso");
			
			Process p = pb.start();
			logger.debug("se lanzó el proceso");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			logger.debug("se lee el mensaje");
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				logger.debug("resultado proceso: " + line);
				//System.out.println(line);
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws CommandLineException
	 */
	public static void main(String[] args) throws IOException, CommandLineException {
		// new BatRunner();
		
		Propiedades pr = new Propiedades();
		pr.setBatchName("clean.bat");
		pr.setBatchPath("G:\\Mi unidad\\Fuentes\\Java\\AsistenteFinanciero\\asistente\\asistente");
		pr.setWindows("1");
		
		
		List<Path> archivos = Util.listarFicheros("C:\\Users\\User\\Desktop\\POC EEFF", "jpg");
		
		 for (Path p : archivos) {
			 BatRunner bat = new BatRunner();
			 bat.setPropiedades(pr);
			 bat.runProcess(p.toString(), p.toString());
		 }
	}

}
