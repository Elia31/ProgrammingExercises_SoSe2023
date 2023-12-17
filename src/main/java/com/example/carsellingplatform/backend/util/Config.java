package com.example.carsellingplatform.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
	private static Properties properties;

	static
	{
		properties = new Properties();
		try (InputStream inputStream = Config.class.getResourceAsStream(
				"/com/example/carsellingplatform/config.properties"))
		{
			properties.load(inputStream);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getEmailUsername()
	{
		return properties.getProperty("email.username");
	}

	public static String getEmailPassword()
	{
		return properties.getProperty("email.password");
	}

	public static String getDatabaseUrl()
	{
		return properties.getProperty("database.url");
	}

	public static String getDatabaseUser()
	{
		return properties.getProperty("database.user");
	}

	public static String getDatabasePassword()
	{
		return properties.getProperty("database.password");
	}

}