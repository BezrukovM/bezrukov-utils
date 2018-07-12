package maksim.bezrukov.utils.filesfilter;

import com.beust.jcommander.Parameter;

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

	@Parameter(names = { HELP_FLAG, HELP }, description = "Shows this message and exits.", help = true)
	private boolean help = false;

	@Parameter(names = { RESULT_DIR_FLAG, RESULT_DIR }, description = "Root dir for filtered structure.", required = true)
	private String resultDirPath = null;

	@Parameter(description = "DIR", required = true)
	private String dir = null;

	public boolean isHelp() {
		return help;
	}

	public String getResultDirPath() {
		return resultDirPath;
	}

	public String getDir() {
		return dir;
	}
}
