<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Create an order with ${shop.name} below, or <a href="/tailor?id=${shop.tailorUsername}">go back</a>.
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
            <strong>Cost: </strong>$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${orderPattern.cost}"/><br />
            <strong>Category: </strong>${orderPattern.category.name}<br />
            <strong>Subcategory: </strong>${orderPattern.dressType.subcategoryName}<br />
            <strong>Dress Type: </strong>${orderPattern.dressType.name}
          </p>
        </div>
      </div>

    </li>
  </ul>
  <form:form method="post" action="/makeOrder" modelAttribute="order">
    <form:hidden path="customerUsername" value="${user.username}" />
    <form:hidden path="tailorUsername" value="${shop.tailorUsername}" />
    <form:hidden path="patternId" value="${orderPattern.patternId}" />
    <div class="row">
      <div class="col-sm form-group">
        <label for="expectedDeliveryDate">Expected Delivery Date</label>
        <div class="input-group date" data-provide="datepicker">
          <form:input cssClass="form-control" path="expectedDeliveryDate" required="true" />
          <div class="input-group-addon">
            <span class="glyphicon glyphicon-th"></span>
          </div>
        </div>
      </div>
      <c:if test="${shop.courierAvailable}">
        <div class="col-sm form-group">
          <label for="courier">Delivery Method</label>
          <form:select path="courier" cssClass="form-control" required="true">
            <option value="1">Courier</option>
            <option value="0">Pickup</option>
          </form:select>
        </div>
      </c:if>
      <div class="col-sm form-group">
        <label for="payingOnline">Payment Method</label>
        <form:select path="payingOnline" cssClass="form-control" required="true">
          <option value="1">Card</option>
          <option value="0">Cash</option>
        </form:select>
      </div>
    </div>

    <div class="row">
      <div class="col-sm form-group">
        <label for="topFabric">Top Fabric</label>
        <form:input path="topFabric" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="topMaterial">Top Material</label>
        <form:input path="topMaterial" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="topDuration">Top Duration</label>
        <form:input path="topDuration" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="topLength">Top Length</label>
        <form:input path="topLength" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="topQuantity">Top Quantity</label>
        <form:input path="topQuantity" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="bottomFabric">Bottom Fabric</label>
        <form:input path="bottomFabric" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomMaterial">Bottom Material</label>
        <form:input path="bottomMaterial" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomDuration">Bottom Duration</label>
        <form:input path="bottomDuration" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="bottomQuantity">Bottom Quantity</label>
        <form:input path="bottomQuantity" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="bottomLength">Bottom Length</label>
        <form:input path="bottomLength" cssClass="form-control" required="true" />
      </div>
    </div>

    <div class="row">
      <div class="col-sm form-group">
        <label for="neck">Neck</label>
        <form:input path="neck" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="waist">Waist</label>
        <form:input path="waist" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="chest">Chest</label>
        <form:input path="chest" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="hip">Hip</label>
        <form:input path="hip" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="kneeLength">Knee Length</label>
        <form:input path="kneeLength" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="shoulderLength">Shoulder Length</label>
        <form:input path="shoulderLength" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="form-group">
      <label for="orderNotes">Additional Notes</label>
      <form:textarea path="orderNotes" cssClass="form-control" rows="5" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Place Order</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
