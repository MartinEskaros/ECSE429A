package stepdefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:build/reports/cucumber/report.html"},
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        monochrome = true
)
public class CucumberRunnerTests {

}