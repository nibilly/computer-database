<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${nbComputers}" />
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="add-computer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="dashboard?search=${search}&page=1&orderBy=computer">Computer name</a></th>
						<th><a href="dashboard?search=${search}&page=1&orderBy=introduced">Introduced date</a></th>
						<!-- Table header for Discontinued Date -->
						<th><a href="dashboard?search=${search}&page=1&orderBy=discontinued">Discontinued date</a></th>
						<!-- Table header for Company -->
						<th><a href="dashboard?search=${search}&page=1&orderBy=company">Company</a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${page.entities}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer?computerId=${computer.id}" onclick=""><c:out
										value="${computer.name}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.companyName}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${page.pageNumber gt 1}">
					<li><a href="dashboard?search=${search}&page=${page.pageNumber - 1}&orderBy=${orderBy}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:set var="pageIterator" value="${page.pageNumber - 2}" />
				<c:if test="${pageIterator lt 2}">
					<c:set var="pageIterator" value="1" />
				</c:if>
				<c:if test="${nbPages gt 5}">
					<c:if test="${pageIterator gt (nbPages - 4)}">
						<c:set var="pageIterator" value="${nbPages - 4}" />
					</c:if>
				</c:if>
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(pageIterator + i) lt (nbPages+1)}">
						<c:choose>
							<c:when test="${(pageIterator + i) eq page.pageNumber }">
								<li class="active"><a
									href="dashboard?search=${search}&page=${pageIterator+i}&orderBy=${orderBy}"><c:out
											value="${pageIterator+i}"></c:out></a></li>
							</c:when>
							<c:otherwise>
								<li><a href="dashboard?search=${search}&page=${pageIterator+i}&orderBy=${orderBy}"><c:out
											value="${pageIterator+i}"></c:out></a></li>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>

				<c:if test="${page.pageNumber lt nbPages}">
					<li><a href="dashboard?search=${search}&page=${page.pageNumber + 1}&orderBy=${orderBy}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:choose>
					<c:when test="${page.getNbRowsReturned() eq 10}">
						<button type="button" class="btn btn-default active"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=10'">10</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=10'">10</button>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${page.getNbRowsReturned() eq 50}">
						<button type="button" class="btn btn-default active"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=50'">50</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=50'">50</button>
					</c:otherwise>
				</c:choose><c:choose>
					<c:when test="${page.getNbRowsReturned() eq 100}">
						<button type="button" class="btn btn-default active"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=100'">100</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onClick="window.location.href='dashboard?search=${search}&orderBy=${orderBy}&nbRowsReturned=100'">100</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>