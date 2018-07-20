package maksim.bezrukov.utils.filesfilter;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maksim Bezrukov
 */
public class FilterArgParser {

	private final static String FLAG_SEP = "-";
	private final static String OPTION_SEP = "--";
	private final static String HELP_FLAG = FLAG_SEP + "h";
	private final static String HELP = OPTION_SEP + "help";
	private final static String RESULT_DIR_FLAG = FLAG_SEP + "rd";
	private final static String RESULT_DIR = OPTION_SEP + "resultDir";
	private final static String EXTENSION_FLAG = FLAG_SEP + "ext";
	private final static String EXTENSION = OPTION_SEP + "extension";
	private final static String EXCLUDE_EXTENSION_FLAG = FLAG_SEP + "eext";
	private final static String EXCLUDE_EXTENSION = OPTION_SEP + "excludeExtension";

	@Parameter(names = { HELP_FLAG, HELP }, description = "Shows this message and exits.", help = true)
	private boolean help = false;

	@Parameter(names = { RESULT_DIR_FLAG, RESULT_DIR }, description = "Root dir for filtered structure.", required = true)
	private String resultDirPath = null;

	@Parameter(names = { EXTENSION_FLAG, EXTENSION }, description = "Extensions to move.")
	private List<String> extensions = new ArrayList<>();

	@Parameter(names = { EXCLUDE_EXTENSION_FLAG, EXCLUDE_EXTENSION }, description = "Extensions to not move.")
	private List<String> excludeExtensions = new ArrayList<>();

	@Parameter(description = "DIR", required = true)
	private String dir = null;

	public boolean isHelp() {
		return help;
	}

	public String getResultDirPath() {
		return resultDirPath;
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public List<String> getExcludeExtensions() {
		return excludeExtensions;
	}

	public String getDir() {
		return dir;
	}
}
