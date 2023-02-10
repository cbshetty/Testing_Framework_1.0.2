package com.api.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketServiceData{

	public String URI;
	public String message;
	public Map<String,String> requestHeaders= new HashMap<String,String>();
	public int statusCode;
	public List<String> messageList = new ArrayList<String>();
	public int timeout=10;
	public int timeTaken;
}
