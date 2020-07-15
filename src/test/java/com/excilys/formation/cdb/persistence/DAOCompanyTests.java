package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.servlet.ContextFactory;
import com.zaxxer.hikari.HikariDataSource;

public class DAOCompanyTests extends DBTestCase {

	private DAOCompany daoCompany;

	public DAOCompanyTests(String name) {
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
		daoCompany = (DAOCompany) ContextFactory.getApplicationContext().getBean("daoCompany");
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
	public void testFindAll() {
		List<Company> companies = daoCompany.findAll();
		assertEquals(false, companies.isEmpty());
	}

	@Test
	public void testFindById() {
		try {
			Company company = daoCompany.findById(1);
			assertEquals("Apple Inc.", company.getName());
			assertEquals(1L, company.getId());
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNbCompanies() {
		int nbCompanies = daoCompany.getNbCompanies();
		assertEquals(2, nbCompanies);
	}

	@Test
	public void testFindAllPages() {
		Page<Company> page = new Page<Company>(1, 1);
		daoCompany.findAllPages(page);
		assertEquals(1, page.getEntities().size());
		Company company = page.getEntities().get(0);
		assertEquals(2, company.getId());
		assertEquals("Thinking Machines", company.getName());
	}
}
