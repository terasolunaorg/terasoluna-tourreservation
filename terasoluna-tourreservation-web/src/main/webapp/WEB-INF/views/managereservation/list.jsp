
<!-- begin body-->
<div class="container">
  <jsp:include page="../common/top.jsp" />
  <h1 id="screenName">
    <span><spring:message code="label.tr.managereservation.manageReservationMessage" /></span>
  </h1>
  <div class="info">
    <spring:message code="label.tr.managereservation.manageReservationFlowMessage" />
  </div>
  <br />
  <c:if test="${rows.size() == 0}">
    <div>
      <spring:message code="label.tr.managereservation.manageReservationNoRecordMessage" />
    </div>
    <spring:message code="label.tr.managereservation.manageReservationNoCancelMessage" />
  </c:if>
  <c:if test="${rows.size() != 0}">
    <table id="reservationsTable">
      <caption>
        <spring:message code="label.tr.managereservation.ReserveListMessage" />
      </caption>
      <tr>
        <th><spring:message code="label.tr.managereservation.reserveNo" /></th>
        <th><spring:message code="label.tr.managereservation.tourName" /></th>
        <th><spring:message code="label.tr.managereservation.depDay" /></th>
        <th><spring:message code="label.tr.managereservation.days" /></th>
        <th><spring:message code="label.tr.managereservation.origin" /></th>
        <th><spring:message code="label.tr.managereservation.destination" /></th>
        <th><spring:message code="label.tr.managereservation.noOfPerson" /></th>
        <th><spring:message code="label.tr.managereservation.paymentStatus" /></th>
        <th><spring:message code="label.tr.managereservation.totalCost" /></th>
        <th></th>
      </tr>

      <%-- reservationInfo --%>
      <spring:message code="label.tr.common.datePattern" var="datePattern" />
      <spring:message code="label.tr.common.personcountPattern" var="personCountPattern" />
      <spring:message code="label.tr.common.currencyPattern" var="currencyPattern" />

      <c:forEach items="${rows}" var="row" varStatus="rowStatus">
        <tr>
          <td>${f:h(row.reserve.reserveNo)}</td>
          <td>${f:h(row.reserve.tourInfo.tourName)}</td>
          <td><fmt:formatDate value="${row.reserve.tourInfo.depDay}" pattern="${datePattern}" /></td>
          <td>${f:h(row.reserve.tourInfo.tourDays)}</td>
          <td>${f:h(row.reserve.tourInfo.departure.depName)}</td>
          <td>${f:h(row.reserve.tourInfo.arrival.arrName)}</td>
          <td><spring:message code="label.tr.common.adult" /> <fmt:formatNumber
              value="${row.reserve.adultCount}" pattern="${personCountPattern}" /> <br /> <spring:message
              code="label.tr.common.child" /> <fmt:formatNumber value="${row.reserve.childCount}"
              pattern="${personCountPattern}" /></td>
          <td><c:if test="${f:h(row.reserve.transfer) == 1}">
              <spring:message code="label.tr.managereservation.done" />
            </c:if> <c:if test="${f:h(row.reserve.transfer) == 0}">
              <spring:message code="label.tr.managereservation.paymentNotDone" />
            </c:if></td>
          <td><fmt:formatNumber pattern="${currencyPattern}" value="${row.reserve.sumPrice}" /></td>
          <td>
            <table class="com-button-table span-4">
              <tr>
                <td><form:form method="get"
                    action="${pageContext.request.contextPath}/reservations/${f:h(row.reserve.reserveNo)}">
                    <button id="showBtn${rowStatus.index}">
                      <spring:message code="label.tr.managereservation.showDetailsBtnMessage" />
                    </button>
                  </form:form> <c:if test="${!f:h(row.limitExceeding)}">
                    <c:if test="${f:h(row.reserve.transfer) == 0}">
                      <form:form method="get"
                        action="${pageContext.request.contextPath}/reservations/${f:h(row.reserve.reserveNo)}/update">
                        <button id="changeBtn${rowStatus.index}" name="form">
                          <spring:message
                            code="label.tr.managereservation.changeReservationBtnMessage" />
                        </button>
                      </form:form>
                      <form:form method="get"
                        action="${pageContext.request.contextPath}/reservations/${f:h(row.reserve.reserveNo)}/cancel">
                        <button id="cancelBtn${rowStatus.index}">
                          <spring:message
                            code="label.tr.managereservation.cancelReservationBtnMessage" />
                        </button>
                      </form:form>
                    </c:if>
                  </c:if></td>
              </tr>
            </table>
          </td>
        </tr>
      </c:forEach>
    </table>
    <br />
  </c:if>
</div>

<!-- end body -->

