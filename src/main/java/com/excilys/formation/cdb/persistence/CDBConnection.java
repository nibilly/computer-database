package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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

	private static Logger logger = LoggerFactory.getLogger(Connection.class);
	
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    	InputStream inputStream = CDBConnection.class.getClassLoader().getResourceAsStream("local.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        ds = new HikariDataSource(config);
    }

	/**
	 * if null or closed then create a JDBC connection
	 * @return
	 */
	public synchronized static Connection getConnection() {
		logger.info("GetConnection()");
		try {
			if(connection == null || connection.isClosed()) {
					connection = ds.getConnection();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
