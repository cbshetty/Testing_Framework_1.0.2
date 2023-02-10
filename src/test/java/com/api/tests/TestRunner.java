package com.api.tests;

import com.api.base.API_BaseClass;
import com.api.data.App_API_Constants;
import com.bdd.base.BDD_BaseClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"src/test/resources/parallel"},
glue={"com.bdd.base","parallel"},
monochrome=true,
plugin =
{
		"pretty" ,
		"junit:target/bdd_report.xml",
		"json:target/bdd_report.json",
		"html:target/bdd_htmlreport"/*,
		"com.api.reporting.TestCaseListener",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"*/
},
dryRun=true,
tags = "@Test"
		)
public class TestRunner extends BDD_BaseClass{
	@BeforeClass
	public static void SetConstantsClass() {
		API_BaseClass.setConstantsClassObject(new App_API_Constants());
	}

}
