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

public class FeatureTemplateProcessor {
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
	
	private static void writeFileContent(String absoluteFile, String text) throws IOException {
		File file = new File(absoluteFile);
		FileUtils.writeStringToFile(file, text, "UTF-8");
	}

	private static String parse(String relativeFeatureTemplate) {
		//=== c.f. https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/text/StrSubstitutor.html
//		String templateString = "The ${datatable1} jumped over the ${target}.\n${datatable1}";
		String temp = getPROJECT_HOME() +"/src/main/java/skeleton/"+ relativeFeatureTemplate;
		String templateString = readFileContent(temp);
		Map<String, String> valuesMap = new HashMap<String, String>();
		String tableName = "datatable";
		String currName = null;
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
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
		
		return resolvedString;
	}

	public static boolean isFileValid(String directoryOrFile) {
		boolean ret = false;
		File f = new File(directoryOrFile);
		if(f.exists() || f.isDirectory()) {
			ret = true;
		}

		return ret;
	}

	public static void preprocessTemplateFeatureFile(String projectDir, String templateFile) throws Exception {
		setPROJECT_HOME(projectDir);
		//=== generate the final feature file
		String finalFeatureFile = parse(templateFile);
		String finalFile = getPROJECT_HOME() +"/src/test/resources/skeleton/"+ templateFile + ".feature";	//TODO
		System.out.println(finalFeatureFile);
		writeFileContent(finalFile, finalFeatureFile);
	}

	public static void main(String[] args) throws Exception {
		if(args.length == 0 || args.length < 2) {
			throw new Exception("Need to specify the project directory and the feature template file. The template file (as its corresponding external source file(s)) should be under src/main/java/skeleton/ relative to the project directory.");
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
		String temp = projDir +"/src/main/java/skeleton/"+ templateFile;
		if(!isFileValid(temp)) {
			throw new Exception("The specified template file is invalid.");
		}
		preprocessTemplateFeatureFile(projDir, templateFile);

		//=== run the cuketest!
		MavenCli cli = new MavenCli();
//		cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
		cli.doMain(new String[]{"test"}, args[0], System.out, System.out);

	}
}
