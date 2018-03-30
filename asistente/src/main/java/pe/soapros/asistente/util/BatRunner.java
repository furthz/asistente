package pe.soapros.asistente.util;

import java.io.File;
import java.io.OutputStreamWriter;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

public class BatRunner {
	
	public BatRunner() {
		String batfile = "clean.bat";
		String directory = "C:\\Users\\Furth\\git\\asistente\\asistente";
		String source = "D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\scripts\\prueba2.png";
		String dest = "D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\scripts\\prueba22222.png";
		try {
			runProcess(batfile, directory, source, dest);
		} catch (CommandLineException e) {
			e.printStackTrace();
		}
	}
	
public static void runProcess(String source, String dest) throws CommandLineException {
	
	String batfile = "clean.bat";
	String directory = "C:\\Users\\Furth\\git\\asistente\\asistente";
	
	BatRunner.runProcess(batfile, directory, source, dest);
	
}
	
private static void runProcess(String batfile, String directory, String source, String dest) throws CommandLineException {
		
		Commandline commandLine = new Commandline();
		
		File executable = new File(directory + File.separator +batfile);
		commandLine.setExecutable(executable.getAbsolutePath());
		String[] argumentos = new String[2];
		argumentos[0] = source;
		argumentos[1] = dest;
		commandLine.addArguments(argumentos);
		
		WriterStreamConsumer systemOut = new WriterStreamConsumer(
	            new OutputStreamWriter(System.out));
		
		WriterStreamConsumer systemErr = new WriterStreamConsumer(
	            new OutputStreamWriter(System.out));
 
		int returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (returnCode != 0) {
		    System.out.println("Something Bad Happened!");
		} else {
		    System.out.println("Taaa!! ddaaaaa!!");
		};
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new BatRunner();
	}

}
