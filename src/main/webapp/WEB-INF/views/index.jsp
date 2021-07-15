<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="webjars/bootstrap/4.6.0/css/bootstrap.min.css" rel="stylesheet" type="text/css">

	<title>Online Tailoring Store</title>
</head>
<body>
<div class="container mt-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Sign in to your account using to the form below.<br />
		Don't have an account? <a href="/register">Register now</a>!
	</p>


	<form:form method="post" action="/login" modelAttribute="user">
		<div class="form-group">
			<label for="username">Username</label>
			<form:input path="username" name="username" id="username" cssClass="form-control" />
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<form:password path="password" name="password" id="password" cssClass="form-control" />
		</div>
		<button type="submit" class="btn btn-primary">Sign in</button>
	</form:form>

</div>

<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="webjars/popper.js/2.5.4/umd/popper.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>