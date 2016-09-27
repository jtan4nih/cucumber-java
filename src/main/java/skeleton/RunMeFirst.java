package skeleton;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.maven.cli.MavenCli;

import com.google.common.base.Joiner;

public class RunMeFirst {
	private static final int FILE_LIMIT = 99;
	private static String PROJECT_HOME = null;

	public static String getPROJECT_HOME() {
		return PROJECT_HOME;
	}

	public static void setPROJECT_HOME(String pROJECT_HOME) {
		PROJECT_HOME = pROJECT_HOME;
	}

	private static String readFileContent(String absoluteFile) {
		List<String> lines = null;
		File file = new File(absoluteFile);
		String joined = "";
		try {
			lines = FileUtils.readLines(file, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		joined = Joiner.on("\n").join(lines);

		return joined;
	}

	private static boolean parse(String absoluteFeatureTemplate) {
		//=== c.f. https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/text/StrSubstitutor.html
//		String templateString = "The ${datatable1} jumped over the ${target}.\n${datatable1}";
		String templateString = readFileContent(absoluteFeatureTemplate);
		Map<String, String> valuesMap = new HashMap<String, String>();
		String tableName = "datatable";
		String currName = null;
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String temp = null;
		for(int i = 0; i< FILE_LIMIT; i++) {
			currName = tableName + 1;
			temp = getPROJECT_HOME() +"/src/main/java/skeleton/"+ currName + ".txt";
			if(isFileValid(temp)) {
				try {
					valuesMap.put(currName, readFileContent(temp));
					templateString = sub.replace(templateString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		if(args.length == 0 || args.length < 2) {
			throw new Exception("Need to specify the project directory and the feature template file!");
		}
		String projDir = args[0];
		if(projDir == null) {
			throw new Exception("Need to specify the project directory!");
		}
		String templateFile = args[1];
		if(templateFile == null) {
			throw new Exception("Need to specify the feature template file!");
		}
		if(!isFileValid(projDir)) {
			throw new Exception("The specified project directory is invalid.");
		}
		if(!isFileValid(templateFile)) {
			throw new Exception("The specified template file is invalid.");
		}
		setPROJECT_HOME(projDir);
		MavenCli cli = new MavenCli();
//		cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
		cli.doMain(new String[]{"test"}, args[0], System.out, System.out);

		parse(templateFile);
	}
}
