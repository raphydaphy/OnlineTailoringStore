<%@ include file="common/header.jsp" %>
<div class="container my-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Sign in to your account using to the form below.<br />
		Don't have an account? <a href="/register">Register now</a>!
	</p>

	<c:choose>

		<c:when test="${error != null}">
			<p class="text-warning">
				<c:choose>
					<c:when test="${error == 'username'}">
						Invalid username. If you've forgotten your username, you can <a href="/forgotUsername">retrieve your username here</a>.
					</c:when>
					<c:when test="${error == 'password'}">
						Incorrect password. If you've forgotten your password, you can <a href="/forgotPassword?username=${username}">reset it here</a>.
					</c:when>
					<c:otherwise>
						${error}
					</c:otherwise>
				</c:choose>
			</p>
		</c:when>
		<c:when test="${message != null}">
			<p class="text-info">${message}</p>
		</c:when>
	</c:choose>

	<form:form method="post" action="/login" modelAttribute="user">
		<div class="form-group">
			<label for="username">Username</label>
			<form:input path="username" name="username" id="username" value="${username}" cssClass="form-control" />
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<form:password path="password" name="password" id="password" cssClass="form-control" />
		</div>
		<button type="submit" class="btn btn-primary btn-block">Sign in</button>
	</form:form>

</div>
<%@ include file="common/footer.jsp"%>
