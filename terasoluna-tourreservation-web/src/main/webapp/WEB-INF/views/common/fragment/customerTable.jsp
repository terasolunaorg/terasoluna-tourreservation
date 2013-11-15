
<table>
  <caption>
    <spring:message code="label.tr.searchtour.representative"></spring:message>
  </caption>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerCode"></spring:message></td>

    <td id="customer-id-td" colspan="3">${f:h(output.customer.customerCode)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerKana"></spring:message></td>

    <td colspan="3">${f:h(output.customer.customerKana)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerName"></spring:message></td>

    <td colspan="3">${f:h(output.customer.customerName)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerBirth"></spring:message></td>

    <td colspan="3"><fmt:formatDate
        value="${output.customer.customerBirth}" pattern="yyyy/MM/dd" /></td>
  </tr>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerJob"></spring:message></td>

    <td colspan="3">${f:h(output.customer.customerJob)}</td>
  </tr>
  <tr>
    <td colspan="2"><spring:message
        code="label.tr.searchtour.customerMail"></spring:message></td>

    <td colspan="3">${f:h(output.customer.customerMail)}</td>
  </tr>
  <tr>
    <td rowspan="3"><spring:message
        code="label.tr.searchtour.customerContactInfo"></spring:message></td>

    <td><spring:message code="label.tr.searchtour.customerTell"></spring:message></td>
    <td colspan="3">${f:h(output.customer.customerTel)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.customerPost"></spring:message></td>
    <td colspan="3">${f:h(output.customer.customerPost)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.customerAdd"></spring:message></td>
    <td colspan="3">${f:h(output.customer.customerAdd)}</td>
  </tr>
</table>