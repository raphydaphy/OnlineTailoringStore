<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    If you've forgotten your username, enter your email or contact number below and we'll help you retrieve it.<br />
    Changed your mind? <a href="/login">Go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/forgotUsername" modelAttribute="user">
    <div class="form-group">
      <label for="email">Email Address</label>
      <form:input type="email" path="email" name="email" id="email" cssClass="form-control" />
    </div>
    <div class="form-group">
      <label for="contactNumber">Contact Number</label>
      <form:input path="contactNumber" name="contactNumber" id="contactNumber" cssClass="form-control" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Retrieve Username</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
