package maksim.bezrukov.utils.files.doctopdf;

import com.beust.jcommander.Parameter;
import maksim.bezrukov.utils.files.common.CommonArgParser;

/**
 * @author Maksim Bezrukov
 */
class DocToPdfArgParser extends CommonArgParser {

	private final static String RESULT_DIR_FLAG = FLAG_SEP + "rd";
	private final static String RESULT_DIR = OPTION_SEP + "resultDir";
	private final static String OFFICE_DIR_FLAG = FLAG_SEP + "od";
	private final static String OFFICE_DIR = OPTION_SEP + "officeDir";

	@Parameter(names = { OFFICE_DIR_FLAG, OFFICE_DIR }, description = "Open Office directory.", required = true)
	private String officeDir = null;

	@Parameter(names = { RESULT_DIR_FLAG, RESULT_DIR }, description = "Root dir for converted structure.", required = true)
	private String resultDirPath = null;

	String getOfficeDir() {
		return officeDir;
	}

	String getResultDirPath() {
		return resultDirPath;
	}
}
