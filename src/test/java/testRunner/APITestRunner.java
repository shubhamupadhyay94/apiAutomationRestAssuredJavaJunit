package testRunner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(features = "./src/test/java/features",glue = {"stepDefination","hook"})
@RunWith(Cucumber.class)
public class APITestRunner {

}


