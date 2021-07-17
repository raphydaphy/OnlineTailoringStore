<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    ${shop.name} has completed your order! Please write a review below before continuing.
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
            <strong>Cost: </strong>$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.amount}"/><br />
            <strong>Creation Date: </strong>${order.placedDate}<br />
            <strong>Delivery Date: </strong>${order.deliveredDate}<br />
          </p>
        </div>
      </div>
    </div>
  </div>

  <form:form method="post" action="/writeReview" modelAttribute="review">
    <form:hidden path="orderId" value="${order.orderId}" />
    <div class="form-group">
      <label for="review">Your Review</label>
      <form:textarea path="review" cssClass="form-control" rows="5" required="true" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Submit Review</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
