package com.api.base;

import java.util.Map;

public class WebClient {
	public Client webSocket;
	
	public SocketServiceData ConnectAndListen(SocketServiceData socketContext) {
		try {	
		webSocket = new Client(socketContext);
			if(!socketContext.requestHeaders.isEmpty()) {
				Map<String,String>requestHeaderParams = socketContext.requestHeaders;
				for(String headername:requestHeaderParams.keySet()) {
					webSocket.addHeader(headername, requestHeaderParams.get(headername));
				}
			}
			
				webSocket.connectBlocking();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		return socketContext;
	}
	
	public void SendMessage(String message) {
		webSocket.send(message);
	}
	
	public void Ping() {
		webSocket.sendPing();
	}
}
