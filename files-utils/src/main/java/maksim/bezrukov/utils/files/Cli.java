package maksim.bezrukov.utils.files;

import maksim.bezrukov.utils.files.doctopdf.DocToPdfCli;
import maksim.bezrukov.utils.files.extfilter.ExtFilterCli;
import maksim.bezrukov.utils.files.filter.FilterCli;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Maksim Bezrukov
 */
public class Cli {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("First argument shall be module name. Possible modules: "
			+ Arrays.stream(Module.values()).map(Module::getName).collect(Collectors.joining(", ")));
			System.exit(1);
		}

		try {
			Module module = Module.fromName(args[0]);
			String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
			switch (module) {
				case EXT_FILTER:
					ExtFilterCli.main(newArgs);
					break;
				case DOC_TO_PDF:
					DocToPdfCli.main(newArgs);
					break;
				case FILTER:
					FilterCli.main(newArgs);
					break;
				default:
					System.err.println("Unsupported module " + module.getName());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public enum Module {
		DOC_TO_PDF("DOCTOPDF"),
		EXT_FILTER("EXTFILTER"),
		FILTER("FILTER");

		private String name;

		Module(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static Module fromName(String name) {
			if (name != null) {
				for (Module module : Module.values()) {
					if (module.name.equals(name.toUpperCase())) {
						return module;
					}
				}
			}
			throw new IllegalArgumentException("Unsupported module name " + name);
		}
	}
}
