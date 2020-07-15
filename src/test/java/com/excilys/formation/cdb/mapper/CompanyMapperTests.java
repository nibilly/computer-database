package com.excilys.formation.cdb.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

public class CompanyMapperTests {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void mapSQLToCompany() {
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		Company companyTest = new Company(2, "name");
		Company company = null;
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(companyTest.getId());
			Mockito.when(resultSet.getString("name")).thenReturn(companyTest.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			company = CompanyMapper.mapSQLToCompany(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(companyTest, company);
	}

	@Test
	public void mapCompanyDTO() {
		long id = 1L;
		String name = "name";
		Company company = new Company(id, name);
		CompanyDTO companyDTO = CompanyMapper.mapCompanyDTO(company);
		assertEquals(id + "", companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}

}
