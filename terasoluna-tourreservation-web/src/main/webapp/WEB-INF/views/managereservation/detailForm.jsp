
  <div class="container">
   <jsp:include page="../common/top.jsp" />
    <div>
      <div>
        <div>
          <div>
            <h1 id="screenName">
              <span><spring:message code="label.tr.managereservation.manageReservationShowScreenTitleMessage" /></span>
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
          <form:form method="GET" action="${pageContext.request.contextPath}/managereservation/downloadPDF">
            <input type="hidden"  value="${output.reserve.reserveNo}" name="reserveNo" />
            <spring:message code="label.tr.common.downloadpdfBtnMessage" var="downloadPDFButton"/>
            <input type="submit" value="${downloadPDFButton}" tabindex="1" />
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
        <div class="span-8 last">
          <table>
            <tr>
              <td><form:form action="${pageContext.request.contextPath}/managereservation/list" method="GET">
                  <spring:message code="label.tr.managereservation.returnToListScreenBtnMessage" var="returnToListScreen" />
                  <input id="backToListBtn" type="submit" name="backTolist" value="${returnToListScreen}" tabindex="2" />
                </form:form></td>
              <c:if test="${!f:h(output.limitExceeding)}">
                <c:if test="${f:h(output.reserve.transfer) == 0}">
                  <td><form:form action="${pageContext.request.contextPath}/managereservation/update/${f:h(output.reserve.reserveNo)}"
                      METHOD="GET">
                      <spring:message code="label.tr.managereservation.changeReservationBtnMessage" var="changeReservation" />
                      <input id="changeBtn" type="submit" name="form" value="${changeReservation}" tabindex="3" />
                    </form:form></td>
                  <td><form:form action="${pageContext.request.contextPath}/managereservation/cancel" METHOD="POST">
                      <input type="hidden" value="${output.reserve.reserveNo}" name="reserveNo">
                      <spring:message code="label.tr.managereservation.cancelReservationBtnMessage" var="cancelReservation" />
                      <input id="cancelBtn" type="submit" name="confirm" value="${cancelReservation}" tabindex="3" />
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

