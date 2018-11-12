package com.glroland.psplay.poc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

	private Properties properties;
	
	private static final Config instance = new Config();
	
	private static final Logger log = Logger.getLogger(Config.class.getName());

	private Config()
	{
		properties = new Properties();
		
		// load the configuration data
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.Config.FILENAME);
		if (inputStream == null)
		{
			String msg = "Properties file not found in classpath!  Name=" + Constants.Config.FILENAME;
			log.severe(msg);
			throw new RuntimeException(msg);
		}
		try
		{
			properties.load(inputStream);
			inputStream.close();
		}
		catch (IOException e)
		{
			String msg = "Unable to load properties file!  Name=" + Constants.Config.FILENAME;
			log.log(Level.SEVERE, msg, e);
			throw new RuntimeException(msg, e);
		}
	}
	
	public static Config getInstance()
	{
		return instance;
	}
	
	public String getRedirectUrl()
	{
		return properties.getProperty(Constants.Config.KEY_REDIRECT_URL);
	}
	
	public String getSonosClientId()
	{
		return properties.getProperty(Constants.Config.KEY_SONOS_CLIENT_ID);
	}
	
	public String getSonosSecret()
	{
		return properties.getProperty(Constants.Config.KEY_SONOS_SECRET);
	}
	
	public String getSonosUrl()
	{
		return properties.getProperty(Constants.Config.KEY_SONOS_URL);
	}
}
