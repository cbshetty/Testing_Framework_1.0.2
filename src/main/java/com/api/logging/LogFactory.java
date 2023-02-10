package com.api.logging;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LogFactory {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	
	private static LinkedList<String>logList;
	
	private LogFactory() {

	}
	public static void InitLogList() {
		logList = new LinkedList<String>();
	}
	public static LinkedList<String> GetLogList(){
		return logList;
	}
	public static void LogInfo(String message) {
		logger.info(message);
		//logList.add(message);
	}
	public static void LogError(String message) {
		logger.error(message);
		//logList.add(message);
	}
	public static void LogWarning(String message) {
		logger.warn(message);
		//logList.add(message);
	}

}
