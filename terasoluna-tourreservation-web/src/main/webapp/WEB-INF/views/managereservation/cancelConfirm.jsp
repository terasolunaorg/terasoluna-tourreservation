
<div class="container">
  <h2 id="screenName">
    <spring:message code="label.tr.managereservation.beforecancelScreenTitleMessage" />
  </h2>

  <div class="info">
    <spring:message code="label.tr.managereservation.beforecancelScreenFlowMessage" />
  </div>
  <p>
    <spring:message code="label.tr.managereservation.beforeCancelScreenMessage" />
  </p>
  <div class="span-24">
    <t:messagesPanel />
    <br>
    <jsp:include page="../common/fragment/reserveTable.jsp" />
    <br>
    <jsp:include page="../common/fragment/customerTable.jsp" />
    <br>
    <hr>
    <jsp:include page="../common/fragment/priceTable.jsp" />
    <br>
    <hr />
    <table>
      <caption>
        <spring:message code="label.tr.common.specialNotes" />
      </caption>
      <tr>
        <td><spring:message code="label.tr.common.opinionSuggestion" /></td>
        <td colspan="3">${f:h(output.reserve.remarks)}<br />
      </tr>
    </table>
  </div>
  <div class="span-9">
    <form:form id="reservationCanelForm">
      <table>
        <tr>
          <td>
            <button id="backToListBtn" name="backTolist">
              <spring:message code="label.tr.managereservation.returnToListScreenBtnMessage" />
            </button>
          </td>
          <td>
            <button id="cancelBtn">
              <spring:message code="label.tr.managereservation.cancelReservationBtnMessage" />
            </button>
          </td>
        </tr>
      </table>
    </form:form>
  </div>
</div>

