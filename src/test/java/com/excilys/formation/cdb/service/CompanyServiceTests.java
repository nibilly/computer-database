package com.excilys.formation.cdb.service;

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

import com.excilys.formation.cdb.config.ContextFactory;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.zaxxer.hikari.HikariDataSource;

public class CompanyServiceTests extends DBTestCase {

	private CompanyService companyService;

	private ComputerService computerService;

	public CompanyServiceTests(String name) {
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
		companyService = (CompanyService) ContextFactory.getApplicationContext().getBean("companyService");
		computerService = (ComputerService) ContextFactory.getApplicationContext().getBean("computerService");
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
		List<Company> companies = companyService.findAll();
		assertEquals(false, companies.isEmpty());
	}

	@Test
	public void testGetNbCompanies() {
		int nbCompanies = companyService.getNbCompanies();
		assertEquals(2, nbCompanies);
	}

	@Test
	public void testFindAllPages() {
		Page<Company> page = new Page<Company>(1, 1);
		companyService.findCompanyPages(page);
		assertEquals(1, page.getEntities().size());
		Company company = page.getEntities().get(0);
		assertEquals(2, company.getId());
		assertEquals("Thinking Machines", company.getName());
	}

	@Test
	public void testDelete() {
		companyService.delete(1);
		List<Company> companiesDeleted = companyService.findAll();
		assertEquals(1, companiesDeleted.size());
		List<Computer> computersDeleted = computerService.findAll();
		assertFalse(computersDeleted.stream().anyMatch(c -> c.getCompany().getId() == 1));
	}
}
