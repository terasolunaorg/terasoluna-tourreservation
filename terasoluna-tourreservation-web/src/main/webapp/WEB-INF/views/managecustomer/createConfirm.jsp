
<div class="container">

  <div class="info">
    <p>
      <spring:message code="label.tr.managecustomer.createConfirmMessage" />
    </p>
  </div>
  <form:form action="create" method="post" modelAttribute="customerForm">
    <jsp:include page="fragment/customerTable.jsp" />

    <!-- Include all the hidden items here. 
			These will be mapped to the modelAttribute form -->

    <form:hidden path="customerKana" />
    <form:hidden path="customerName" />
    <form:hidden path="customerBirthYear" />
    <form:hidden path="customerBirthMonth" />
    <form:hidden path="customerBirthDay" />
    <form:hidden path="customerJob" />
    <form:hidden path="customerMail" />
    <form:hidden path="customerTel" />
    <form:hidden path="customerPost" />
    <form:hidden path="customerAdd" />
    <form:hidden path="customerPass" />
    <form:hidden path="customerPassConfirm" />

    <input id="backToFormBtn" type="submit" name="redo"
      value="<spring:message code="label.tr.common.goBackMessage" />">
            &nbsp;
        <input id="registerBtn" type="submit"
      value="<spring:message code="label.tr.common.register" />">
  </form:form>

</div>
