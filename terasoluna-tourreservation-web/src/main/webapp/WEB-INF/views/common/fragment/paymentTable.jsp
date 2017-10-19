
<table id="paymentTable">
  <caption>
    <spring:message code="label.tr.common.paymentInfo" />
  </caption>
  <tr>
    <td><spring:message code="label.tr.common.paymentMethod" /></td>
    <td colspan="3"><spring:message code="label.tr.common.bankTransfer" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.paymentAccount" /></td>
    <td colspan="3"><spring:message code="label.tr.common.paymentCompanyname" /> <br /> <spring:message
        code="label.tr.common.savingsAccount" /></td>
  </tr>
  <tr>
    <spring:message code="label.tr.common.datePattern" var="datePattern" />
    <td><spring:message code="label.tr.common.paymentTimeLimit" /></td>
    <td colspan="3"><fmt:formatDate value="${output.paymentTimeLimit}" pattern="${datePattern}" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.paymentInquiry" /></td>
    <td colspan="3"><spring:message code="label.tr.common.companyName" /> <spring:message
        code="label.tr.common.companyTel" /> <spring:message code="label.tr.common.companyEmail" />
    </td>
  </tr>
</table>