package com.api.reporting;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

public class TestCaseListener implements ConcurrentEventListener  {

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
		publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);	
	}

	public void onTestCaseStarted(TestCaseStarted event) {
		TestCase testCase = event.getTestCase();
		System.out.println("In onTestCaseStarted Listener: Starting " + testCase.getName());
	}

	private void onTestCaseFinished(final TestCaseFinished event) {
		TestCase testCase = event.getTestCase();
		System.out.println("In onTestCaseFinished Listener: Finished " + testCase.getName());
		Result result = event.getResult();	
		if (result.getStatus() == Status.FAILED) {
			final Throwable error = result.getError();
			ReportFactory.SetTestCaseErrorDetials(error.toString());
			error.printStackTrace();
		}
		ReportFactory.endTest();
	}

}
