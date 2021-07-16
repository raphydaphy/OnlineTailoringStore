<%@ include file="common/header.jsp" %>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Create a new account using to the form below.<br />
    Already have an account? <a href="/userLogin">Login now</a>!
  </p>


  <form:form method="post" action="/register" modelAttribute="user">
    <div class="row">
      <div class="col-sm form-group">
        <label for="username">Username</label>
        <form:input path="username" name="username" id="username" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="password">Password</label>
        <form:password path="password" name="password" id="password" cssClass="form-control" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="username">First Name</label>
        <form:input path="firstName" name="firstName" id="firstName" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="lastName">Last Name</label>
        <form:input path="lastName" name="lastName" id="lastName" cssClass="form-control" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <form:input path="dateOfBirth" name="dateOfBirth" id="dateOfBirth" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="gender">Gender</label>
        <form:select path="gender" name="gender" id="gender" cssClass="form-control">
          <form:option value="male">Male</form:option>
          <form:option value="female">Female</form:option>
          <form:option value="non-binary">Non Binary / Other</form:option>
        </form:select>
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <form:input path="dateOfBirth" name="dateOfBirth" id="dateOfBirth" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="gender">Gender</label>
        <form:select path="gender" name="gender" id="gender" cssClass="form-control">
          <form:option value="male">Male</form:option>
          <form:option value="female">Female</form:option>
          <form:option value="other">Non Binary / Other</form:option>
        </form:select>
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="contactNumber">Contact Number</label>
        <form:input path="contactNumber" name="contactNumber" id="contactNumber" cssClass="form-control" />
      </div>
      <div class="col-sm form-group">
        <label for="category">Account Type</label>
        <form:select path="category" name="category" id="category" cssClass="form-control">
          <form:option value="customer">Customer</form:option>
          <form:option value="tailor">Tailor</form:option>
        </form:select>
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">Create Account</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
