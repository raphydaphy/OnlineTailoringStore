<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Do you need help? Create a ticket below.<br />
    Changed your mind? <a href="/account">Go back</a>.
  </p>

  <form:form method="post" action="/createTicket" modelAttribute="ticket">
    <div class="form-group">
      <label for="issue">Issue Title</label>
      <form:input path="issue" name="issue" id="issue" cssClass="form-control" required="true" />
    </div>
    <div class="form-group">
      <label for="description">Issue Description</label>
      <form:textarea path="description" name="description" id="description" cssClass="form-control" rows="5" required="true" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Create Ticket</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
