<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Please answer the security questions below to prove your identity, or <a href="/login">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/answerSecurityQuestions" modelAttribute="securityQuestions">
    <form:hidden path="username" name="username" id="username" value="${user.username}" />
    <form:hidden path="email" name="email" id="email" value="${user.email}" />
    <form:hidden path="contactNumber" name="contactNumber" id="contactNumber" value="${user.contactNumber}" />

    <div class="form-group">
      <label for="firstAnswer">${questions[0].question}</label>
      <form:hidden path="firstQuestion.promptId" name="firstPromptId" id="firstPromptId" value="${questions[0].promptId}" />
      <form:input path="firstQuestion.answer" name="firstAnswer" id="firstAnswer" cssClass="form-control" required="true" />
    </div>

    <div class="form-group">
      <label for="secondAnswer">${questions[1].question}</label>
      <form:hidden path="secondQuestion.promptId" name="secondPromptId" id="secondPromptId" value="${questions[1].promptId}" />
      <form:input path="secondQuestion.answer" name="secondAnswer" id="secondAnswer" cssClass="form-control" required="true" />
    </div>

    <div class="form-group">
      <label for="thirdAnswer">${questions[2].question}</label>
      <form:hidden path="thirdQuestion.promptId" name="thirdPromptId" id="thirdPromptId" value="${questions[2].promptId}" />
      <form:input path="thirdQuestion.answer" name="thirdAnswer" id="thirdAnswer" cssClass="form-control" required="true" />
    </div>

    <button type="submit" class="btn btn-primary btn-block">Submit Answers</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
