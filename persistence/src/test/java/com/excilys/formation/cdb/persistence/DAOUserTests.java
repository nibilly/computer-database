package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.formation.cdb.config.ContextFactory;
import com.excilys.formation.cdb.model.User;
import com.zaxxer.hikari.HikariDataSource;

public class DAOUserTests extends DBTestCase {

	private DAOUser daoUser;

	public DAOUserTests(String name) {
		super(name);

		try {
			InputStream inputStream = new FileInputStream("src/test/resources/local.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			String url = properties.getProperty("db.url");
			String username = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");
			String driver = properties.getProperty("db.driver");

			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, driver);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, url);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, username);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, password);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		daoUser = (DAOUser) ContextFactory.getApplicationContext().getBean("daoUser");
	}

	@Before
	public void setUp() throws Exception {
		try (Connection connection = (Connection) ((HikariDataSource) ContextFactory.getApplicationContext()
				.getBean("dataSource")).getConnection()) {
			DatabaseConnection dbConnection = new DatabaseConnection(connection);
			getSetUpOperation().execute(dbConnection, getDataSet());
		}
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset.xml"));
	}

	@Test
	public void testFindByUsername() {
		User user = null;
		try {
			user = daoUser.findByUsername("user");
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals("user", user.getUsername());
		assertNotNull(user.getPassword());
		user.getAuthorities().forEach(a -> assertEquals("ROLE_USER", a.getAuthority()));
	}

	@Test
	public void testFindByUsernameNoResult() {
		boolean result = true;
		try {
			daoUser.findByUsername("dontexist");
		} catch (UsernameNotFoundException e) {
			result = false;
		}
		assertFalse(result);
	}
}
