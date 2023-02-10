package com.api.base;

import com.neovisionaries.ws.client.WebSocketFrame;

public interface WebSocketListener {
	
	void onConnected();

    void onPong(WebSocketFrame frame);
    
    void onTextMessage(String text);
    
    void onBinaryMessage(byte[] binary);

    void onError(Exception e);
    
    void onDisconnected();
    
    String getResponse();
    

}
