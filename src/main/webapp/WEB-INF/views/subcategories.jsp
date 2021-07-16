<%@ include file="common/header.jsp" %>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can manage the clothing subcategories below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <form:form method="post" action="/createSubcategory" modelAttribute="subcategory">
    <div class="row align-items-center">
      <div class="row col-md flex-grow-1 align-items-center">
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:input path="subcategoryName" name="subcategoryName" id="subcategoryName" cssClass="form-control" placeholder="Subcategory Name" />
        </div>
        <div class="col-sm form-group flex-grow-1 align-items-center">
          <form:select path="categoryId" name="categoryId" id="categoryId" cssClass="form-control" required="true">
            <c:forEach items="${categories}" var="category">
              <form:option value="${category.categoryId}">${category.name}</form:option>
            </c:forEach>
          </form:select>
        </div>
      </div>
      <div class="col-md-3 align-items-center mb-2 mb-md-3">
        <button type="submit" class="btn btn-primary btn-block">Add Subcategory</button>
      </div>
    </div>

  </form:form>

  <c:if test="${subcategories.size() == 0}">
    <p class="text-info">There are currently no subcategories registered in the system!</p>
  </c:if>
  <ul class="list-group">
    <c:forEach items="${subcategories}" var="subcategory">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <span>${subcategory.subcategoryName} (${subcategory.categoryName})</span>
        <a class="btn btn-danger" href="/deleteSubcategory?subcategoryId=${subcategory.subcategoryId}">Delete</a>
      </li>
    </c:forEach>
  </ul>

</div>
<%@ include file="common/footer.jsp"%>
