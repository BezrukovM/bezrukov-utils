package maksim.bezrukov.utils.files.common;

import com.beust.jcommander.Parameter;

/**
 * @author Maksim Bezrukov
 */
public class CommonArgParser {
	protected final static String FLAG_SEP = "-";
	protected final static String OPTION_SEP = "--";
	private final static String HELP_FLAG = FLAG_SEP + "h";
	private final static String HELP = OPTION_SEP + "help";

	@Parameter(names = { HELP_FLAG, HELP }, description = "Shows this message and exits.", help = true)
	private boolean help = false;

	@Parameter(description = "DIR", required = true)
	private String dir = null;

	public boolean isHelp() {
		return help;
	}

	public String getDir() {
		return dir;
	}
}
