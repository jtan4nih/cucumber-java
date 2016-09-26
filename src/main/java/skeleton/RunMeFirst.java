package skeleton;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.maven.cli.MavenCli;

public class RunMeFirst {
	private static final int FILE_LIMIT = 99;
	private static String PROJECT_HOME = null;

	public static String getPROJECT_HOME() {
		return PROJECT_HOME;
	}

	public static void setPROJECT_HOME(String pROJECT_HOME) {
		PROJECT_HOME = pROJECT_HOME;
	}

	public static boolean compile() {
		//=== c.f. https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/text/StrSubstitutor.html
		String templateString = "The ${datatable1} jumped over the ${target}.\n${datatable1}";
		Map valuesMap = new HashMap<Integer, String>();
		String tableName = "datatable";
		String currName = null;
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		for(int i = 0; i< FILE_LIMIT; i++) {
			currName = tableName + 1;
			if(isFileValid(getPROJECT_HOME() + "/" + currName + ".txt")) {
				valuesMap.put("${" + currName + "}", "quick brown fox");
				templateString = sub.replace(templateString);
			}
		}
		String resolvedString = templateString;
		
		System.out.println("Final text = [" + resolvedString + "]");
		
		return false;
	}

	public static boolean isFileValid(String directoryOrFile) {
		boolean ret = false;
		File f = new File(directoryOrFile);
		if(f.exists() || f.isDirectory()) {
			ret = true;
		}

		return ret;
	}

	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			throw new Exception("Need to specify the project directory!");
		}
		String projDir = args[0];
		if(args[0] == null) {
			throw new Exception("Need to specify the project directory!");
		}
		if(!isFileValid(projDir)) {
			System.out.println("The specified project directory is invalid.");
		}
		setPROJECT_HOME(projDir);
		MavenCli cli = new MavenCli();
//		cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
		cli.doMain(new String[]{"test"}, args[0], System.out, System.out);

		compile();
	}
}
