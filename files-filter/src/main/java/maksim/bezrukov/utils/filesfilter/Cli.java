package maksim.bezrukov.utils.filesfilter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * @author Maksim Bezrukov
 */
public class Cli {

	public static void main(String[] args) {
		FilterArgParser cliArgParser = new FilterArgParser();
		JCommander jCommander = new JCommander(cliArgParser);
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
		Filter filter = new Filter(resDir);
		filter.filter(dirToFilter);
	}

	private static void displayHelpAndExit(JCommander jCommander, int i) {
		jCommander.usage();
		System.exit(i);
	}
}