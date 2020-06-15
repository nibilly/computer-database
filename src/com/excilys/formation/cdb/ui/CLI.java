package com.excilys.formation.cdb.ui;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CDBConnection;
import com.excilys.formation.cdb.persistence.DAOCompany;
import com.excilys.formation.cdb.persistence.DAOComputer;

public class CLI {
	private static boolean continuer;
	private static Scanner scanner;
	
	/**
	 * Create a scanner, while user continue to respond call menu else close scanner and possible BD connection
	 * @param args
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		continuer = true;
		while(continuer) {
			menu();
		}
		scanner.close();
		CDBConnection.CloseConnection();
	}
	
	/**
	 * Print the menu. User choice call a function or stop the program
	 */
	private static void menu() {
		
		System.out.println("--------CLI---------");// 20 characters
		System.out.println("0)Quit");
		System.out.println("1)List computers");
		System.out.println("2)List companies");
		System.out.println("3)Show computer details");
		System.out.println("4)Create a computer");
		System.out.println("5)Update a computer");
		System.out.println("6)Delete a computer");
		System.out.println("--------------------");
		System.out.print("Your choice : ");
		String choice = scanner.nextLine();
		System.out.println();
		switch (choice) {
		case "0":
			continuer = false;
			break;
		case "1":
			computerListing();
			break;
		case "2":
			companyListing();
			break;
		default:
			System.out.println("Choix incorrect");
			break;
		}
	}
	
	private static void computerListing() {
		System.out.println("---List computers---");
		List<Computer> computers = DAOComputer.findAll();
		for (Computer computer : computers) {
			System.out.println(computer);
		}
		System.out.println();
	}

	private static void companyListing() {
		System.out.println("---List companies---");
		List<Company> companies = DAOCompany.findAll();
		for (Company company : companies) {
			System.out.println(company);
		}
		System.out.println();
	}
}
