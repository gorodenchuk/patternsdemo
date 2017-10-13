package pageObject.utility;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Class that extracts properties from the prop file.
 */
public class PropertyLoader {
	private static Properties props = new Properties();
	private static final String PROP_FILE = "src\\main\\java\\resources\\application.properties";

	public static String loadProperty(String name) {
		
		try {
			 props.load(new FileInputStream(PROP_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String value = "";

		if (name != null) {
			value = props.getProperty(name);
		}
		return value;
	}
	
}



