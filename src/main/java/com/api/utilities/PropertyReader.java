package com.api.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private Properties prop;
	public PropertyReader(String filepath) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(filepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String ReadProperty(String key) {
		return prop.getProperty(key);
	}


}
