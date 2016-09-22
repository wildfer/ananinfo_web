package com.AnAnInfoWebMagic.util;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
public class Constants {
	public static Properties CONFIG = new Properties();
	public static final String TEST1 = "test";
	public static final int TEST2 = 2;
	
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
