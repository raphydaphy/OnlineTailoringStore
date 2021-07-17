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

  <h3>New Orders</h3>
  <c:if test="${newOrders.size() == 0}">
    <c:choose>
      <c:when test="${user.category == 'customer'}">
        <p class="text-info">You don't have any new orders. If you want, you can <a href="/tailors">search for a tailor</a> and place an order.</p>
      </c:when>
      <c:otherwise>
        <p class="text-info">There are no new orders to view at this time.</p>
      </c:otherwise>
    </c:choose>
  </c:if>
  <c:forEach items="${newOrders}" var="order">
    <div class="card mt-2 mb-2">
      <div class="card-body">
        <div class="d-flex mr-2">
          <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h5 class="card-title"><a href="/order?id=${order.orderId}">${order.patternName}</a></h5>
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
            <c:if test="${user.category == 'tailor'}">
              <a href="/acceptOrder?id=${order.orderId}" class="btn btn-primary">Accept Order</a>
            </c:if>
            <a href="/deleteOrder?id=${order.orderId}" class="btn btn-secondary">Delete Order</a>
          </div>
        </div>

      </div>
    </div>
  </c:forEach>



  <h3>Active Orders</h3>
  <c:if test="${activeOrders.size() == 0}">
    <c:choose>
      <c:when test="${user.category == 'admin'}">
        <p class="text-info">There are no active orders to view at this time.</p>
      </c:when>
      <c:otherwise>
        <p class="text-info">You don't have any active orders.</p>
      </c:otherwise>
    </c:choose>
  </c:if>
  <c:forEach items="${activeOrders}" var="order">
    <div class="card mt-2 mb-2">
      <div class="card-body">
        <div class="d-flex mr-2">
          <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h5 class="card-title"><a href="/order?id=${order.orderId}">${order.patternName}</a></h5>
            <p class="card-text mt-1 mb-3">
              <c:if test="${user.category != 'customer'}">
                <strong>Created by: </strong> ${order.customerUsername}<br />
              </c:if>
              <c:if test="${user.category != 'tailor'}">
                <strong>Tailor Name: </strong> ${order.tailorUsername}<br />
              </c:if>
              <strong>Cost: </strong>$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.amount}"/><br />
              <strong>Creation Date: </strong>${order.placedDate}<br />
              <strong>Expected Delivery Date: </strong>${order.expectedDeliveryDate}<br />
              <strong>Notes: </strong>${order.orderNotes}
            </p>
            <c:if test="${user.category == 'tailor'}">
              <a href="/completeOrder?id=${order.orderId}" class="btn btn-primary">Complete Order</a>
              <a href="/updateOrder?id=${order.orderId}" class="btn btn-primary">Update Order</a>
            </c:if>
            <a href="/deleteOrder?id=${order.orderId}" class="btn btn-secondary">Delete Order</a>
          </div>
        </div>

      </div>
    </div>
  </c:forEach>



  <h3>Past Orders</h3>
  <c:if test="${completedOrders.size() == 0}">
    <c:choose>
      <c:when test="${user.category == 'admin'}">
        <p class="text-info">There are no past orders to view at this time.</p>
      </c:when>
      <c:otherwise>
        <p class="text-info">You don't have any past orders.</p>
      </c:otherwise>
    </c:choose>
  </c:if>
  <c:forEach items="${completedOrders}" var="order">
    <div class="card mt-2 mb-2">
      <div class="card-body">
        <div class="d-flex mr-2">
          <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h5 class="card-title"><a href="/order?id=${order.orderId}">${order.patternName}</a></h5>
            <p class="card-text mt-1 mb-3">
              <c:if test="${user.category != 'customer'}">
                <strong>Created by: </strong> ${order.customerUsername}<br />
              </c:if>
              <c:if test="${user.category != 'tailor'}">
                <strong>Tailor Name: </strong> ${order.tailorUsername}<br />
              </c:if>
              <strong>Cost: </strong>$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.amount}"/><br />
              <strong>Creation Date: </strong>${order.placedDate}<br />
              <strong>Delivered Date: </strong>${order.deliveredDate}<br />
              <strong>Notes: </strong>${order.orderNotes}

              <c:if test="${order.review != null}">
                <br />
                <strong>Review: </strong>${order.review}
              </c:if>
            </p>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>

</div>
<%@ include file="common/footer.jsp"%>
