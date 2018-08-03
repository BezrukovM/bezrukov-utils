package maksim.bezrukov.utils.files.doctopdf;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import maksim.bezrukov.utils.files.Cli;

import java.io.File;

/**
 * @author Maksim Bezrukov
 */
public class DocToPdfCli {

	public static void main(String[] args) {
		DocToPdfArgParser cliArgParser = new DocToPdfArgParser();
		JCommander jCommander = new JCommander(cliArgParser);
		jCommander.setProgramName("jarFile " + Cli.Module.DOC_TO_PDF.getName());
		try {
			jCommander.parse(args);
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			displayHelpAndExit(jCommander, 1);
		}
		if (cliArgParser.isHelp()) {
			displayHelpAndExit(jCommander, 0);
		}

		File resDir = new File(cliArgParser.getResultDirPath());
		if (!resDir.isDirectory()) {
			System.err.println("Specified filtered directory path either doesn't exist or is not a directory.");
			displayHelpAndExit(jCommander, 1);
		}
		File dirToFilter = new File(cliArgParser.getDir());
		if (!dirToFilter.isDirectory()) {
			System.err.println("Specified directory for filtering path either doesn't exist or is not a directory.");
			displayHelpAndExit(jCommander, 1);
		}
		OpenOfficeUtil.officeDirectory = cliArgParser.getOfficeDir();
		Converter converter = new Converter(resDir);
		long converted = converter.convert(dirToFilter);
		System.out.println("Total files converted: " + converted);
	}

	private static void displayHelpAndExit(JCommander jCommander, int i) {
		jCommander.usage();
		System.exit(i);
	}
}
