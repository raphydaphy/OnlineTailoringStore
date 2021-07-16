<%@ include file="common/header.jsp" %>
<body>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You are logged in as ${user.username}.<br />
    Hello ${user.firstName} ${user.lastName}!

    <c:choose>
      <c:when test="${user.category=='admin'}">
        You are an admin
      </c:when>
      <c:when test="${user.category=='tailor'}">
        You are a tailor
      </c:when>
      <c:when test="${user.category=='customer'}">
        You are a customer
      </c:when>
    </c:choose>
  </p>

</div>
<%@ include file="common/footer.jsp"%>
