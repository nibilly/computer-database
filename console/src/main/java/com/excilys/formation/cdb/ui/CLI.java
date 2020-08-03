package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Command Line Interface
 * 
 * @author nbilly
 *
 */
public class CLI {
	private static Logger logger = LoggerFactory.getLogger(CLI.class);
	private Scanner scanner;
	private DateTimeFormatter dateTimeFormatter;
	private boolean continuerMenu;
	private boolean continuerPageRequest;

	private ComputerService computerService;
	private CompanyService companyService;

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * Create a scanner, while user continue to respond call menu else close scanner
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("console-beans.xml");
		CLI cli = (CLI) app.getBean("CLI");
		cli.moteur();
	}

	public void moteur() {
		logger.info("Début CLI");
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		scanner = new Scanner(System.in);
		continuerMenu = true;
		while (continuerMenu) {
			menu();
		}
		scanner.close();
		logger.info("Fin CLI");
	}

	/**
	 * Print the menu. User choice call a function or stop the program
	 */
	private void menu() {

		System.out.println("--------CLI---------");// 20 characters
		System.out.println("0)Quit");
		System.out.println("1)List computers");
		System.out.println("2)List companies");
		System.out.println("3)Show computer details");
		System.out.println("4)Create a computer");
		System.out.println("5)Update a computer");
		System.out.println("6)Delete a computer");
		System.out.println("7)Delete a company");
		System.out.println("--------------------");
		System.out.print("Your choice : ");
		String choice = scanner.nextLine();
		System.out.println();
		switch (choice) {
		case "0":
			continuerMenu = false;
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
		case "7":
			deleteCompany();
		default:
			System.out.println("Choix incorrect");
			break;
		}
	}

	private void deleteCompany() {
		System.out.println("--Delete a company--");
		boolean continuer;
		Company company;
		do {
			continuer = false;
			System.out.print("CompanyId: ");
			String line = scanner.nextLine();
			long companyId = Long.parseLong(line);
			company = companyService.findById(companyId);
			if (company == null) {
				System.out.println("Wrong company");
				continuer = true;
			}
		} while (continuer);
		System.out.println(company);
		System.out.print("Do you want to delete this company and its computers(N/o)? ");
		if (scanner.nextLine().equals("o")) {
			companyService.delete(company.getId());
		}
	}

	private void deleteComputer() {
		System.out.println("-Delete a computer--");
		long computerId = askComputerId();// this method already ask for validation
		computerService.deleteComputerById(computerId);
	}

	private void updateComputer() {
		System.out.println("-Update a computer--");
		long computerId = askComputerId();
		System.out.println("New informations for this computer: ");
		Computer computer = askComputerInformations();
		computer.setId(computerId);
		computerService.updateComputer(computer);
	}

	private void createComputer() {
		System.out.println("-Create a computer--");
		Computer computer = askComputerInformations();
		computerService.createComputer(computer);
	}

	/**
	 * ask computer informations until user give valid inputs.
	 * 
	 * @return a computer without id
	 */
	private Computer askComputerInformations() {
		boolean continuer;
		String name = null;
		String introduced = null;
		LocalDate introducedDate = null;
		String discontinued = null;
		LocalDate discontinuedDate = null;
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
			introducedDate = null;
			discontinuedDate = null;
			companyIdLong = 0l;
			if (name.equals("")) {
				System.out.println("Name incorrect");
				continuer = true;
			}
			if (!introduced.equals("null")) {
				try {
					introducedDate = LocalDate.parse(introduced, dateTimeFormatter);
				} catch (DateTimeParseException e) {
					System.out.println("Introduced date incorrect");
					continuer = true;
				}
			}
			if (!discontinued.equals("null")) {
				try {
					discontinuedDate = LocalDate.parse(discontinued, dateTimeFormatter);
				} catch (DateTimeParseException e) {
					System.out.println("Discontinued date incorrect");
					continuer = true;
				}
			}
			if (!companyId.equals("null")) {
				try {
					companyIdLong = Long.parseLong(companyId);
				} catch (NumberFormatException e) {
					System.out.println("CompanyId number is not a number");
					continuer = true;
				}
			}
			if (introducedDate != null && discontinuedDate != null) {
				if (introducedDate.compareTo(discontinuedDate) >= 0) {
					System.out.println("Introduced date can't be upper or equals to discontinued date");
					continuer = true;
				}
			}
		} while (continuer);
		Company company = companyService.findById(companyIdLong);
		return new Computer(name, introducedDate, discontinuedDate, company);
	}

	/**
	 * Ask for computerId until this computer exist
	 * 
	 * @return the computer corresponding
	 */
	private long askComputerId() {
		boolean computerExist;
		boolean incorrectComputer;
		long computerIdLong = 0l;
		Computer computer = null;
		do {
			computerExist = true;
			incorrectComputer = false;
			System.out.print("Computer id: ");
			Boolean parsing = true;
			do {
				String computerId = scanner.nextLine();
				try {
					computerIdLong = Long.parseLong(computerId);
				} catch (NumberFormatException e) {
					parsing = false;
				}
			} while (!parsing);
			try {
				computer = computerService.findById(computerIdLong);
			} catch (NoResultException e) {
				System.out.println("computer doesn't exist");
				computerExist = false;
			}
			if (computerExist) {
				System.out.println(computer);
				System.out.println("Is this computer which you want (O/n)?");
				if (scanner.nextLine().equals("n")) {
					incorrectComputer = true;
				}
			}
		} while (!computerExist || incorrectComputer);
		return computer.getId();
	}

	private void computerDetails() {
		System.out.println("--Computer Details--");
		askComputerId();// It ask validation with details
	}

	private void computerListing() {
		System.out.println("---List computers---");
		int nbRowsJumped = 0;
		Page<Computer> actualPage = new Page<Computer>(0);
		continuerPageRequest = true;
		// Page display
		do {
			computerService.findComputersPages(actualPage);
			for (Computer computer : actualPage.getEntities()) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("Computer [id=").append(computer.getId()).append(", name=")
						.append(computer.getName()).append("]");
				System.out.println(stringBuffer.toString());
			}
			int pageRequested = 0;
			// Page request
			do {
				int nbComputers = computerService.getNbComputers();
				int nbPages = nbComputers / Page.NB_ROWS_RETURNED_DEFAULT;
				if ((nbComputers % Page.NB_ROWS_RETURNED_DEFAULT) > 0) {
					nbPages++;
				}
				pageRequested = pageRequest(nbPages, actualPage);
			} while (pageRequested == 0 && continuerPageRequest);
			if (continuerPageRequest) {
				actualPage = new Page<Computer>(pageRequested - 1);
			}
			System.out.println();
		} while (continuerPageRequest);
		System.out.println("END of listing");
	}

	private int pageRequest(int nbPages, @SuppressWarnings("rawtypes") Page actualPage) {
		int pageRequested = 0;
		System.out.print("Page " + actualPage.getPageNumber() + "/" + nbPages);
		System.out.print(". Which page do you want ('n' for next, '0' to stop or a number)?");
		String page = scanner.nextLine();
		if (page.equals("0")) {
			continuerPageRequest = false;
		} else {
			if (page.equals("n")) {
				if (actualPage.getPageNumber() >= nbPages) {
					System.out.println("Last page reached");
				} else {
					pageRequested = actualPage.getPageNumber() + 1;
				}
			} else {
				try {
					pageRequested = Integer.parseInt(page);
					if (pageRequested < 1 || pageRequested > nbPages) {
						System.out.println("Incorrect page number");
						pageRequested = 0;
					}
				} catch (NumberFormatException e) {
					System.out.println("Incorrect page number");
				}
			}
		}
		return pageRequested;
	}

	private void companyListing() {
		System.out.println("---List companies---");

		Page<Company> actualPage = new Page<Company>(0);
		continuerPageRequest = true;
		// Page display
		do {
			companyService.findCompanyPages(actualPage);
			for (Company company : actualPage.getEntities()) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("Company [id=").append(company.getId()).append(", name=").append(company.getName())
						.append("]");
				System.out.println(stringBuffer.toString());
			}
			int pageRequested = 0;
			// Page request
			do {
				int nbCompanies = companyService.getNbCompanies();
				int nbPages = nbCompanies / Page.NB_ROWS_RETURNED_DEFAULT;
				if ((nbCompanies % Page.NB_ROWS_RETURNED_DEFAULT) > 0) {
					nbPages++;
				}
				pageRequested = pageRequest(nbPages, actualPage);
			} while (pageRequested == 0 && continuerPageRequest);
			if (continuerPageRequest) {
				actualPage = new Page<Company>(pageRequested - 1);
			}
			System.out.println();
		} while (continuerPageRequest);
		System.out.println("END of listing");

	}
}
