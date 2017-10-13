
<div class="container">
  <jsp:include page="../common/top.jsp" />
  <div>
    <div>
      <div>
        <div>
          <h1 id="screenName">
            <span><spring:message
                code="label.tr.managereservation.manageReservationShowScreenTitleMessage" /></span>
          </h1>
        </div>
      </div>
    </div>
    <div>
      <div class="info">
        <spring:message code="label.tr.managereservation.manageReservationShowScreenFlowMessage" />
      </div>
      <br />
      <P>
        <spring:message code="label.tr.managereservation.manageReservationShowMessage" />
      </P>
      <c:if test="${f:h(output.reserve.transfer) == 0}">
        <br />
        <spring:message code="label.tr.managereservation.paymentReminderMessage" />
      </c:if>
      <c:if test="${f:h(output.reserve.transfer) == 1}">
        <br />
        <spring:message code="label.tr.managereservation.paymentNoCancelMessage" />
        <br />
      </c:if>
      <br />
      <spring:message code="label.tr.common.pdfdownloadMessage" />
      <br />
      <div>
        <form:form method="get"
          action="${pageContext.request.contextPath}/reservations/${output.reserve.reserveNo}/pdf">
          <button id="downloadBtn">
            <spring:message code="label.tr.common.downloadpdfBtnMessage" />
          </button>
        </form:form>
      </div>
      <c:if test="${f:h(output.reserve.transfer) == 0}">
        <jsp:include page="../common/fragment/paymentTable.jsp" />
        <br />
        <hr />
      </c:if>
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
      <!-- comment end -->
      <br /> <br />
      <div class="span-12">
        <table>
          <tr>
            <td><form:form method="get"
                action="${pageContext.request.contextPath}/reservations/me">
                <button id="backToListBtn" name="backTolist">
                  <spring:message code="label.tr.managereservation.returnToListScreenBtnMessage" />
                </button>
              </form:form></td>
            <c:if test="${!output.limitExceeding}">
              <c:if test="${output.reserve.transfer == 0}">
                <td><form:form method="get"
                    action="${pageContext.request.contextPath}/reservations/${f:h(output.reserve.reserveNo)}/update">
                    <button id="changeBtn" name="form">
                      <spring:message code="label.tr.managereservation.changeReservationBtnMessage" />
                    </button>
                  </form:form></td>
                <td><form:form method="get"
                    action="${pageContext.request.contextPath}/reservations/${f:h(output.reserve.reserveNo)}/cancel">
                    <button id="cancelBtn">
                      <spring:message code="label.tr.managereservation.cancelReservationBtnMessage" />
                    </button>
                  </form:form></td>
              </c:if>
            </c:if>
          </tr>
        </table>
      </div>
      <br /> <br />
    </div>
  </div>
</div>

