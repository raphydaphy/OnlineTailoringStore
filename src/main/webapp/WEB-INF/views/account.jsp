<%@ include file="common/header.jsp" %>
<body>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You are logged in as ${user.category} <strong>${user.username}</strong>.<br />
    Hello ${user.firstName} ${user.lastName}!
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
    <c:choose>
      <c:when test="${user.category=='admin'}">
        <a class="list-group-item list-group-item-action text-primary" href="/tickets">View Support Tickets</a>
        <a class="list-group-item list-group-item-action text-primary" href="/categories">Manage Clothing Categories</a>
        <a class="list-group-item list-group-item-action text-primary" href="/dressTypes">Manage Dress Types</a>
      </c:when>
      <c:otherwise>
        <a class="list-group-item list-group-item-action text-primary" href="/createTicket">Create Support Ticket</a>
        <a class="list-group-item list-group-item-action text-primary" href="/tickets">View My Tickets</a>

        <c:choose>
          <c:when test="${user.category=='tailor'}">
            <!-- <a class="list-group-item list-group-item-action text-primary" href="/updateShop">Update Shop Details</a> -->
            <a class="list-group-item list-group-item-action text-primary" href="/dressTypes">Manage Dress Types</a>
          </c:when>
          <c:when test="${user.category=='customer'}">
            <!-- <a class="list-group-item list-group-item-action text-primary" href="/order">Create Order</a> -->
          </c:when>
        </c:choose>

      </c:otherwise>
    </c:choose>
    <a class="list-group-item list-group-item-action text-primary" href="/logout">Log Out</a>
  </div>

</div>
<%@ include file="common/footer.jsp"%>
