package com.api.reporting;


import com.api.base.API_BaseClass;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import net.minidev.json.JSONArray;


public class SlackUtil {

	private String webHookURL;
	private String postAPI = "https://slack.com/api/chat.postMessage";
	private String token=System.getProperty("SLACK_BOT_TOKEN");
	private String channel;
	private String thread="";
	
	/*public SlackUtil(String webhookurl) {
		this.webHookURL=webhookurl;
	}*/
	public SlackUtil(String channelId) {
		this.channel=channelId;
	}
	
	public void postMessage(String message) {		
		/*API_BaseClass.API_Setup("AMXSlack", new AMX_API_Constants(), false);
		String pLoad="{\"text\":\""+message+"\"}";
		API_BaseClass.setAPIRequestBody(pLoad);
		API_BaseClass.Send_API_Request();
		System.out.println(API_BaseClass.getAPIResponse());*/	
		
		String pLoad="{\"text\":\""+message+"\"}";
		String resp = RestAssured.given().
				header("Content-Type", "application/json").
				body(pLoad).
				when().
				post(webHookURL).getBody().asString();
		
		System.out.println(resp);
			
	}
	
	public String postFormattedMessage(String message) {		
		String pLoad="{\r\n" + 
				"	\"blocks\": [\r\n" + 
				"		{\r\n" + 
				"			\"type\": \"section\",\r\n" + 
				"			\"text\": {\r\n" + 
				"				\"type\": \"mrkdwn\",\r\n" + 
				"				\"text\": \""+message+"\"\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	]\r\n" + 
				"}";
		String resp = RestAssured.given().
				header("Content-Type", "application/json").
				body(pLoad).
				when().
				post(webHookURL).getBody().asString();
		System.out.println("Slack Response:"+resp);
		return resp;
			
	}
	public String postFormattedMessageWithThread(String message) {	
		
		String pLoad = "{\r\n" + 
				"    \"channel\": \""+channel+"\",\r\n" + 
				"    \"text\":\""+"API Tests"+"\",\r\n" + 
				"    \"thread_ts\":\""+thread+"\",\r\n" + 
				"    \"blocks\": [\r\n" + 
				"        {\r\n" + 
				"            \"type\": \"section\",\r\n" + 
				"            \"text\": {\r\n" + 
				"                \"type\": \"mrkdwn\",\r\n" + 
				"                \"text\": \""+message+"\"\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";
		
		
		String resp = RestAssured.given().
				header("Content-Type", "application/json").
				header("Authorization", "Bearer "+token).
				body(pLoad).
				when().
				post(postAPI).getBody().asString();
		
		if(thread.equals("")) {
			thread = JsonPath.read(resp,"$.ts");
			System.setProperty("AMX_ts1", thread);
		}
		System.out.println("Slack Response:"+resp);
		return resp;
		
	}

}
