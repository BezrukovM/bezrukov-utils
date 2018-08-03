package maksim.bezrukov.utils.files.filter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import maksim.bezrukov.utils.files.Cli;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Maksim Bezrukov
 */
public class FilterCli {

	public static void main(String[] args) {
		FilterArgParser cliArgParser = new FilterArgParser();
		JCommander jCommander = new JCommander(cliArgParser);
		jCommander.setProgramName("jarFile " + Cli.Module.FILTER.getName());
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
			System.err.println("Specified result directory path either doesn't exist or is not a directory.");
			displayHelpAndExit(jCommander, 1);
		}
		File dirToFilter = new File(cliArgParser.getDir());
		if (!dirToFilter.isDirectory()) {
			System.err.println("Specified directory for filtering path either doesn't exist or is not a directory.");
			displayHelpAndExit(jCommander, 1);
		}

		Filter filter = new Filter(resDir);
		filter.filter(dirToFilter, cliArgParser.isRename());
	}

	private static void displayHelpAndExit(JCommander jCommander, int i) {
		jCommander.usage();
		System.exit(i);
	}
}
