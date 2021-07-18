<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    <c:choose>
      <c:when test="${mode == 'accept'}">
        You can adjust the order below before accepting it, or <a href="/order?id=${order.orderId}">go back</a>.
      </c:when>
      <c:otherwise>
        You can update the selected order below, or <a href="/order?id=${order.orderId}">go back</a>.
      </c:otherwise>
    </c:choose>
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
  </c:choose>

  <ul class="list-group mb-3">
    <li class="list-group-item">
      <div class="d-flex">
        <img src="/patternImage?patternId=${orderPattern.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
        <div>
          <h3 class="h5 mt-0">${orderPattern.name}</h3>
          <p>${orderPattern.description}</p>
          <p>
            <strong>Ordered by: </strong> ${order.customerUsername}<br />
            <strong>Order Date: </strong>${order.placedDate}<br />
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
            <c:if test="${order.orderNotes.length() > 0}">
              <br />
              <strong>Notes: </strong>${order.orderNotes}
            </c:if>
          </p>
        </div>
      </div>

    </li>
  </ul>
  <form:form method="post" action="${mode == 'accept' ? '/acceptOrder' : '/updateOrder'}" modelAttribute="order">
    <form:hidden path="orderId" value="${order.orderId}" />

    <div class="row">

      <div class="col-sm form-group">
        <label for="expectedDeliveryDate">Expected Delivery Date</label>
        <div class="input-group date" data-provide="datepicker">
          <form:input cssClass="form-control" path="expectedDeliveryDate" required="true" value="${order.expectedDeliveryDate}" />
          <div class="input-group-addon">
            <span class="glyphicon glyphicon-th"></span>
          </div>
        </div>
      </div>

      <div class="col-sm form-group">
        <label for="amount">Order Cost</label>
        <div class="input-group">
          <form:input path="amount" cssClass="form-control" required="true" type="number" step="0.01" value="${orderPattern.cost}" />
          <div class="input-group-append">
            <div class="input-group-text">INR</div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="topFabric">Top Fabric</label>
        <form:input path="topFabric" cssClass="form-control" required="true" value="${order.topFabric}" />
      </div>
      <div class="col-sm form-group">
        <label for="topMaterial">Top Material</label>
        <form:input path="topMaterial" cssClass="form-control" required="true" value="${order.topMaterial}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="topDuration">Top Duration</label>
        <form:input path="topDuration" cssClass="form-control" required="true" value="${order.topDuration}" />
      </div>
      <div class="col-sm form-group">
        <label for="topLength">Top Length</label>
        <form:input path="topLength" cssClass="form-control" required="true" value="${order.topLength}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="topQuantity">Top Quantity</label>
        <form:input path="topQuantity" cssClass="form-control" required="true" value="${order.topQuantity}" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomFabric">Bottom Fabric</label>
        <form:input path="bottomFabric" cssClass="form-control" required="true" value="${order.bottomFabric}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="bottomMaterial">Bottom Material</label>
        <form:input path="bottomMaterial" cssClass="form-control" required="true" value="${order.bottomMaterial}" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomDuration">Bottom Duration</label>
        <form:input path="bottomDuration" cssClass="form-control" required="true" value="${order.bottomDuration}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="bottomQuantity">Bottom Quantity</label>
        <form:input path="bottomQuantity" cssClass="form-control" required="true" value="${order.bottomQuantity}" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomLength">Bottom Length</label>
        <form:input path="bottomLength" cssClass="form-control" required="true" value="${order.bottomLength}" />
      </div>
    </div>

    <div class="row">
      <div class="col-sm form-group">
        <label for="neck">Neck</label>
        <form:input path="neck" cssClass="form-control" required="true" value="${order.neck}" />
      </div>
      <div class="col-sm form-group">
        <label for="waist">Waist</label>
        <form:input path="waist" cssClass="form-control" required="true" value="${order.waist}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="chest">Chest</label>
        <form:input path="chest" cssClass="form-control" required="true" value="${order.chest}" />
      </div>
      <div class="col-sm form-group">
        <label for="hip">Hip</label>
        <form:input path="hip" cssClass="form-control" required="true" value="${order.hip}" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="kneeLength">Knee Length</label>
        <form:input path="kneeLength" cssClass="form-control" required="true" value="${order.kneeLength}" />
      </div>
      <div class="col-sm form-group">
        <label for="shoulderLength">Shoulder Length</label>
        <form:input path="shoulderLength" cssClass="form-control" required="true" value="${order.shoulderLength}" />
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">
      <c:choose>
        <c:when test="${mode == 'accept'}">
          Accept Order
        </c:when>
        <c:otherwise>
          Update Order
        </c:otherwise>
      </c:choose>
    </button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
