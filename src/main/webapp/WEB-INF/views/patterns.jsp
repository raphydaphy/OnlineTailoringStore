<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can manage your patterns below, <a href="/uploadPattern">upload a new pattern</a>, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <c:if test="${patterns.size() == 0}">
    <p class="text-info">You haven't created any patterns yet!</p>
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
        <div class="v-flex">
          <a class="btn btn-primary btn-block" href="/updatePattern?patternId=${pattern.patternId}">Update</a>
          <a class="btn btn-secondary btn-block" href="/deletePattern?patternId=${pattern.patternId}">Delete</a>
        </div>
      </li>
    </c:forEach>
  </ul>


</div>
<%@ include file="common/footer.jsp"%>
