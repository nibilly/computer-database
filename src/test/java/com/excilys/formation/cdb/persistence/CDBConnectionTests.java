package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.formation.cdb.servlet.ContextFactory;

public class CDBConnectionTests {

	private CDBConnection cdbConnection;

	public CDBConnectionTests() {
		cdbConnection = (CDBConnection) ContextFactory.getApplicationContext().getBean("cdbConnection");
	}

	@Test
	public void connectionClosed() {

		int hashCode = 0;
		int hashCode1 = 1;
		try (Connection c = cdbConnection.getConnection()) {
			hashCode = c.hashCode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.gc();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try (Connection c1 = cdbConnection.getConnection()) {
			hashCode1 = c1.hashCode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotEquals(hashCode, hashCode1);
	}

	@Test
	public void connectionSingleton() {
		int hashCode = 0;
		int hashCode1 = 1;
		try (Connection c = cdbConnection.getConnection()) {
			hashCode = c.hashCode();
			try (Connection c1 = cdbConnection.getConnection()) {
				hashCode1 = c1.hashCode();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(hashCode, hashCode1);
	}
}
