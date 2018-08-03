package maksim.bezrukov.utils.files.extfilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Maksim Bezrukov
 */
class ExtFilter {

	private final File rootForFiltered;
	private final Set<String> extensions;
	private final Set<String> excludeExtensions;
	private final Map<String, File> dirByExt = new HashMap<>();

	ExtFilter(File rootForFiltered, Set<String> extensions, Set<String> excludeExtensions) {
		if (!rootForFiltered.isDirectory()) {
			throw new IllegalArgumentException("Root for filtered argument has to be an existing directory");
		}
		this.rootForFiltered = rootForFiltered;
		this.extensions = extensions == null ? Collections.emptySet() : Collections.unmodifiableSet(extensions);
		this.excludeExtensions = excludeExtensions == null ? Collections.emptySet() : Collections.unmodifiableSet(excludeExtensions);
	}

	void filter(File dirToFilter) {
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
		String ext = indexOfExt > 0 && indexOfExt < name.length()-1 ? name.substring(indexOfExt + 1).toLowerCase() : "null";
		if ((extensions.isEmpty() || extensions.contains(ext)) && !excludeExtensions.contains(ext)) {
			File resDir = getDirForExt(ext);
			File newFile = getNewFilePath(resDir, name);
			try {
				Files.move(file.toPath(), newFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (!file.delete()) {
			file.deleteOnExit();
		}
	}

	private File getDirForExt(String ext) {
		if (!this.dirByExt.containsKey(ext)) {
			File dir = getDir(ext);
			this.dirByExt.put(ext, dir);
		}
		return this.dirByExt.get(ext);
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
		String ext = extStart < 0 ? "" : name.substring(extStart);
		long counter = 0;
		while (res.exists()) {
			res = new File(dirTo, nameWithoutExt + "-copy-" + String.valueOf(counter++) + ext);
		}
		return res;
	}
}
