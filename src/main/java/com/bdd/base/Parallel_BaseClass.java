package com.bdd.base;

import java.util.HashMap;
import java.util.Map;

import com.api.base.API_BaseClass_V2;

public class Parallel_BaseClass {
	
	private static ThreadLocal<API_BaseClass_V2> API_BaseClass = new ThreadLocal<API_BaseClass_V2>();
	private static Map<String,String> ScenarioStatus = new HashMap<String,String>();
	
	public static void SetAPIBaseClass(API_BaseClass_V2 obj) {
		API_BaseClass.set(obj);
	}
	
	public static API_BaseClass_V2 getAPIBaseClass() {
		return API_BaseClass.get();
	}
	
	public static void RemoveAPIBaseClass() {
		API_BaseClass.remove();
	}
	
	public static void SetScenarioStatus(String Scenario, String Status) {
		ScenarioStatus.remove(Scenario);
		ScenarioStatus.put(Scenario, Status);
	}
	
	public static String GetScenarioStatus(String Scenario) {
		if(ScenarioStatus.keySet().contains(Scenario)) {
			return ScenarioStatus.get(Scenario);
		}else {
			return "";
		}
	}
}
