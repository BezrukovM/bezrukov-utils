package maksim.bezrukov.utils.files.extfilter;

import com.beust.jcommander.Parameter;
import maksim.bezrukov.utils.files.common.CommonArgParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maksim Bezrukov
 */
class ExtFilterArgParser extends CommonArgParser {

	private final static String RESULT_DIR_FLAG = FLAG_SEP + "rd";
	private final static String RESULT_DIR = OPTION_SEP + "resultDir";
	private final static String EXTENSION_FLAG = FLAG_SEP + "ext";
	private final static String EXTENSION = OPTION_SEP + "extension";
	private final static String EXCLUDE_EXTENSION_FLAG = FLAG_SEP + "eext";
	private final static String EXCLUDE_EXTENSION = OPTION_SEP + "excludeExtension";

	@Parameter(names = { RESULT_DIR_FLAG, RESULT_DIR }, description = "Root dir for filtered structure.", required = true)
	private String resultDirPath = null;

	@Parameter(names = { EXTENSION_FLAG, EXTENSION }, description = "Extensions to move.")
	private List<String> extensions = new ArrayList<>();

	@Parameter(names = { EXCLUDE_EXTENSION_FLAG, EXCLUDE_EXTENSION }, description = "Extensions to not move.")
	private List<String> excludeExtensions = new ArrayList<>();

	String getResultDirPath() {
		return resultDirPath;
	}

	List<String> getExtensions() {
		return extensions;
	}

	List<String> getExcludeExtensions() {
		return excludeExtensions;
	}
}
