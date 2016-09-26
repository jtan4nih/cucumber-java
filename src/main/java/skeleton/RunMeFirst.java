package skeleton;

import java.io.File;

import org.apache.maven.cli.MavenCli;

public class RunMeFirst {

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
