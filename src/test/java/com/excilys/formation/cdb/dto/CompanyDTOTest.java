package com.excilys.formation.cdb.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CompanyDTOTest {
	@Test
	void companyDTOContructorFull() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO(id, name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}
	
	@Test
	void companyDTOContructorEmpty() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		companyDTO.setName(name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}
}
