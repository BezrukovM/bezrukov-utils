package maksim.bezrukov.utils.files.extfilter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import maksim.bezrukov.utils.files.Cli;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Maksim Bezrukov
 */
public class ExtFilterCli {

	public static void main(String[] args) {
		ExtFilterArgParser cliArgParser = new ExtFilterArgParser();
		JCommander jCommander = new JCommander(cliArgParser);
		jCommander.setProgramName("jarFile " + Cli.Module.EXT_FILTER.getName());
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
		Set<String> extensions = cliArgParser.getExtensions().stream().map(String::toLowerCase).collect(Collectors.toSet());
		Set<String> excludeExtensions = cliArgParser.getExcludeExtensions().stream().map(String::toLowerCase).collect(Collectors.toSet());
		ExtFilter extFilter = new ExtFilter(resDir, extensions, excludeExtensions);
		extFilter.filter(dirToFilter);
	}

	private static void displayHelpAndExit(JCommander jCommander, int i) {
		jCommander.usage();
		System.exit(i);
	}
}
