package ru.curs.lyragridtest;

import java.util.Properties;

public class Settings {
	private final static Properties settings = new Properties();

	static {
		// put your settings here
		settings.setProperty("score.path", "d:/workspaces/mvnspace-neon/lyragridtest/src/main/celesta");
		settings.setProperty("pylib.path", "d:/jython2.7.0/Lib");
		settings.setProperty("database.connection",
				"jdbc:postgresql://127.0.0.1:5432/kladr?user=postgres&password=123");
		// p.setProperty("database.connection",
		// "jdbc:sqlserver://localhost;databaseName=testkladr;user=sa;password=123");
	}

	public static Properties getSettings() {
		return settings;
	}
}
