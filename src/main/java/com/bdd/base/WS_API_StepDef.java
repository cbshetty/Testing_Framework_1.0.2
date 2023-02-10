package com.bdd.base;

import java.util.List;
import com.api.reporting.ReportFactory;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WS_API_StepDef {

	@Then("Date type of parameter {string} is {string}")
	public void date_type_of_parameter_is(String paramPath, String paramType) {
		Parallel_BaseClass.getAPIBaseClass().validateJsonParamterType(paramPath, paramType);
	}

	@Then("Websocket API returns the expected reponse")
	public void websocket_API_returns_the_expected_reponse() {
		Parallel_BaseClass.getAPIBaseClass().validateJsonSchema();
	}

	@Then("Websocket API returns the expected parameters")
	public void websocket_API_returns_the_expected_parameters() {
		Parallel_BaseClass.getAPIBaseClass().Validate_WS_APIResponseParameters();	
		//Parallel_BaseClass.getAPIBaseClass().Validate_WS_APIResponseParameterTypes();	
	}

	@Then("Appilication pings the websocket connection")
	public void appilication_pings_the_websocket_connection() {
		Parallel_BaseClass.getAPIBaseClass().WS_Ping();
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponsePongList());
	}

	@Then("API response is {string}")
	public void api_response_is(String ExpResponse) {
		String actResponse = Parallel_BaseClass.getAPIBaseClass().getAPIResponse();
		if(actResponse.equals(ExpResponse)) {
			ReportFactory.PassTest("SUCCESS :: API returned the expected response");
		}else {
			ReportFactory.FailTest("FAILURE :: API did notreturn the expected response");
		}

	}

	@When("Applications listens to the Websocket connection")
	public void applications_listens_to_the_Websocket_connection() {
		Parallel_BaseClass.getAPIBaseClass().WS_Listen();
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponsePongList());
	}

	@When("Applications sends message {string} and listens to the Websocket connection")
	public void applications_sends_message_and_listens_to_the_Websocket_connection(String message) {
		Parallel_BaseClass.getAPIBaseClass().WS_Listen(message);
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponsePongList());
	}

	@When("Applications sends message and listens to the Websocket connection")
	public void applications_sends_message_and_listens_to_the_Websocket_connection() {
		Parallel_BaseClass.getAPIBaseClass().WS_Listen();
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList());
		System.out.println(Parallel_BaseClass.getAPIBaseClass().getAPIReponsePongList());
	}

	@Then("{int} message\\(s) recieved in {int} seconds")
	public void message_s_recieved_in_seconds(Integer messageCount, Integer timeInSeconds) {
		List<Long> timestamps  = Parallel_BaseClass.getAPIBaseClass().getAPIResponseTimeStamps();
		if(timestamps.size()<(messageCount+1)) {
			ReportFactory.FailTest("FAILURE :: Failed to recieve "+messageCount+" message(s) in 30 sec");
		}else {
			long start = timestamps.get(0);
			long end = timestamps.get(messageCount);
			long timeElapsed = end-start;
			if(timeElapsed<=(timeInSeconds*1000)) {
				ReportFactory.PassTest("SUCCESS :: "+messageCount+" message(s) recieved in "+timeElapsed+" ms(< threshold of "+(timeInSeconds*1000)+" ms)");
			}else {
				ReportFactory.FailTest("FAILURE :: "+messageCount+" message(s) recieved in "+timeElapsed+" ms(> threshold of "+(timeInSeconds*1000)+" ms)");
			}
		}
	}

	@Then("Display last {int} responses of Websocket API")
	public void display_last_responses_of_Websocket_API(Integer count) {
		int size=0;
		if(size<count)
			if(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList().size()>=count) {
				size = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size();
				for(int i=1;i<=count;i++) {
					ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList().get(size-i)+"</font></details></a>");
				} 	
			}else if(Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList().size()>0) {
				size = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size();
				ReportFactory.testInfo("INFO :: only "+size+" response(s) recieved");
				for(int i=1;i<=size;i++) {
					ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+Parallel_BaseClass.getAPIBaseClass().getAPIReponseStringList().get(size-i)+"</font></details></a>");
				} 
			}
		if(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size()>=count) {
			size = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size();
			for(int i=1;i<=count;i++) {
				Object o = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().get(size-i);
				ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+Parallel_BaseClass.getAPIBaseClass().getJSONStringFromBinary(o)+"</font></details></a>");
			}     	
		}else if(Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size()>0) {
			size = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().size();
			ReportFactory.testInfo("INFO :: only "+size+" response(s) recieved");
			for(int i=1;i<=size;i++) {
				Object o = Parallel_BaseClass.getAPIBaseClass().getAPIReponseObjectList().get(size-i);
				ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+Parallel_BaseClass.getAPIBaseClass().getJSONStringFromBinary(o)+"</font></details></a>");
			} 
		}

	}

	@Then("Application disconnects the websocket API")
	public void application_disconnects_the_websocket_API() {
		Parallel_BaseClass.getAPIBaseClass().Disconnect_WS_API();

	}

	@Then("API response status is {string}")
	public void api_response_status_is(String statusCode) {
		Parallel_BaseClass.getAPIBaseClass().Validate_APIResponseStatus(statusCode);
	}


}
