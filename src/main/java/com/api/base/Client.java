package com.api.base;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.api.reporting.ReportFactory;

public class Client extends WebSocketClient{
	public SocketServiceData dataContext;
	public Date openedTime,closedTime;

	public Client(URI serverUri) {
		super(serverUri);
	}
	public Client(SocketServiceData dataContext) throws URISyntaxException {
		super(new URI(dataContext.URI));
		this.dataContext=dataContext;
	}
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		openedTime=new Date();
		System.out.println("INFO :: Opened WwebSocket connection");
	}
	@Override
	public void onMessage(String message) {	
		System.out.println("INFO :: Message recieved : "+message);
		dataContext.messageList.add(message);
		
	}
	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("INFO :: WebSocket connection closed.");
		dataContext.statusCode=code;
	}
	@Override
	public void onError(Exception ex) {
		System.out.println("INFO :: error while connecting/listening to WebSocket");
	}
}

