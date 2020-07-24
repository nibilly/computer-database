package com.excilys.formation.cdb.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NoResultExceptionTests {

	@Test
	public void testGetMessage() {
		NoResultException noResultException = new NoResultException();
		assertEquals("SQL request has no results", noResultException.getMessage());
	}
}
