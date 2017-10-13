
<div class="container">
  <div class="success">
    <p>
      <spring:message code="label.tr.managecustomer.createCompleteMessage"
        arguments="${f:h(customer.customerCode)}" />
    </p>
  </div>
  <form:form method="get" action="${pageContext.request.contextPath}/">
    <button id="goToMenuBtn">
      <spring:message code="label.tr.common.gotoMenuMessage" />
    </button>
  </form:form>
</div>
