package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL connection singleton
 * @author nbilly
 *
 */
public class CDBConnection {

	private static Connection connection;

	public static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url = "jdbc:mysql://localhost/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
					connection = DriverManager.getConnection(url, "admincdb", "qwerty1234");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void CloseConnection() {
		if(connection != null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
