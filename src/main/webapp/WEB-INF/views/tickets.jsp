<%@ include file="common/header.jsp" %>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can view your tickets below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <c:if test="${tickets.size() == 0}">
    <c:choose>
      <c:when test="${user.category == 'admin'}">
        <p class="text-info">There are no tickets to view at this time.</p>
      </c:when>
      <c:otherwise>
        <p class="text-info">You don't have any active support tickets. If you want, you can <a href="/createTicket">open a new ticket</a>.</p>
      </c:otherwise>
    </c:choose>
  </c:if>
  <c:forEach items="${tickets}" var="ticket">
    <div class="card mt-2 mb-2">
      <div class="card-body">
        <h5 class="card-title">${ticket.issue}</h5>
        <p class="card-text mt-1 mb-3">
          <c:if test="${user.category == 'admin'}">
            <strong>Created by: </strong> ${ticket.user.username}<br />
          </c:if>
          <strong>Creation Date: </strong>${ticket.date}<br />
          <c:if test="${user.category == 'admin'}">
            <strong>Contact Number: </strong> ${ticket.user.contactNumber}<br />
          </c:if>
          <strong>Description: </strong>${ticket.description}
        </p>
        <c:if test="${user.category == 'admin'}">
          <a href="/resolveTicket?ticketId=${ticket.ticketId}" class="btn btn-primary">Respond</a>
        </c:if>
        <a href="/closeTicket?ticketId=${ticket.ticketId}" class="btn btn-secondary">Close Ticket</a>
      </div>
    </div>
  </c:forEach>


</div>
<%@ include file="common/footer.jsp"%>
