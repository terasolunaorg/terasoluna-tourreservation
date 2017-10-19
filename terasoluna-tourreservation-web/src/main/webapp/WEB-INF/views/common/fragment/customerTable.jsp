
<table id="customerTable">
  <caption>
    <spring:message code="label.tr.searchtour.representative" />
  </caption>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerCode" /></td>
    <td id="customer-id-td" colspan="3">${f:h(output.customer.customerCode)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerKana" /></td>
    <td colspan="3">${f:h(output.customer.customerKana)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerName" /></td>
    <td colspan="3">${f:h(output.customer.customerName)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerBirth" /></td>
    <td colspan="3"><spring:message code="label.tr.common.datePattern" var="datePattern" /> <fmt:formatDate
        value="${output.customer.customerBirth}" pattern="${datePattern}" /></td>
  </tr>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerJob" /></td>
    <td colspan="3">${f:h(output.customer.customerJob)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message code="label.tr.searchtour.customerMail" /></td>
    <td colspan="3">${f:h(output.customer.customerMail)}</td>
  </tr>
  <tr>
    <td rowspan="3"><spring:message code="label.tr.searchtour.customerContactInfo" /></td>
    <td><spring:message code="label.tr.searchtour.customerTell" /></td>
    <td colspan="3">${f:h(output.customer.customerTel)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.customerPost" /></td>
    <td colspan="3">${f:h(output.customer.customerPost)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.customerAdd" /></td>
    <td colspan="3">${f:h(output.customer.customerAdd)}</td>
  </tr>
</table>