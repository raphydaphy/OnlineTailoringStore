<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can view your orders below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <c:if test="${orders.size() == 0}">
    <c:choose>
      <c:when test="${user.category == 'customer'}">
        <p class="text-info">You don't have any open orders. If you want, you can <a href="/tailors">search for a tailor</a> and place an order.</p>
      </c:when>
      <c:otherwise>
        <p class="text-info">There are no orders to view at this time.</p>
      </c:otherwise>
    </c:choose>
  </c:if>
  <c:forEach items="${orders}" var="order">
    <div class="card mt-2 mb-2">
      <div class="card-body">
        <div class="d-flex mr-2">
          <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h5 class="card-title">${order.patternName}</h5>
            <p class="card-text mt-1 mb-3">
              <c:if test="${user.category != 'customer'}">
                <strong>Created by: </strong> ${order.customerUsername}<br />
              </c:if>
              <c:if test="${user.category != 'tailor'}">
                <strong>Tailor Name: </strong> ${order.tailorUsername}<br />
              </c:if>
              <strong>Creation Date: </strong>${order.placedDate}<br />
              <strong>Expected Delivery Date: </strong>${order.expectedDeliveryDate}<br />
              <strong>Notes: </strong>${order.orderNotes}
            </p>
            <a href="/deleteOrder?id=${order.orderId}" class="btn btn-secondary">Delete Order</a>
          </div>
        </div>

      </div>
    </div>
  </c:forEach>


</div>
<%@ include file="common/footer.jsp"%>
