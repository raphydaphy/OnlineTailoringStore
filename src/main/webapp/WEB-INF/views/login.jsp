<%@ include file="common/header.jsp" %>
<div class="container mt-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Sign in to your account using to the form below.<br />
		Don't have an account? <a href="/register">Register now</a>!
	</p>

	<%
		String error = (String) request.getAttribute("error");
		String message = (String) request.getAttribute("message");
		if(error != null) {
			out.print("<p class='text-warning'>" + error + "</p>");
		} else if (message != null) {
			out.print("<p class='text-info'>" + message + "</p>");
		}
	%>

	<form:form method="post" action="/login" modelAttribute="user">
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
