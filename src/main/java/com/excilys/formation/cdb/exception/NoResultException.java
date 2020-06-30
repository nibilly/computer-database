package com.excilys.formation.cdb.exception;

/**
 * Exception throwable when there is no SQL result
 * @author nbilly
 *
 */
public class NoResultException extends Exception {

	private static final long serialVersionUID = -9154366546599680143L;

	@Override
	public String getMessage() {
		return "SQL request has no results";
	}
}
