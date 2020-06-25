package com.excilys.formation.cdb.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

public class ComputerDTOTest {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void computerDTOComstructorFull() {
		String id = "1";
		String name = "name";
		String introduced = "1991-01-01";
		String discontinued = "2003-12-31";
		String companyId = "2";
		String companyName = "company name";
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyId());
		assertEquals(companyName, computerDTO.getCompanyName());
	}

	@Test
	void computerDTOComstructorEmpty() {
		String id = "1";
		String name = "name";
		String introduced = "1991-01-01";
		String discontinued = "2003-12-31";
		String companyId = "2";
		String companyName = "company name";
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setCompanyId(companyId);
		computerDTO.setCompanyName(companyName);
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyId());
		assertEquals(companyName, computerDTO.getCompanyName());
	}
}
