package skeleton;

import org.sample.cucumber.ExtendedCucumberRunner;
import org.sample.cucumber.annotations.AfterSuite;
import org.sample.cucumber.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

//@RunWith(Cucumber.class)
@RunWith(ExtendedCucumberRunner.class)
@CucumberOptions(
  plugin = {"pretty"}
  ,features = "src/test/resources/skeleton"
//	plugin = {"html:target/cucumber-html-report",
//            "json:target/cucumber.json",
//            "pretty:target/cucumber-pretty.txt",
//            "usage:target/cucumber-usage.json"
//           },
//  features = {"output/" },
//  ,glue = {"org/sample/cucumber/glue" }
//  ,tags = {"@2"}
)
public class RunCukesTest {
	@BeforeSuite
    public static void setUp() {
        // TODO: Add your pre-processing
		System.out.println("****************************** RunCukesTest.java: inside setUp:");
		String projectDir = "C:/Users/ATAN/cucumber-java";
		String templateFile = "belly2.feature.~";
		System.out.println("projectDir: " + projectDir);
		System.out.println("templateFile: " + templateFile);
		try {
			FeatureTemplateProcessor.preprocessTemplateFeatureFile(projectDir, templateFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @AfterSuite
    public static void tearDown() {
        // TODO: Add your post-processing
		System.out.println("****************************** RunCukesTest.java: inside tearDown");
    }
}