package marc.repository.dbRepo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionFactory {
	
	private static String dbUrl;
	private static String dbUser;
	private static String dbPassword;

	static {
		checkDriver();
		loadCredentials();
	}

	private static void loadCredentials() {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("db.properties")) {
			properties.load(fis);
			dbUrl = properties.getProperty("dbUrl", "");
			dbUser = properties.getProperty("dbUser", "");
			dbPassword = properties.getProperty("dbPassword", "");
		} catch (IOException e) {
			throw new RuntimeException("Failed to load database credentials.", e);
		}
	}

	private static void checkDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load database connection driver.", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	}

}
