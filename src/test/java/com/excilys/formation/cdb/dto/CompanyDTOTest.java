package com.excilys.formation.cdb.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyDTOTest {
	@Test
	public void testCompanyDTOContructorFull() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO(id, name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}

	@Test
	public void testCompanyDTOContructorEmpty() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		companyDTO.setName(name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}
}
