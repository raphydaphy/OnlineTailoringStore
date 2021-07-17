<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can respond to the ticket below, or <a href="/tickets">go back</a>.
  </p>

  <div class="card mt-2 mb-2">
    <div class="card-body">
      <h5 class="card-title">${ticket.issue}</h5>
      <p class="card-text mt-1 mb-1">
        <strong>Created by: </strong> ${ticket.user.username}<br />
        <strong>Creation Date: </strong>${ticket.date}<br />
        <strong>Contact Number: </strong> ${ticket.user.contactNumber}<br />
        <strong>Description: </strong>${ticket.description}
      </p>
    </div>
  </div>

  <form:form method="post" action="/ticketRespond" modelAttribute="ticket">
    <form:hidden path="ticketId" name="ticketId" id="ticketId" value="${ticket.ticketId}" />
    <div class="form-group">
      <label for="response">Your Response</label>
      <form:textarea path="response" name="response" id="response" cssClass="form-control" rows="5" required="true" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Send Response</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
