<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can update your pattern below, or <a href="/patterns">go back</a>.
  </p>

  <form:form method="post" enctype="multipart/form-data" action="/updatePattern" modelAttribute="pattern" cssClass="my-3">
    <form:hidden path="patternId" value="${pattern.patternId}" />

    <div class="row">
      <div class="col-sm form-group">
        <label for="name">Pattern Name</label>
        <form:input path="name" cssClass="form-control" required="true" value="${pattern.name}" />
      </div>
      <div class="col-sm form-group">
        <label for="cost">Pattern Cost</label>
        <div class="input-group">
          <form:input path="cost" cssClass="form-control" required="true" type="number" step="0.01" value="${pattern.cost}" />
          <div class="input-group-append">
            <div class="input-group-text">INR</div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="category">Category</label>
        <form:select path="category.categoryId" id="category" cssClass="form-control" required="true">
          <c:forEach items="${categories}" var="category">
            <c:choose>
              <c:when test="${pattern.category.categoryId == category.categoryId}">
                <option value="${category.categoryId}" selected>${category.name}</option>
              </c:when>
              <c:otherwise>
                <option value="${category.categoryId}">${category.name}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </form:select>
      </div>
      <div class="col-sm form-group">
        <label for="dressType">Dress Type</label>
        <form:select path="dressType.dressTypeId" id="dressType" cssClass="form-control" required="true">
          <c:forEach items="${dressTypes}" var="dressType">
            <c:choose>
              <c:when test="${pattern.dressType.dressTypeId == dressType.dressTypeId}">
                <option value="${dressType.dressTypeId}" selected>${dressType.name} (${dressType.subcategoryName})</option>
              </c:when>
              <c:otherwise>
                <option value="${dressType.dressTypeId}">${dressType.name} (${dressType.subcategoryName})</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </form:select>
      </div>
    </div>
    <div class="form-group">
      <label for="description">Pattern Description</label>
      <form:textarea path="description" cssClass="form-control" rows="3" required="true" value="${pattern.description}" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Update Pattern</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
