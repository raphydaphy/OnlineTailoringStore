<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can search for tailor shops below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/tailors" modelAttribute="tailorShopSearch">
    <div class="row align-items-center">
      <div class="row col-md flex-grow-1 align-items-center">
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:input path="area" name="area" id="area" cssClass="form-control" placeholder="Area" />
        </div>
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:select path="dressType.dressTypeId" name="dressTypeId" id="dressTypeId" cssClass="form-control" required="true">
            <form:option value="-1">Any Dress Type</form:option>
            <c:forEach items="${dressTypes}" var="dressType">
              <form:option value="${dressType.dressTypeId}">${dressType.name} (${dressType.subcategoryName})</form:option>
            </c:forEach>
          </form:select>
        </div>
      </div>
      <div class="col-md-3 align-items-center mb-2 mb-md-3">
        <button type="submit" class="btn btn-primary btn-block">Search Tailor Shops</button>
      </div>
    </div>
  </form:form>

  <c:if test="${tailorShops.size() == 0}">
    <p class="text-info">We couldn't find any tailor shops that matched your search!</p>
  </c:if>
  <ul class="list-group">
    <c:forEach items="${tailorShops}" var="shop">
      <li class="list-group-item">
        <h3><a href="/tailor?id=${shop.tailorUsername}">${shop.name}</a></h3>
        <p>
          <strong>Address:</strong> ${shop.address}<br />
          <strong>Contact Number: </strong> ${shop.contactNumber}<br />
          <strong>Working Hours: </strong> ${shop.workingHours}<br />
          <strong>Available Services: </strong> ${shop.availableServices}<br />
          <strong>Courier Available: </strong>
          <c:choose>
            <c:when test="${shop.courierAvailable}">
              Yes
            </c:when>
            <c:otherwise>
              No
            </c:otherwise>
          </c:choose>
        </p>
      </li>
    </c:forEach>
  </ul>

</div>
<%@ include file="common/footer.jsp"%>
