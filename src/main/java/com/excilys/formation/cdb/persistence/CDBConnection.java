package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * MySQL connection singleton
 * 
 * @author nbilly
 *
 */
public class CDBConnection {

	/**
	 * JDBC connection which is Autocloseable
	 */
	private Connection connection;

	private static Logger logger = LoggerFactory.getLogger(Connection.class);

	private HikariDataSource dataSource;

	public void setDataSource(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * if null or closed then create a JDBC connection
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		logger.info("GetConnection()");
		try {
			if (connection == null || connection.isClosed()) {
				connection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
