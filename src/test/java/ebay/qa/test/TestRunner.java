package ebay.qa.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
plugin = {"json:build/cucumber.json"},
features = {"classpath:features"},
glue= {"ebay.qa.testautomation", "utilities"},
tags = {"@Scenario001","@Scenario002","@Scenario003"}
)

public class TestRunner {
	
}
