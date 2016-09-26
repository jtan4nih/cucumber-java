package skeleton;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.maven.cli.MavenCli;

public class RunMeFirst {

	private boolean compile() {
		//=== c.f. https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/text/StrSubstitutor.html
		Map valuesMap = new HashMap<Integer, String>();
		valuesMap.put("animal", "quick brown fox");
		valuesMap.put("target", "lazy dog");
		String templateString = "The ${animal} jumped over the ${target}.";
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String resolvedString = sub.replace(templateString);

		return false;
	}

	public static void main(String[] args) {
		String projDir = args[0];
		File f = new File(projDir);
		if(!f.exists() || !f.isDirectory()) {
			System.out.println("The specified project directory is invalid.");
		}
		MavenCli cli = new MavenCli();
//		cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
		cli.doMain(new String[]{"test"}, args[0], System.out, System.out);

	}
}
