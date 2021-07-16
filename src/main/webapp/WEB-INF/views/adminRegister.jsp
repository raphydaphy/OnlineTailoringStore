<%@ include file="common/header.jsp" %>
<div class="container mt-3">
  <h1>Online Tailoring Store</h1>
  <p>
    Create a new admin account using to the form below.<br />
    Already have an account? <a href="/adminLogin">Login now</a>!
  </p>

  <%
    String error = (String) request.getAttribute("error");
    if(error != null) {
      out.print("<p class='text-warning'>" + error + "</p>");
    }
  %>

  <form:form method="post" action="/adminRegister" modelAttribute="user">
    <div class="row">
      <div class="col-sm form-group">
        <label for="username">Username</label>
        <form:input path="username" name="username" id="username" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="password">Password</label>
        <form:password path="password" name="password" id="password" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="firstName">First Name</label>
        <form:input path="firstName" name="firstName" id="firstName" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="lastName">Last Name</label>
        <form:input path="lastName" name="lastName" id="lastName" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="email">Email Address</label>
        <form:input type="email" path="email" name="email" id="email" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="contactNumber">Contact Number</label>
        <form:input path="contactNumber" name="contactNumber" id="contactNumber" cssClass="form-control" required="true" />
      </div>
    </div>
    <div class="row">
      <div class="col-sm form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <form:input path="dateOfBirth" name="dateOfBirth" id="dateOfBirth" pattern="" cssClass="form-control" required="true" />
      </div>
      <div class="col-sm form-group">
        <label for="gender">Gender</label>
        <form:select path="gender" name="gender" id="gender" cssClass="form-control" required="true">
          <form:option value="male">Male</form:option>
          <form:option value="female">Female</form:option>
          <form:option value="other">Non Binary / Other</form:option>
        </form:select>
      </div>
    </div>
    <button type="submit" class="btn btn-primary btn-block">Create Account</button>
  </form:form>

</div>
<%@ include file="common/footer.jsp"%>
