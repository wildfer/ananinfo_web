package com.AnAnInfoCmd.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.AnAnInfoCmd.bean.Aai_system_cmdbean;

public class Constants {
	public static Properties CONFIG = new Properties();
	public static HashMap<String, Aai_system_cmdbean> CMD_MAP = new HashMap<String, Aai_system_cmdbean>();
	
	static {
		
		try {
			InputStream in = Constants.class.getClassLoader().getResourceAsStream(
					"config.properties");
			CONFIG.load(in);
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
