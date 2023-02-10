package com.api.base;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.api.reporting.ReportFactory;
import com.neovisionaries.ws.client.*;

import io.restassured.http.Header;
import io.restassured.http.Headers;



public class WebSocket_Client {

	private String websocketuri;
	private WebSocketListener websocketlistener;
	private WebSocket websocket;
	private String clientId;
	private String feedToken;
	private Headers headers;
	private String text;
	private static final int PING_INTERVAL = 10000; // 30 seconds
	private LinkedList<Long> messageTimeStamps;

	public WebSocket_Client(WebSocketListener websocketlistener) {
		this.websocketlistener = websocketlistener;
		headers = new Headers();
		init();
	}
	public WebSocket_Client(WebSocketListener websocketlistener,String wsuri) {
		this.websocketuri = wsuri;
		this.headers = new Headers();
		this.websocketlistener = websocketlistener;
	}

	public void AddHeaders(Headers headers) {
		this.headers=headers;
	}

	public void SetEndPoint(String endpoint) {
		this.websocketuri+=endpoint;
	}

	/**
	 * Returns a WebSocketAdapter to listen to ticker related events.
	 */
	public WebSocketAdapter getWebsocketAdapter() {
		return new WebSocketAdapter() {
			
			@Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws WebSocketException {
				System.out.println("connected");
				websocketlistener.onConnected();
            }

			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
				messageTimeStamps.add(System.currentTimeMillis());
				System.out.println("Text message recieved");
				websocketlistener.onTextMessage(message);
				super.onTextMessage(websocket, message);
			}

			@Override
			public void onBinaryMessage(WebSocket websocket, byte[] binary) {
				messageTimeStamps.add(System.currentTimeMillis());
				System.out.println("binary message recieved");
				try {
					websocketlistener.onBinaryMessage(binary);
					super.onBinaryMessage(websocket, binary);
				} catch (Exception e) {
					websocketlistener.onError(e);
				}
			}
			@Override
			public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
				System.out.println("Pong recieved");
				try {
					websocketlistener.onPong(frame);
				} catch (Exception e) {
					websocketlistener.onError(e);
				}
			}

			/**
			 * On disconnection, return statement ensures that the thread ends.
			 *
			 * @param websocket
			 * @param serverCloseFrame
			 * @param clientCloseFrame
			 * @param closedByServer
			 * @throws Exception
			 */
			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) {
				try {
					if (closedByServer) {
						if (serverCloseFrame.getCloseCode() == 1001) {

						}
						connect();
						listen(text);
					}else {
						System.out.println("Disconnected");
						websocketlistener.onDisconnected();
					}

				} catch (Exception e) {
					ReportFactory.FailTest("FAILURE :: Unable to connect back.");
					e.printStackTrace();
				}
			}

			@Override
			public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
				super.onCloseFrame(websocket, frame);
			}
		};
	}
	/**
	 * Disconnects websocket connection.
	 */
	public void disconnect() {
		System.out.println("disconnecting");
		if (websocket != null && websocket.isOpen()) {
			websocket.disconnect();
		}
	}

	/**
	 * Returns true if websocket connection is open.
	 *
	 * @return boolean
	 */
	public boolean isConnectionOpen() {
		return (websocket != null) && websocket.isOpen();
	}

	/**
	 * Returns true if websocket connection is closed.
	 *
	 * @return boolean
	 */
	public boolean isConnectionClosed() {
		return !isConnectionOpen();
	}

	public void connect() throws WebSocketException {	
		init();
		System.out.println("connecting");
		websocket.connect();
	}

	public void ping() throws WebSocketException {
		if(isConnectionClosed()) {
			System.out.println("Websocket is not open");
			connect();
		}
		System.out.println("pinging");
		websocket.sendPing();
	}
	
	public void listen(String text) {
		if(isConnectionClosed()) {
			System.out.println("Websocket is not open");
			try {
				connect();
			} catch (WebSocketException e) {
				System.out.println("Falied to connect");
				e.printStackTrace();
			}
		}
		messageTimeStamps = new LinkedList<Long>();
		this.text=text;
        if (websocket != null) {
            if (websocket.isOpen()) {
            	messageTimeStamps.add(System.currentTimeMillis());
            	websocket.sendText(text);
            } else {
            	ReportFactory.FailTest("FAILURE :: Websocket is not open.");
            }
        } else {
        	ReportFactory.FailTest("FAILURE :: Websocket is not connected.");
        }
    }

	public void init() {
		try {
			System.out.println("Init");
			websocket = new WebSocketFactory().setVerifyHostname(false).createSocket(websocketuri).setPingInterval(PING_INTERVAL);
			WebSocketExtension perMsgDeflateExt = WebSocketExtension.parse(WebSocketExtension.PERMESSAGE_DEFLATE);
			perMsgDeflateExt.setParameter("client_max_window_bits", null);
			websocket.addExtension(perMsgDeflateExt);
			for(Header header:this.headers) {
				websocket.addHeader(header.getName(),header.getValue());
            }
			websocket.addListener(getWebsocketAdapter());
		} catch (IOException e) {
			if (websocketlistener != null) {
				websocketlistener.onError(e);
            }
		}
		
	}
	
	public LinkedList<Long> GetMessageTimeStamps(){
		return messageTimeStamps;
	}

}
