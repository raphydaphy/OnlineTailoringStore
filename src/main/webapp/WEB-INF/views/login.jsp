<%@ include file="common/header.jsp" %>
<div class="container mt-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Sign in to your account using to the form below.<br />
		Don't have an account? <a href="/register">Register now</a>!
	</p>

	<c:choose>
		<c:when test="${error != null}">
			<p class="text-warning">${error}</p>
		</c:when>
		<c:when test="${message != null}">
			<p class="text-info">${message}</p>
		</c:when>
	</c:choose>

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
