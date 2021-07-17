<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can view the tailor's portfolio below, or <a href="/tailors">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <h3>${shop.name}</h3>
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

  <h3>Patterns</h3>

  <c:if test="${patterns.size() == 0}">
    <p class="text-info">This tailor hasn't uploaded any patterns!</p>
  </c:if>
  <ul class="list-group">
    <c:forEach items="${patterns}" var="pattern">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <div class="d-flex">
          <img src="/patternImage?patternId=${pattern.patternId}" class="mr-3 flex-shrink-0" width="180px" height="auto" />
          <div>
            <h3 class="h5 mt-0">${pattern.name}</h3>
            <p>${pattern.description}</p>
            <p>
              <strong>Cost: </strong>$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${pattern.cost}"/><br />
              <strong>Category: </strong>${pattern.category.name}<br />
              <strong>Subcategory: </strong>${pattern.dressType.subcategoryName}<br />
              <strong>Dress Type: </strong>${pattern.dressType.name}
            </p>
          </div>
        </div>
        <a href="/makeOrder?tailor=${shop.tailorUsername}&pattern=${pattern.patternId}" class="btn btn-primary mb-3">Order</a>
      </li>
    </c:forEach>
  </ul>


</div>
<%@ include file="common/footer.jsp"%>
