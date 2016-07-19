package Main;

import java.io.FileNotFoundException;

public class User {
	private Configuration config;
		
	public User(String configRoute){
		
		try {
			config = new Configuration(configRoute);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public User(){
		config = new Configuration();
	}
	public void setConfig(String configRoute){
		try {
			config = new Configuration(configRoute);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Configuration getConfig(){
		return config;
	}
}
