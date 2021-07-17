<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Please create some security questions below to secure your new account.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/createSecurityQuestions" modelAttribute="securityQuestions">
    <div class="row">
      <div class="col-sm form-group">
        <label for="firstPrompt">Question</label>
        <form:select path="firstQuestion.promptId" name="firstPrompt" id="firstPrompt" cssClass="form-control" required="true">
          <option value="" selected="selected" disabled="disabled">Select a Question</option>
          <c:forEach items="${prompts}" var="prompt">
            <form:option value="${prompt.promptId}">${prompt.question}</form:option>
          </c:forEach>
        </form:select>
      </div>
      <div class="col-sm form-group">
        <label for="firstAnswer">Your Answer</label>
        <form:input path="firstQuestion.answer" name="firstAnswer" id="firstAnswer" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="secondPrompt">Question</label>
        <form:select path="secondQuestion.promptId" name="secondPrompt" id="secondPrompt" cssClass="form-control" required="true">
          <option value="" selected="selected" disabled="disabled">Select a Question</option>
          <c:forEach items="${prompts}" var="prompt">
            <form:option value="${prompt.promptId}">${prompt.question}</form:option>
          </c:forEach>
        </form:select>
      </div>
      <div class="col-sm form-group">
        <label for="secondAnswer">Your Answer</label>
        <form:input path="secondQuestion.answer" name="secondAnswer" id="secondAnswer" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="thirdPrompt">Question</label>
        <form:select path="thirdQuestion.promptId" name="thirdPrompt" id="thirdPrompt" cssClass="form-control" required="true">
          <option value="" selected="selected" disabled="disabled">Select a Question</option>
          <c:forEach items="${prompts}" var="prompt">
            <form:option value="${prompt.promptId}">${prompt.question}</form:option>
          </c:forEach>
        </form:select>
      </div>
      <div class="col-sm form-group">
        <label for="thirdAnswer">Your Answer</label>
        <form:input path="thirdQuestion.answer" name="thirdAnswer" id="thirdAnswer" cssClass="form-control" required="true" />
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">Save Security Questions</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
