<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can use the form below to reset your password, or <a href="/login">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/resetPassword" modelAttribute="securityQuestions">
    <form:hidden path="username" value="${username}"/>
    <form:hidden path="firstQuestion.promptId" value="${questions[0].promptId}"/>
    <form:hidden path="firstQuestion.answer" value="${questions[0].answer}"/>
    <form:hidden path="secondQuestion.promptId" value="${questions[1].promptId}"/>
    <form:hidden path="secondQuestion.answer" value="${questions[1].answer}"/>
    <form:hidden path="thirdQuestion.promptId" value="${questions[2].promptId}"/>
    <form:hidden path="thirdQuestion.answer" value="${questions[2].answer}"/>

    <div class="form-group">
      <label for="mockUsername">Username</label>
      <input id="mockUsername" value="${username}" class="form-control" disabled="true" />
    </div>
    <div class="form-group">
      <label for="newPassword">Password</label>
      <form:password path="newPassword" name="newPassword" id="newPassword" cssClass="form-control" required="true" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Reset Password</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
