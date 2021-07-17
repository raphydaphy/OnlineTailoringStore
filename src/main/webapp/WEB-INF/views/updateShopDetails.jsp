<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can update your tailor shop details below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/updateShopDetails" modelAttribute="tailorShop">
    <div class="form-group">
      <label for="name">Shop Name</label>
      <form:input path="name" name="name" id="name" cssClass="form-control" required="true" value="${shop.name}"/>
    </div>
    <div class="form-group">
      <label for="address">Address</label>
      <form:input path="address" name="address" id="address" cssClass="form-control" required="true" value="${shop.address}" />
    </div>
    <div class="form-group">
      <label for="availableServices">Available Services</label>
      <form:input type="availableServices" path="availableServices" name="availableServices" id="availableServices" cssClass="form-control" required="true" value="${shop.availableServices}" />
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="contactNumber">Contact Number</label>
        <form:input path="contactNumber" name="contactNumber" id="contactNumber" cssClass="form-control" required="true" value="${shop.contactNumber}"/>
      </div>
      <div class="col-sm form-group">
        <label for="workingHours">Working Hours</label>
        <form:input path="workingHours" name="workingHours" id="workingHours" cssClass="form-control" required="true" value="${shop.workingHours}" />
      </div>
    </div>
    <div class="form-group">
      <div class="form-check">
        <c:choose>
          <c:when test="${shop.courierAvailable}">
            <form:checkbox path="courierAvailable" name="courierAvailable" id="courierAvailable" cssClass="form-check-input" checked="true" />
          </c:when>
          <c:otherwise>
            <form:checkbox path="courierAvailable" name="courierAvailable" id="courierAvailable" cssClass="form-check-input" />
          </c:otherwise>
        </c:choose>
        <label class="form-check-label" for="courierAvailable">Courier Available</label>
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">Update Shop Details</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
