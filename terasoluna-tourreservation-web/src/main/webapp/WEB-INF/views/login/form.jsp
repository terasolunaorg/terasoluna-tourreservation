
<div class="container">
  <jsp:include page="../common/top.jsp" />
  <form:form action="${pageContext.request.contextPath}/login">
    <fieldset>
      <legend>
        <spring:message code="label.tr.login.loginFormMessage" />
      </legend>

      <c:if test="${param.containsKey('error')}">
        <span id="loginError"> <t:messagesPanel
            messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" />
        </span>
      </c:if>

      <p>
        <label for="username"> <spring:message code="label.tr.common.userId" /></label> <br> <input
          type="text" class="text" id="username" name="username">
      </p>
      <p>
        <label for="password"> <spring:message code="label.tr.common.password" />
        </label> <br> <input type="password" class="text" id="password" name="password">
      </p>
      <p>
        <input type="hidden" name="redirectTo" value="${f:h(param.redirectTo)}" />
        <button id="loginBtn">
          <spring:message code="label.tr.common.login" />
        </button>
        <button id="resetBtn" type="reset">
          <spring:message code="label.tr.common.reset" />
        </button>
      </p>
    </fieldset>
  </form:form>
  <hr>
  <p>
    <spring:message code="label.tr.login.notCustmerMessage" />
  </p>
  <form:form method="get" action="${pageContext.request.contextPath}/customers/create">
    <button id="customerRegisterBtn" name="form">
      <spring:message code="label.tr.menu.customerRegisterBtnMessage" />
    </button>
  </form:form>
</div>


