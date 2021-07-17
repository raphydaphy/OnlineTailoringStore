<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can manage the clothing categories below, or <a href="/account">go back</a>.
  </p>

  <c:choose>
    <c:when test="${error != null}">
      <p class="text-warning">${error}</p>
    </c:when>
    <c:when test="${message != null}">
      <p class="text-info">${message}</p>
    </c:when>
  </c:choose>

  <h2 class="h3">Categories</h2>

  <form:form method="post" action="/createCategory" modelAttribute="category">
    <div class="row align-items-center my-3">
      <div class="col align-items-center">
        <label for="name">Category Name</label>
      </div>
      <div class="col-6">
        <form:input path="name" name="name" id="name" cssClass="form-control" required="true" />
      </div>
      <div class="col-md mt-2 mt-md-0">
        <button type="submit" class="btn btn-primary btn-block">Add Category</button>
      </div>
    </div>
  </form:form>

  <c:if test="${categories.size() == 0}">
    <p class="text-info">There are currently no categories registered in the system!</p>
  </c:if>
  <ul class="list-group mb-3">
    <c:forEach items="${categories}" var="category">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <span>${category.name}</span>
        <a class="btn btn-danger" href="/deleteCategory?categoryId=${category.categoryId}">Delete</a>
      </li>
    </c:forEach>
  </ul>


  <h2 class="h3">Subcategories</h2>

  <form:form method="post" action="/createSubcategory" modelAttribute="subcategory">
    <div class="row align-items-center my-3">
      <div class="col align-items-center">
        <label for="subcategoryName">Subcategory Name</label>
      </div>
      <div class="col-6">
        <form:input path="subcategoryName" name="subcategoryName" id="subcategoryName" cssClass="form-control" required="true" />
      </div>
      <div class="col-md mt-2 mt-md-0">
        <button type="submit" class="btn btn-primary btn-block">Add Subcategory</button>
      </div>
    </div>
  </form:form>

  <c:if test="${subcategories.size() == 0}">
    <p class="text-info">There are currently no categories registered in the system!</p>
  </c:if>
  <ul class="list-group mb-3">
    <c:forEach items="${subcategories}" var="subcategory">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <span>${subcategory.subcategoryName}</span>
        <a class="btn btn-danger" href="/deleteSubcategory?subcategoryId=${subcategory.subcategoryId}">Delete</a>
      </li>
    </c:forEach>
  </ul>

</div>
<%@ include file="common/footer.jsp"%>
