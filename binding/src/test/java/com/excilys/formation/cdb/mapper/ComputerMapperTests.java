package com.excilys.formation.cdb.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public class ComputerMapperTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMapSQLTocomputer() {
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		Computer computerTest = new Computer(2, "name", LocalDate.parse("1998-01-01", dateTimeFormatter),
				LocalDate.parse("2003-01-01", dateTimeFormatter), new Company(2, "CompanyName"));

		Computer computer = null;
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(computerTest.getId());
			Mockito.when(resultSet.getString("computer_name")).thenReturn(computerTest.getName());
			Mockito.when(resultSet.getDate("introduced"))
					.thenReturn(DateMapper.sqlDateFromLocalDate(computerTest.getIntroduced()));
			Mockito.when(resultSet.getDate("discontinued"))
					.thenReturn(DateMapper.sqlDateFromLocalDate(computerTest.getDiscontinued()));
			Mockito.when(resultSet.getLong("company_id")).thenReturn(computerTest.getCompany().getId());
			Mockito.when(resultSet.getString("company_name")).thenReturn(computerTest.getCompany().getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			computer = ComputerMapper.mapSQLToComputer(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(computerTest, computer);
	}

	@Test
	public void testMapComputerDTO() {
		long id = 1L;
		String name = "name";
		LocalDate introduced = LocalDate.parse("1995-01-01", dateTimeFormatter);
		LocalDate discontinued = LocalDate.parse("1995-01-01", dateTimeFormatter);
		long companyId = 2L;
		String companyName = "companyName";
		Computer computer = new Computer(id, name, introduced, discontinued, new Company(companyId, companyName));
		ComputerDTO computerDTO = ComputerMapper.mapComputerDTO(computer);
		assertEquals(id + "", computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced.toString(), computerDTO.getIntroduced());
		assertEquals(discontinued.toString(), computerDTO.getDiscontinued());
		assertEquals(companyId + "", computerDTO.getCompanyDTO().getId());
		assertEquals(companyName, computerDTO.getCompanyDTO().getName());
	}
}
