
<div class="container">
  <jsp:include page="../common/top.jsp" />
  <h2 id="screenName">
    <spring:message code="label.tr.searchtour.titleConfirmScreenMessage" />
  </h2>

  <div class="info">
    <spring:message code="label.tr.searchtour.confirmScreenFlowMessage" />
  </div>
  <p>
    <spring:message code="label.tr.searchtour.pressReserveBtnMessage" />
  </p>
  <div class="span-24">
    <br>

    <jsp:include page="../common/fragment/tourInfoTable.jsp" />
    <br>
    <jsp:include page="../common/fragment/customerTable.jsp" />
    <br>
    <hr>
    <jsp:include page="../common/fragment/priceTable.jsp" />
    <br>
    <hr />
    <table id="specialNotesTable">
      <caption>
        <spring:message code="label.tr.common.specialNotes" />
      </caption>
      <tr>
        <td><spring:message code="label.tr.common.opinionSuggestion" /></td>
        <td colspan="3">${f:h(reserveTourForm.remarks)}<br />
      </tr>
    </table>
  </div>
  <div class="span-4 append-20">
    <form:form modelAttribute="reserveTourForm" cssClass="inline">
      <form:hidden path="childCount" />
      <form:hidden path="adultCount" />
      <form:hidden path="remarks" />
      <input type="hidden" name="page" value="${f:h(param['page'])}" />
      <input type="hidden" name="size" value="${f:h(param['size'])}" />
      <form:button id="reserveBtn">
        <spring:message code="label.tr.searchtour.confirmedMessage" />
      </form:button>
    </form:form>
    <form:form modelAttribute="reserveTourForm">
      <form:hidden path="childCount" />
      <form:hidden path="adultCount" />
      <form:hidden path="remarks" />
      <input type="hidden" name="page" value="${f:h(param['page'])}" />
      <input type="hidden" name="size" value="${f:h(param['size'])}" />
      <form:button id="goBackBtn" name="redo">
        <spring:message code="label.tr.common.goBackMessage" />
      </form:button>
    </form:form>
  </div>
</div>


