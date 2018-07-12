package maksim.bezrukov.utils.filesfilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maksim Bezrukov
 */
public class Filter {

	private final File rootForFiltered;
	private final Map<String, File> dirByExt = new HashMap<>();

	public Filter(File rootForFiltered) {
		if (!rootForFiltered.isDirectory()) {
			throw new IllegalArgumentException("Root for filtered argument has to be an existing directory");
		}
		this.rootForFiltered = rootForFiltered;
	}

	public void filter(File dirToFilter) {
		if (!dirToFilter.isDirectory()) {
			throw new IllegalArgumentException("Dir to filter has to be an existing directory");
		}

		File[] files = dirToFilter.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					filter(f);
					if (!f.delete()) {
						f.deleteOnExit();
					}
				} else {
					filterFile(f);
				}
			}
		}
	}

	private void filterFile(File file) {
		String name = file.getName();
		int indexOfExt = name.lastIndexOf(".");
		String ext = indexOfExt < 0 ? null : name.substring(indexOfExt+1, name.length());
		File resDir = getDirForExt(ext);
		File newFile = getNewFilePath(resDir, name);
		try {
			Files.move(file.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getDirForExt(String ext) {
		String actualExt = ext == null || ext.isEmpty() ? "null" : ext.toLowerCase();
		if (!this.dirByExt.containsKey(ext)) {
			File dir = getDir(actualExt);
			this.dirByExt.put(actualExt, dir);
		}
		return this.dirByExt.get(actualExt);
	}

	private File getDir(String name) {
		File res = new File(this.rootForFiltered, name);
		if (!res.exists() && !res.mkdirs()) {
			throw new IllegalStateException("Can't create directory with path " + res.getAbsolutePath());
		} else if (res.isFile()) {
			throw new IllegalStateException("Path " + res.getAbsolutePath() + " already exists and isn't a directory");
		}
		return res;
	}

	private static File getNewFilePath(File dirTo, String currentName) {
		String name = currentName.startsWith(".") ? "hidden" + currentName : currentName;
		File res = new File(dirTo, name);
		int extStart = name.lastIndexOf(".");
		String nameWithoutExt = extStart < 0 ? name : name.substring(0, extStart);
		String ext = extStart < 0 ? "" : name.substring(extStart, name.length());
		long counter = 0;
		while (res.exists()) {
			res = new File(dirTo, nameWithoutExt + "-copy-" + String.valueOf(counter++) + ext);
		}
		return res;
	}
}
