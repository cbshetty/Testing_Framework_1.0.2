package com.api.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.api.reporting.ReportFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

public class WebSocketListenerImpl implements WebSocketListener {
	
	public String statusCode="0";
	public String statusLine = "No Status";
	
	public String MessageStatusCode="0";
	public String MessageStatusLine = "No Status";
	
	public String PingStatusLine = "No Status";

	public String response;

	public Object responseObject;
	
	public LinkedList<Object> pongList;
	
	public LinkedList<Object> BinaryList;
	
	public LinkedList<String> TextList;


	@Override
	public void onConnected() {
		//ReportFactory.testInfo("INFO :: Websocket connection established");
		statusCode = "100";
		response="Connected";
		statusLine = "SUCCESS";
		pongList = new LinkedList<>();
		BinaryList = new LinkedList<>();
		TextList = new LinkedList<>();
		System.out.println("Status Code:"+statusCode);
	}

	@Override
	public void onPong(WebSocketFrame frame) {
		//ReportFactory.testInfo("INFO :: Websocket pong recieved");
		statusCode = "100";
		PingStatusLine = "SUCCESS";
		response = "pong";
		responseObject=frame;
		pongList.add(frame);
		System.out.println("Status Code:"+statusCode);
	}

	@Override
	public void onTextMessage(String text) {
		//ReportFactory.testInfo("INFO :: Text messgae recieved");
		statusCode = "200";
		MessageStatusCode="200";
		MessageStatusLine = "SUCCESS";
		response = "text";
		responseObject=text;
		TextList.add(text);
		System.out.println("Status Code:"+statusCode);
	}

	@Override
	public void onBinaryMessage(byte[] binary) {
		//ReportFactory.testInfo("INFO :: Binary messgae recieved");
		statusCode = "200";
		MessageStatusCode="200";
		MessageStatusLine = "SUCCESS";
		response = "binary";
		responseObject=binary;
		BinaryList.add(binary);
		System.out.println("Status Code:"+statusCode);
	}

	@Override
	public void onError(Exception e) {
		statusCode = "400";
		response="error";
		MessageStatusLine = "ERROR";
		responseObject="error";
		//ReportFactory.FailTest("FAILURE :: Error whilie connecting/listenng to WebSocket");
		e.printStackTrace();	
		System.out.println("Status Code:"+statusCode);
	}

	@Override
	public void onDisconnected() {
		statusCode = "100";
		response="Disconnected";
		statusLine = "SUCCESS";
		System.out.println("Status Code:"+statusCode);
	}
	
	public void resetResponses() {
		statusCode="0";
		statusLine = "No Status";
		MessageStatusCode="0";
		MessageStatusLine = "No Status";
		PingStatusLine = "No Status";
		pongList = new LinkedList<>();
		BinaryList = new LinkedList<>();
		TextList = new LinkedList<>();
	}

	@Override
	public String getResponse() {
		return response;
	}
}
