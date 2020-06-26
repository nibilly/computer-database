package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet(name = "ComputerListServlet", urlPatterns = "/computer-list")
public class ComputerListServlet extends HttpServlet {

	private static final long serialVersionUID = 6587399260408067529L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		int nbComputers = ComputerService.getNbComputers();
		request.setAttribute("nbComputers", nbComputers+"");
		Page<Computer> page = new Page<Computer>(1, 0);
		ComputerService.findComputersPages(page);
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		for (Computer computer : page.getEntities()) {
			computers.add(ComputerMapper.mapComputerDTO(computer));
		}
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
