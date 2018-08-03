package maksim.bezrukov.utils.files.filter;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Maksim Bezrukov
 */
class Filter {

	private final File rootForFiltered;
	private Map<String, File> filesMap = new HashMap<>();

	Filter(File rootForFiltered) {
		if (!rootForFiltered.isDirectory()) {
			throw new IllegalArgumentException("Root for filtered argument has to be an existing directory");
		}
		this.rootForFiltered = rootForFiltered;
	}

	int filter(File dirToFilter, boolean rename) {
		if (!dirToFilter.isDirectory()) {
			throw new IllegalArgumentException("Dir to filter has to be an existing directory");
		}
		this.filesMap.clear();

		File[] files = dirToFilter.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					filter(f, rename);
				} else {
					try {
						filterFile(f, rename);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return this.filesMap.size();
	}

	private void filterFile(File file, boolean rename) throws IOException {
		String sha1 = getSha1(file);
		if (filesMap.containsKey(sha1)) {
			File oldFile = filesMap.get(sha1);
			if (oldFile.length() != file.length()) {
				System.out.println("Two files with different length produce the same sha1: " + sha1);
				System.out.println("Old file " + oldFile.getAbsolutePath());
				System.out.println("New file " + file.getAbsolutePath());
			}
		} else {
			String newName = rename ? sha1 : null;
			File dest = getDestFile(file, newName);
			Files.copy(file.toPath(), dest.toPath());
			filesMap.put(sha1, dest);
		}
	}

	private static String getSha1(File file) throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return DigestUtils.sha1Hex(fileInputStream);
		}
	}

	private File getDestFile(File file, String newName) {
		String currentName = file.getName();
		String name = currentName.startsWith(".") ? "hidden" + currentName : currentName;
		int extStart = name.lastIndexOf(".");
		String nameWithoutExt = extStart < 0 ? name : name.substring(0, extStart);
		String ext = extStart < 0 ? "" : name.substring(extStart);
		String resName = newName == null ? nameWithoutExt : newName;

		File res = new File(this.rootForFiltered, resName + ext);
		long counter = 0;
		while (res.exists()) {
			res = new File(this.rootForFiltered, resName + "-copy-" + String.valueOf(counter++) + ext);
		}
		return res;
	}
}
