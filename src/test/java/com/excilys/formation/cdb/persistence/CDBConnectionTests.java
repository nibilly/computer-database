package com.excilys.formation.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class CDBConnectionTests{
	
	@Test
	void connectionClosed() {
		
		int hashCode = 0;
		int hashCode1 = 1;
		try(Connection c = CDBConnection.getConnection()){
			hashCode = c.hashCode();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		System.gc();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try(Connection c1 = CDBConnection.getConnection()){
			hashCode1 = c1.hashCode();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assertNotEquals(hashCode, hashCode1);
	}
	
	@Test
	void connectionSingleton() {
		int hashCode = 0;
		int hashCode1 = 1;
		try(Connection c = CDBConnection.getConnection()){
			hashCode = c.hashCode();
			try(Connection c1 = CDBConnection.getConnection()){
				hashCode1 = c1.hashCode();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		assertEquals(hashCode, hashCode1);
	}
}
