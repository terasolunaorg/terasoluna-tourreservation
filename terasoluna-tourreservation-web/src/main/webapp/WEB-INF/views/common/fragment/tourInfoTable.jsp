
<table id="tourInfoTable" summary="Tour Information">
  <caption>
    <spring:message code="label.tr.searchtour.tourDetailCaptionMessage" />
  </caption>
  <tr>
    <td><spring:message code="label.tr.searchtour.tourname" /></td>
    <td colspan="3">${f:h(output.tourInfo.tourName)}</td>
  </tr>
  <tr>
    <spring:message code="label.tr.common.datePattern" var="datePattern" />
    <td><spring:message code="label.tr.searchtour.depDay" /></td>
    <td><fmt:formatDate value="${output.tourInfo.depDay}" pattern="${datePattern}" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.tourDays" /></td>
    <td>${f:h(output.tourInfo.tourDays)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.depName" /></td>
    <td>${f:h(output.tourInfo.departure.depName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.arrName" /></td>
    <td>${f:h(output.tourInfo.arrival.arrName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.conductor" /></td>
    <td colspan="3">${f:h(CL_EXISTENCE[output.tourInfo.conductor])}</td>

  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.accomName" /></td>
    <td>${f:h(output.tourInfo.accommodation.accomName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.accomTel" /></td>
    <td>${f:h(output.tourInfo.accommodation.accomTel)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.tourAbs" /></td>
    <td colspan="3">${f:h(output.tourInfo.tourAbs)}</td>
  </tr>
</table>