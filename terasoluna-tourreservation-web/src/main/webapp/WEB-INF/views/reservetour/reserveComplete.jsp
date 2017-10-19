
<div class="container">
  <!-- begin body-->
  <h2 id="screenName">
    <spring:message code="label.tr.searchtour.reserveScreenTitleMessage" />
  </h2>
  <div class="success">
    <spring:message code="label.tr.searchtour.reserveScreenFlowMessage" />
  </div>
  <br />

  <p>
    <spring:message code="label.tr.searchtour.searchtourReservePdfMessage" />
  </p>
  <p>
    <spring:message code="label.tr.searchtour.searchtourReserveDetailMessage" />
  </p>
  <form:form method="get"
    action="${pageContext.request.contextPath}/reservations/${f:h(output.getReserve().getReserveNo())}/pdf">
    <button>
      <spring:message code="label.tr.common.downloadpdfBtnMessage" />
    </button>
  </form:form>

  <jsp:include page="../common/fragment/paymentTable.jsp" />
  <br />
  <hr />
  <jsp:include page="../common/fragment/reserveTable.jsp" />
  <br />
  <!-- representative -->
  <hr />
  <jsp:include page="../common/fragment/customerTable.jsp" />

  <%-- representative end --%>

  <%-- total amount start --%>
  <br />
  <hr />
  <jsp:include page="../common/fragment/priceTable.jsp" />
  <br />
  <hr />
  <table>
    <caption>
      <spring:message code="label.tr.common.specialNotes" />
    </caption>
    <tr>
      <td><spring:message code="label.tr.common.opinionSuggestion" /></td>
      <td>${f:h(output.reserve.remarks)}</td>
    </tr>
  </table>
  <br />
  <div>
    <form:form method="get" action="${pageContext.request.contextPath}/tours">
      <button>
        <spring:message code="label.tr.searchtour.goTourSearchScreenMessage" />
      </button>
    </form:form>
    <form:form method="get" action="${pageContext.request.contextPath}/">
      <button>
        <spring:message code="label.tr.common.gotoMenuMessage" />
      </button>
    </form:form>
  </div>
</div>
<!-- end body-->

