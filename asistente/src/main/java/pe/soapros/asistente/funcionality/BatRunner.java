package pe.soapros.asistente.funcionality;

import java.io.File;
import java.io.IOException;
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
		
//		String batfile = propiedades.getBatchName();// "clean.bat";
//		String directory = propiedades.getBatchPath();// "C:\\Users\\Furth\\git\\asistente\\asistente";
//		logger.debug("BatFile: " + batfile);
//		logger.debug("directory: " + directory);

		// String source = "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\scripts\\prueba2.png";
		// String dest = "D:\\Documents\\Proyectos\\Bancolombia\\Asistente
		// Financiero\\EEFF\\scripts\\prueba22222.png";
		// try {
		// runProcess(batfile, directory, source, dest);
		// } catch (CommandLineException e) {
		// e.printStackTrace();
		// }
	}

	
	public Propiedades getPropiedades() {
		return propiedades;
	}


	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}


	public void runProcess(String source, String dest) throws CommandLineException {

		logger.debug("runProcess");
		logger.debug("Batch: " + this.propiedades);
		
		String batfile = this.propiedades.getBatchName();// "clean.bat";
		String directory = this.propiedades.getBatchPath(); // "C:\\Users\\Furth\\git\\asistente\\asistente";

		this.runProcess(batfile, directory, source, dest);

	}

	private void runProcess(String batfile, String directory, String source, String dest) throws CommandLineException {

		Commandline commandLine = new Commandline();

		File executable = new File(directory + File.separator + batfile);
		commandLine.setExecutable(executable.getAbsolutePath());
		String[] argumentos = new String[2];
		argumentos[0] = source;
		argumentos[1] = dest;
		commandLine.addArguments(argumentos);

		WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));

		WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.out));

		int returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (returnCode != 0) {
			System.out.println("Ocurrió un error");
		} /*
			 * else { System.out.println("Todo funcionó"); };
			 */
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws CommandLineException
	 */
	public static void main(String[] args) throws IOException, CommandLineException {
		// new BatRunner();
		List<Path> archivos = Util.listarFicheros(
				"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\SOA\\seleccionado\\destino\\Fase2",
				"png");

		//for (Path p : archivos) {
			// runProcess(p.toString(), p.toString());
		//}
	}

}
