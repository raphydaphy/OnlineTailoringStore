<%@ include file="common/header.jsp" %>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can manage the dress types below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/createDressType" modelAttribute="dressType">
    <div class="row align-items-center">
      <div class="row col-md flex-grow-1 align-items-center">
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:input path="name" name="name" id="name" cssClass="form-control" placeholder="Dress Type" required="true" />
        </div>
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:select path="subcategoryId" name="subcategoryId" id="subcategoryId" cssClass="form-control" required="true">
            <c:forEach items="${subcategories}" var="subcategory">
              <form:option value="${subcategory.subcategoryId}">${subcategory.subcategoryName}</form:option>
            </c:forEach>
          </form:select>
        </div>
      </div>
      <div class="col-md-3 align-items-center mb-2 mb-md-3">
        <button type="submit" class="btn btn-primary btn-block">Add Dress Type</button>
      </div>
    </div>

  </form:form>

  <c:if test="${dressTypes.size() == 0}">
    <p class="text-info">There are currently no dress types registered in the system!</p>
  </c:if>
  <ul class="list-group">
    <c:forEach items="${dressTypes}" var="dressType">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <span>${dressType.name} (${dressType.subcategoryName})</span>
        <a class="btn btn-danger" href="/deleteDressType?dressTypeId=${dressType.dressTypeId}">Delete</a>
      </li>
    </c:forEach>
  </ul>

</div>
<%@ include file="common/footer.jsp"%>
