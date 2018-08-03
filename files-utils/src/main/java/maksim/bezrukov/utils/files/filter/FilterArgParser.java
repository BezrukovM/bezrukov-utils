package maksim.bezrukov.utils.files.filter;

import com.beust.jcommander.Parameter;
import maksim.bezrukov.utils.files.common.CommonArgParser;

/**
 * @author Maksim Bezrukov
 */
class FilterArgParser extends CommonArgParser {

	private final static String RESULT_DIR_FLAG = FLAG_SEP + "rd";
	private final static String RESULT_DIR = OPTION_SEP + "resultDir";
	private final static String RENAME = OPTION_SEP + "rename";

	@Parameter(names = { RESULT_DIR_FLAG, RESULT_DIR }, description = "Root dir for copied files.", required = true)
	private String resultDirPath = null;

	@Parameter(names = {RENAME}, description = "Rename files to their sha1 with extension.")
	private boolean rename = false;

	String getResultDirPath() {
		return resultDirPath;
	}

	boolean isRename() {
		return rename;
	}
}
