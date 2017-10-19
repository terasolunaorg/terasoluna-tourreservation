<div class="container">
  <jsp:include page="../common/top.jsp" />
  <h2 id="screenName">
    <spring:message code="label.tr.searchtour.titleDetailScreenMessage" />
  </h2>

  <div class="info">
    <spring:message code="label.tr.searchtour.detailScreenFlowMessage" />
  </div>

  <sec:authorize access="!hasRole('USER')">
    <p>
      <spring:message code="label.tr.searchtour.loginToReserveMessage" />
    </p>
  </sec:authorize>
  <sec:authorize access="hasRole('USER')">
    <p>
      <spring:message code="label.tr.searchtour.pressReserveBtnMessage" />
    </p>
  </sec:authorize>
  <div class="span-24">
    <t:messagesPanel />
    <form:errors path="*" class="error" />
    <br>
    <jsp:include page="../common/fragment/tourInfoTable.jsp" />
    <br>
    <sec:authorize access="hasRole('USER')">
      <br>
      <jsp:include page="../common/fragment/customerTable.jsp" />
    </sec:authorize>
    <br>
    <jsp:include page="../common/fragment/priceTable.jsp" />
  </div>
  <sec:authorize access="hasRole('USER')">
    <br>
    <table>
      <tr>
        <td></td>
        <td></td>
      </tr>
      <form:form modelAttribute="reserveTourForm">
        <table>
          <form:errors path="*" cssClass="error" element="div" />
          <caption>
            <spring:message code="label.tr.common.specialNotes" />
          </caption>
          <tr>
            <td><spring:message code="label.tr.common.opinionSuggestion" /></td>
            <td><form:textarea path="remarks" /> <br> <spring:message
                code="label.tr.searchtour.lessThanEightyCharsMessage" /> <br> <spring:message
                code="label.tr.searchtour.opinionSuggestionMessage" /></td>
          </tr>
        </table>
        <br />
        <form:hidden path="childCount" />
        <form:hidden path="adultCount" />
        <input type="hidden" name="page" value="${f:h(param['page'])}" />
        <input type="hidden" name="size" value="${f:h(param['size'])}" />
        <form:button id="confirmBtn" name="confirm">
          <spring:message code="label.tr.common.confirm" />
        </form:button>
      </form:form>
      <form:form method="get" action="${pageContext.request.contextPath}/tours" cssClass="inline">
        <input type="hidden" name="page" value="${f:h(param['page'])}" />
        <input type="hidden" name="size" value="${f:h(param['size'])}" />
        <button id="backToToursBtn">
          <spring:message code="label.tr.common.goBackMessage" />
        </button>
      </form:form>
    </table>
  </sec:authorize>
  <sec:authorize access="!hasRole('USER')">
    <form:form method="get" action="${pageContext.request.contextPath}/login" cssClass="inline">
      <input type="hidden" name="redirectTo"
        value="/tours/${f:h(output.tourInfo.tourCode)}/reserve?form&${f:query(reserveTourForm)}&page=${f:h(param['page'])}&size=${f:h(param['size'])}" />
      <button id="loginBtn">
        <spring:message code="label.tr.menu.loginBtnMessage" />
      </button>
    </form:form>
    <form:form method="get" action="${pageContext.request.contextPath}/tours" cssClass="inline">
      <input type="hidden" name="page" value="${f:h(param['page'])}" />
      <input type="hidden" name="size" value="${f:h(param['size'])}" />
      <button id="backToToursBtn">
        <spring:message code="label.tr.common.goBackMessage" />
      </button>
    </form:form>
  </sec:authorize>
</div>

