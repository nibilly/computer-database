package com.excilys.formation.cdb.model;

@SuppressWarnings("serial")
public class NoResultException extends Exception {

	@Override
	public String getMessage() {
		return "SQL request has no results";
	}
}
