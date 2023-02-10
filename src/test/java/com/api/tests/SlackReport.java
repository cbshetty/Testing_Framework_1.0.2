package com.api.tests;

import com.api.reporting.ReportFactory;
import com.bdd.base.BDD_BaseClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"src/test/resources/features/"},
glue={"stepdefs","com.bdd.base"},
monochrome=true,
plugin =
{
		"pretty" , 
		"json:target/cucumber.json",
		"html:target/cucumber"/*,
		"com.api.reporting.TestCaseListener",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"*/
},
dryRun=false,
tags = "@PublishSlackReport"
)
public class SlackReport extends BDD_BaseClass{
	@BeforeClass
	public static void Setup() {
		ReportFactory.reporting=false;
	}
	
}
