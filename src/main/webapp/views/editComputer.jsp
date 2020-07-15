<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: 
                        <c:out value="${computerId}"/>
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST" id="computer-form">
                        <input name="id" type="hidden" id="id" value="${computerId}">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input name="name" type="text" class="form-control" id="computerName" placeholder="Computer name" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input name="introduced" type="date" class="form-control" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input name="discontinued" type="date" class="form-control" id="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select name="company" class="form-control" id="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <c:out value="${error}"/>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="js/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
	<script src="js/computerValidation.js"></script>
</body>
</html>