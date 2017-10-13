
<div class="container">
  <div class="success">
    <p>
      <spring:message code="label.tr.managecustomer.createCompleteMessage"
        arguments="${f:h(customerCode)}" />
    </p>
  </div>
  <form:form action="${pageContext.request.contextPath}/" method="get">
    <input id="goToMenuBtn" type="submit" name="submit"
      value="<spring:message code="label.tr.common.gotoMenuMessage" />">
  </form:form>
</div>
