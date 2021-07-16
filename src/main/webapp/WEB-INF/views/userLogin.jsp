<%@ include file="common/header.jsp" %>
<div class="container mt-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Sign in to your account using to the form below.<br />
		Don't have an account? <a href="/userRegister">Register now</a>!
	</p>

	<%
		String error = (String) request.getAttribute("error");
		if(error != null) {
			out.print("<p class='text-warning'>" + error + "</p>");
		}
	%>

	<form:form method="post" action="/userSuccessLogin" modelAttribute="user">
		<div class="form-group">
			<label for="username">Username</label>
			<form:input path="username" name="username" id="username" cssClass="form-control" />
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<form:password path="password" name="password" id="password" cssClass="form-control" />
		</div>
		<button type="submit" class="btn btn-primary btn-block">Sign in</button>
	</form:form>

</div>
<%@ include file="common/footer.jsp"%>
