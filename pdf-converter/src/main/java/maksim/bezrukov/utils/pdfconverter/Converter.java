package maksim.bezrukov.utils.pdfconverter;

import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.uno.Exception;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Maksim Bezrukov
 */
public class Converter {

	private final File rootForFiltered;

	public Converter(File rootForFiltered) {
		if (!rootForFiltered.isDirectory()) {
			throw new IllegalArgumentException("Root for filtered argument has to be an existing directory");
		}
		this.rootForFiltered = rootForFiltered;
	}

	public long convert(File dirToConvert) {
		return convert(dirToConvert, this.rootForFiltered);
	}

	private long convert(File dirToConvert, File pdfDir) {
		long res = 0;
		if (!dirToConvert.isDirectory()) {
			throw new IllegalArgumentException("Dir to filter has to be an existing directory");
		}

		File[] files = dirToConvert.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					File similarPDFDir = new File(pdfDir, f.getName());
					if (!similarPDFDir.exists() && !similarPDFDir.mkdirs()) {
						throw new IllegalStateException("Can't create dir " + similarPDFDir.getAbsolutePath());
					}
					res += convert(f, similarPDFDir);
				} else if (OpenOfficeUtil.isSupportedExtension(f.getName()) && convertFile(f, pdfDir) != null) {
					++res;
				}
			}
		}
		return res;
	}

	private File convertFile(File document, File destination) {
		final String pdfFilename = getPdfFilename(document.getName());
		File pdfFile = new File(destination, pdfFilename);
		if (!pdfFile.exists()) {
			try {
				OpenOfficeUtil.convert(document, pdfFile);
				if (pdfFile.exists()) {
					return pdfFile;
				}
			} catch (BootstrapException | MalformedURLException | InterruptedException | Exception e) {
				System.err.println("Problem during converting file " + document.getAbsolutePath());
				e.printStackTrace();
			}
		} else {
			System.out.println("File " + pdfFile.getAbsolutePath() + " already exists");
		}
		return null;
	}

	private String getPdfFilename(String documentName) {
		return documentName.substring(0, documentName.lastIndexOf(".")) + ".pdf";
	}
}
