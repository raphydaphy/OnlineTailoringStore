<%@ include file="common/header.jsp" %>
<div class="container my-3">
	<h1>Online Tailoring Store</h1>
	<p>
		Welcome to the Online Tailoring Store!<br />
		Select an option below to get started.
	</p>


	<c:choose>
		<c:when test="${error != null}">
			<p class="text-warning">${error}</p>
		</c:when>
		<c:when test="${message != null}">
			<p class="text-info">${message}</p>
		</c:when>
	</c:choose>

	<div class="list-group">
		<a class="list-group-item list-group-item-action text-primary" href="/login">Login</a>
		<a class="list-group-item list-group-item-action text-primary" href="/register">User Registration</a>
		<a class="list-group-item list-group-item-action text-primary" href="/adminRegister">Admin Registration</a>
	</div>

</div>
<%@ include file="common/footer.jsp"%>
