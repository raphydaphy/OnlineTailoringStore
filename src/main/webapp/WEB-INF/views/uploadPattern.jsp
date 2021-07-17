<%@ include file="common/header.jsp" %>
<div class="container my-3">
  <h1>Online Tailoring Store</h1>
  <p>
    You can upload a new pattern below, or <a href="/patterns">go back</a>.
  </p>

  <form:form method="post" enctype="multipart/form-data" action="/uploadPattern" modelAttribute="pattern" cssClass="my-3">
    <div class="row">
      <div class="col-sm form-group">
        <label for="name">Pattern Name</label>
        <form:input path="name" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="cost">Pattern Cost</label>
        <div class="input-group">
          <div class="input-group-prepend">
            <div class="input-group-text">$</div>
          </div>
          <form:input path="cost" cssClass="form-control" required="true" type="number" step="0.01" />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="category">Category</label>
        <form:select path="category.categoryId" id="category" cssClass="form-control" required="true">
          <c:forEach items="${categories}" var="category">
            <option value="${category.categoryId}">${category.name}</option>
          </c:forEach>
        </form:select>
      </div>
      <div class="col-sm form-group">
        <label for="dressType">Dress Type</label>
        <form:select path="dressType.dressTypeId" id="dressType" cssClass="form-control" required="true">
          <c:forEach items="${dressTypes}" var="dressType">
            <option value="${dressType.dressTypeId}">${dressType.name} (${dressType.subcategoryName})</option>
          </c:forEach>
        </form:select>
      </div>
    </div>
    <div class="form-group">
      <label for="description">Pattern Description</label>
      <form:textarea path="description" cssClass="form-control" rows="3" required="true" />
    </div>
    <div class="form-group">
      <label for="imageUpload">Pattern Image</label>
      <form:input path="imageUpload" type="file" cssClass="form-control-file" required="true" />
    </div>
    <button type="submit" class="btn btn-primary btn-block">Upload Pattern</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
