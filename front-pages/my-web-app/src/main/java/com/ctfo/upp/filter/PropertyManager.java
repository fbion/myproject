package com.ctfo.upp.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author HollyLee
 *
 */
public class PropertyManager {

	private static PropertyManager instance = getInstance();
	private static Properties properties;
	
	public PropertyManager (){
		try {
			instance.properties = loadProperties();
		} catch (Exception ex) {
			// //System.out.println(n(n(n("Not able to load properties!!!");
			ex.printStackTrace();
		}
	}
	
	public static PropertyManager getInstance() {
		if(instance == null) {
			return new PropertyManager();
		} else {
			return instance;
		}
	}
	
	public Properties loadProperties() throws Exception {

		Properties properties = new Properties();
		InputStream input = null;

		try {
			input = this.getClass().getResourceAsStream("/filterkey.properties");
			properties.load(input);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return properties;
	}
	
	public static String getValue(String key) {
		return instance.properties.getProperty(key);
	}

}
