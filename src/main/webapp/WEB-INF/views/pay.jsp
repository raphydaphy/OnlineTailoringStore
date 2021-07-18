<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can pay for the order below, or <a href="/order?id=${order.orderId}">go back</a>.
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
      <div class="d-flex mr-2">
        <img src="/patternImage?patternId=${order.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
        <div>
          <h3 class="card-title">${order.patternName}</h3>
          <p class="card-text mt-1 mb-3">
            <strong>Tailor Name: </strong> ${order.tailorUsername}<br />
            <strong>Status: </strong>${order.orderStatus}<br />
            <strong>Cost: </strong><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.amount}"/> INR<br />
            <strong>Creation Date: </strong>${order.placedDate}<br />
            <strong>Expected Delivery Date: </strong>${order.expectedDeliveryDate}<br />
            <strong>Delivery  Method: </strong>
            <c:choose>
              <c:when test="${order.courier}">
                Courier
              </c:when>
              <c:otherwise>
                Pick Up
              </c:otherwise>
            </c:choose><br />
            <strong>Notes: </strong>${order.orderNotes}
          </p>
        </div>
      </div>
    </div>
  </div>

  <h3>Payment</h3>
  <form:form action="/pay" method="post" modelAttribute="payment">
    <form:hidden path="orderId" value="${order.orderId}" />
    <div class="form-group">
      <label for="cardName">Name on Card</label>
      <form:input path="cardName" required="true" cssClass="form-control" />
    </div>
    <div class="form-group">
      <label for="cardNumber">Card Number</label>
      <form:input path="cardNumber" required="true" cssClass="form-control" />
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="cardExpiry">Expiry Date</label>
        <form:input path="cardExpiry" required="true" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="cardCvv">CVV</label>
        <form:input path="cardCvv" required="true" cssClass="form-control" />
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">Pay Now</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
