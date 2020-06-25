package com.excilys.formation.cdb.model;

/**
 * Exception throwable when there is no SQL result
 * @author nbilly
 *
 */
@SuppressWarnings("serial")
public class NoResultException extends Exception {

	@Override
	public String getMessage() {
		return "SQL request has no results";
	}
}
