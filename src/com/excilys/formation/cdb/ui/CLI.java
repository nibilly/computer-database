package com.excilys.formation.cdb.ui;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Command Line Interface
 * @author nbilly
 *
 */
public class CLI {
	private static boolean continuer;
	private static Scanner scanner;
	private static SimpleDateFormat simpleDateFormat;

	/**
	 * Create a scanner, while user continue to respond call menu else close scanner
	 * @param args
	 */
	public static void main(String[] args) {
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		simpleDateFormat.setLenient(false);
		scanner = new Scanner(System.in);
		continuer = true;
		while(continuer) {
			menu();
		}
		scanner.close();
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
		case "3":
			computerDetails();
			break;
		case "4":
			createComputer();
			break;
		case "5":
			updateComputer();
			break;
		case "6":
			deleteComputer();
			break;
		default:
			System.out.println("Choix incorrect");
			break;
		}
	}

	private static void deleteComputer() {
		System.out.println("-Delete a computer--");
		long computerId = askComputerId();// this method already ask for validation
		ComputerService.deleteComputerById(computerId);		
	}

	private static void updateComputer() {
		System.out.println("-Update a computer--");
		long computerId = askComputerId();
		System.out.println("New informations for this computer: ");
		Computer computer = askComputerInformations();
		computer.setId(computerId);
		ComputerService.updateComputer(computer);
	}

	private static void createComputer() {
		System.out.println("-Create a computer--");
		Computer computer = askComputerInformations();
		ComputerService.createComputer(computer);
	}

	/**
	 * ask computer informations until user give valid inputs.
	 * @return a computer without id
	 */
	private static Computer askComputerInformations() {
		boolean continuer;
		String name = null;
		String introduced = null;
		Date introducedDate = null;
		String discontinued = null;
		Date discontinuedDate = null;
		String companyId = null;
		long companyIdLong = 0l;
		do {
			continuer = false;
			System.out.print("name: ");
			name = scanner.nextLine();
			System.out.print("introduced(yyyy-MM-dd or null): ");
			introduced = scanner.nextLine();
			System.out.print("discontinued(yyyy-MM-dd or null): ");
			discontinued = scanner.nextLine();
			System.out.print("companyId(number or null): ");
			companyId = scanner.nextLine();
			if(introduced.compareTo("null") != 0){
				try{
					introducedDate = new java.sql.Date(simpleDateFormat.parse(introduced).getTime());
				}
				catch(ParseException e){
					System.out.println("introduced date incorrect");
					continuer = true;
				}
			}
			if(discontinued.compareTo("null") != 0) {
				try{
					discontinuedDate = new java.sql.Date(simpleDateFormat.parse(discontinued).getTime());
				}
				catch (ParseException e) {
					System.out.println("discontinued date incorrect");
					continuer = true;
				}
			}
			if(companyId.compareTo("null") != 0) {
				try {
					companyIdLong = Long.parseLong(companyId);
				}
				catch(NumberFormatException e)
				{
					System.out.println("companyId number is not a number");
					continuer = true;
				}
			}
		}while(continuer);
		return new Computer(name, introducedDate, discontinuedDate, companyIdLong);
	}

	/**
	 * Ask for computerId until this computer exist
	 * @return the computer corresponding
	 */
	private static long askComputerId() {
		boolean computerExist;
		boolean incorrectComputer;
		long computerIdLong=0l;
		Computer computer = null;
		do {
			computerExist = true;
			incorrectComputer = false;
			System.out.print("Computer id: ");
			Boolean parsing = true;
			do{
				String computerId = scanner.nextLine();
				try {
					computerIdLong = Long.parseLong(computerId);
				}
				catch(NumberFormatException e) {
					parsing = false;
				}
			}while(!parsing);
			try {
				computer = ComputerService.findById(computerIdLong);
			} catch (NoResultException e) {
				System.out.println("computer doesn't exist");
				computerExist = false;
			}
			if(computerExist) {
				System.out.println(computer);
				System.out.println("Is this computer which you want (O/n)?");			
				if(scanner.nextLine().compareTo("n")==0) {
					incorrectComputer = true;
				}
			}
		}
		while(!computerExist || incorrectComputer);
		return computer.getId();
	}

	private static void computerDetails() {
		System.out.println("--Computer Details--");
		askComputerId();// It ask validation with details
	}

	private static void computerListing() {
		System.out.println("---List computers---");
		int nbRowsJumped = 0;
		int nbRowsReturned = 10;
		int actualPage = 1;
		List<Computer> computers;
		boolean continuer = true;
		// Page display
		do {
			computers = ComputerService.findComputersBetween(nbRowsJumped, nbRowsReturned);
			for (Computer computer : computers) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("Computer [id=").append(computer.getId()).append(", name=").append(computer.getName()).append("]");
				System.out.println(stringBuffer.toString());
			}
			int pageRequested=0;
			// Page request
			do {
				int nbComputers = Page.getNbTotalComputers();
				int nbPages = nbComputers/nbRowsReturned;
				if((nbComputers%nbRowsReturned)>0) {
					nbPages++;
				}
				System.out.print("Page " + actualPage + "/" +nbPages);
				System.out.print(". Which page do you want ('n' for next, '0' to stop or a number)?");
				String page = scanner.nextLine();
				if(page.compareTo("0")==0) {
					continuer = false;
				}
				else {
					if(page.compareTo("n")==0) {
						pageRequested = actualPage+1;
					}
					else {
						try {
							pageRequested = Integer.parseInt(page);
							if(pageRequested < 1 || pageRequested > nbPages) {
								System.out.println("Incorrect page number");
								pageRequested = 0;
							}
						}
						catch(NumberFormatException e){
							System.out.println("Incorrect page number");
						}
					}
				}
			}
			while (pageRequested == 0 && continuer);
			if(continuer) {
				actualPage = pageRequested;
				nbRowsJumped = nbRowsReturned * (pageRequested-1);
			}
			System.out.println();
		}
		while(continuer);
		System.out.println("END of listing");
	}

	private static void companyListing() {
		System.out.println("---List companies---");
		List<Company> companies = CompanyService.findAll();
		for (Company company : companies) {
			System.out.println(company);
		}
		System.out.println();
	}
}
