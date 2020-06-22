package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * MySQL connection singleton
 * @author nbilly
 *
 */
public class CDBConnection{

	/**
	 * JDBC connection which is Autocloseable
	 */
	private static Connection connection;

	/**
	 * if null or closed then create a JDBC connection
	 * @return
	 */
	public static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					InputStream inputStream = new FileInputStream("local.properties");
					Properties properties = new Properties();
					properties.load(inputStream);
					connection = DriverManager.getConnection(properties.getProperty("db.url"), 
							properties.getProperty("db.username"), properties.getProperty("db.password"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
