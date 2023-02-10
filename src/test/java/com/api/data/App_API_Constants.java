package com.api.data;

import java.util.Arrays;
import java.util.List;



public class App_API_Constants {


	public static String BaseURI = "default-app-base-url"; // priority 1 if environment name and environment url are not passed in the argument
	public static String BaseURI_Environment="Environment-base-url"; // priority 1 if environment name is passed in the argument and environment base uri & default base uri is not set for api

	// WS API
	public static String WSAPI2_BaseURI = "default-api-base-url"; // priority 1 if environment name/environment url is passed in the argument and environment base uri is not set for api
	public static String WSAPI2_BaseURI_Envronment = "Environment-base-url"; // priority 1 if environment name is passed as argumment
	public static String WSAPI_EndPoint = "/end-point";
	public static String WSAPI_MethodType = "TEXT";
	public static String WSAPI_ListenText = "message-string";
	public static List<String> WSAPI_RespParams = Arrays.asList("param1,2,3,4");

	//Rest API
	public static String RestAPI_BaseURI = "base-uri";
	public static String RestAPI_EndPoint = "/emdoint";
	public static String RestAPI_ReqHeaders_Names = "headerName1,2,3";
	public static String RestAPI_ReqHeaders_Values= "headerValue1,2,3";
	public static String RestAPI_MethodType = "POST";
	public static String RestAPI_RequestBody = "json-payload-string";
	
	//Sample API
	//public static String Sample_BaseURI = "https://reqres.in";
	public static String Sample_EndPoint = "/api/users/2";
	public static String Sample_MethodType = "GET";
	
}
