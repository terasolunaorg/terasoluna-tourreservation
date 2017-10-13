
<div class="container">

  <div class="info">
    <p>
      <spring:message code="label.tr.managecustomer.createConfirmMessage" />
    </p>
  </div>
  <form:form modelAttribute="customerForm">
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

    <form:button id="backToFormBtn" name="redo">
      <spring:message code="label.tr.common.goBackMessage" />
    </form:button>
            &nbsp;
		<form:button id="registerBtn">
      <spring:message code="label.tr.common.register" />
    </form:button>
  </form:form>

</div>
