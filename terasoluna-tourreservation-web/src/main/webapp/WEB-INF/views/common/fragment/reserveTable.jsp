
<table id="reserveTable">
  <caption>
    <spring:message code="label.tr.searchtour.tourDetailCaptionMessage" />
  </caption>
  <spring:message code="label.tr.common.datePattern" var="datePattern" />
  <tr>
    <td><spring:message code="label.tr.searchtour.reserveNo" /></td>
    <td>${f:h(output.reserve.reserveNo)}</td>
    <td><spring:message code="label.tr.searchtour.reserveDate" /></td>
    <td><fmt:formatDate value="${output.reserve.reservedDay}" pattern="${datePattern}" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.tourname" /></td>
    <td colspan="3">${f:h(output.reserve.tourInfo.tourName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.depDay" /></td>
    <td><fmt:formatDate value="${output.reserve.tourInfo.depDay}" pattern="${datePattern}" /></td>
    <td><spring:message code="label.tr.searchtour.tourDays" /></td>
    <td>${f:h(output.reserve.tourInfo.tourDays)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.depName" /></td>
    <td>${f:h(output.reserve.tourInfo.departure.depName)}</td>
    <td><spring:message code="label.tr.searchtour.arrName" /></td>
    <td>${f:h(output.reserve.tourInfo.arrival.arrName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.conductor" /></td>
    <td colspan="3">${f:h(CL_EXISTENCE[output.reserve.tourInfo.conductor])}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.accomName" /></td>
    <td>${f:h(output.reserve.tourInfo.accommodation.accomName)}</td>
    <td><spring:message code="label.tr.searchtour.accomTel" /></td>
    <td>${f:h(output.reserve.tourInfo.accommodation.accomTel)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.tourAbs" /></td>
    <td colspan="3">${f:h(output.reserve.tourInfo.tourAbs)}</td>
  </tr>
</table>