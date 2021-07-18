<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can view the order below, or <a href="/orders">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <div class="card mt-2 mb-2">
    <div class="card-body">
      <div class="d-flex mr-2 justify-content-between align-items-center">
        <div class="d-flex">
          <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h3 class="card-title">${order.patternName}</h3>
            <p class="card-text mt-1 mb-3">
              <c:if test="${user.category != 'customer'}">
                <strong>Created by: </strong> ${order.customerUsername}<br />
              </c:if>
              <c:if test="${user.category != 'tailor'}">
                <strong>Tailor Name: </strong> ${order.tailorUsername}<br />
              </c:if>
              <strong>Status: </strong>${order.orderStatus}<br />
              <c:if test="${order.amount > 0}">
                <strong>Cost: </strong><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.amount}"/> INR<br />
              </c:if>
              <strong>Creation Date: </strong>${order.placedDate}<br />
              <strong>Expected Delivery Date: </strong>${order.expectedDeliveryDate}
              <c:if test="${order.orderStatusId < 3}">
                <br />
                <strong>Delivery  Method: </strong>
                <c:choose>
                  <c:when test="${order.courier}">
                    Courier
                  </c:when>
                  <c:otherwise>
                    Pick Up
                  </c:otherwise>
                </c:choose>
                <br />
                <strong>Payment Method: </strong> ${order.payingOnline ? "Card" : "Cash"}
                <br />
                <strong>Paid: </strong> ${order.paid ? "Yes" : "No"}
              </c:if>
              <c:if test="${order.orderNotes.length() > 0}">
                <br />
                <strong>Notes: </strong>${order.orderNotes}
              </c:if>

              <c:if test="${order.review != null}">
                <br />
                <strong>Review: </strong>${order.review}
              </c:if>
            </p>
          </div>
        </div>
        <div class="align-items-center">
          <c:if test="${user.category == 'tailor'}">
            <c:choose>
              <c:when test="${order.orderStatusId == 1}">
                <a href="/acceptOrder?id=${order.orderId}" class="btn btn-primary btn-block">Accept Order</a>
              </c:when>
              <c:when test="${order.orderStatusId == 2}">
                <a href="/completeOrder?id=${order.orderId}" class="btn btn-primary btn-block">Complete Order</a>
                <a href="/updateOrder?id=${order.orderId}" class="btn btn-primary btn-block">Update Order</a>
              </c:when>
            </c:choose>
          </c:if>
          <c:if test="${user.category == 'customer' && order.amount > 0 && !order.paid && order.orderStatusId == 2 && order.payingOnline}">
            <a href="/pay?order=${order.orderId}" class="btn btn-primary btn-block">Make Payment</a>
          </c:if>
          <c:if test="${order.orderStatusId != 3}">
            <a href="/deleteOrder?id=${order.orderId}" class="btn btn-secondary btn-block">Delete Order</a>
          </c:if>
        </div>
      </div>
    </div>
  </div>

  <h3 class="mt-3">Measurements</h3>
  <div class="row">
    <div class="col-md">
      <strong>Neck:</strong> ${order.neck}<br />
      <strong>Waist:</strong> ${order.waist}<br />
      <strong>Chest:</strong> ${order.chest}<br />
      <strong>Hip:</strong> ${order.hip}<br />
      <strong>Knee Length:</strong> ${order.kneeLength}<br />
      <strong>Shoulder Length:</strong> ${order.shoulderLength}<br /><br />
    </div>
    <div class="col-md">
      <strong>Top Fabric:</strong> ${order.topFabric}<br />
      <strong>Top Material:</strong> ${order.topMaterial}<br />
      <strong>Top Duration:</strong> ${order.topDuration}<br />
      <strong>Top Length:</strong> ${order.topLength}<br />
      <strong>Top Quantity:</strong> ${order.topQuantity}<br /><br />
    </div>
    <div class="col-md">
      <strong>Bottom Fabric:</strong> ${order.bottomFabric}<br />
      <strong>Bottom Material:</strong> ${order.bottomMaterial}<br />
      <strong>Bottom Duration</strong> ${order.bottomDuration}<br />
      <strong>Bottom Length</strong> ${order.bottomLength}<br />
      <strong>Bottom Quantity</strong> ${order.bottomQuantity}<br /><br />
    </div>
  </div>

</div>
<%@ include file="common/footer.jsp"%>
