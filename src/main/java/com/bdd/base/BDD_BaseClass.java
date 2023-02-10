package com.bdd.base;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.api.reporting.ReportFactory;

public class BDD_BaseClass {

	@BeforeClass
	public static void setup() {
		if(ReportFactory.reporting) {
			ReportFactory.StartReport("TestExtent");
			ReportFactory.setCucumberReports();
			ReportFactory.Setup_SlackIntegration();
		}
	}

	@AfterClass
	public static void tearDown() {
		if(ReportFactory.reporting) {
			//ReportFactory.PrintAllTestCases();
			ReportFactory.EndReport();
			ReportFactory.saveCucumberReports();
			ReportFactory.UploadReportToFileIO();
			ReportFactory.PublishReportOnSlack();
		}
	}

}
