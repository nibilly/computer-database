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

	/**
	 * JDBC connection
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

	/**
	 * Close connection
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
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
