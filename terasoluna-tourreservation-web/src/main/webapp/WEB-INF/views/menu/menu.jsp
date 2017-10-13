<div class="container">
  <p id="messagesArea" class="box">
    <sec:authorize access="!hasRole('USER')">
      <spring:message code="label.tr.common.notLoginMessage" />
    </sec:authorize>
    <spring:message code="label.tr.menu.menuMessage" />
  </p>
  <div class="span-24 last">
    <form:form method="get" action="${pageContext.request.contextPath}/tours">
      <fieldset class="notice">
        <div class="span-5">
          <button id="searchTourBtn" name="initForm" style="width: 150px;">
            <spring:message code="label.tr.menu.searchBtnMessage" />
          </button>
        </div>
        <div class="span-18 last">
          <p>
            <spring:message code="label.tr.menu.tourSearchMessage" />
          </p>
        </div>
      </fieldset>
    </form:form>
    <sec:authorize access="!hasRole('USER')">
      <form:form method="get" action="${pageContext.request.contextPath}/login">
        <fieldset class="notice">
          <div class="span-5">
            <button id="loginBtn" style="width: 150px;">
              <spring:message code="label.tr.menu.loginBtnMessage" />
            </button>
          </div>
          <div class="span-18 last">
            <p>
              <spring:message code="label.tr.menu.loginMessage" />
            </p>
          </div>
        </fieldset>
      </form:form>

      <form:form method="get" action="${pageContext.request.contextPath}/customers/create">
        <fieldset class="notice">
          <div class="span-5">
            <button id="customerRegisterBtn" name="form" style="width: 150px;">
              <spring:message code="label.tr.menu.customerRegisterBtnMessage" />
            </button>
          </div>
          <div class="span-18 last">
            <p>
              <spring:message code="label.tr.menu.customerRegisterMessage" />
            </p>
          </div>
        </fieldset>
      </form:form>
    </sec:authorize>
    <sec:authorize access="hasRole('USER')">
      <form:form method="get" action="${pageContext.request.contextPath}/reservations/me">
        <fieldset class="notice">
          <div class="span-5">
            <button id="reservedToursReferBtn" style="width: 150px;">
              <spring:message code="label.tr.menu.referBtnMessage" />
            </button>
          </div>
          <div class="span-18 last">
            <p>
              <spring:message code="label.tr.menu.referMessage" />
            </p>
          </div>
        </fieldset>
      </form:form>
    </sec:authorize>
  </div>
  <div id="languageSwitcher" class="right">
    <spring:eval expression="@localeResolver.resolveLocale(pageContext.request)" var="currentLocale" />
    <c:choose>
      <c:when test="${currentLocale == 'ja'}">
        <a id="switchEn" href="?locale=en">English</a>
      </c:when>
      <c:otherwise>
        <a id="switchJa" href="?locale=ja">日本語</a>
      </c:otherwise>
    </c:choose>
  </div>
</div>
