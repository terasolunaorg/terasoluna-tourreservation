
<table id="customerTable">
  <caption>
    <spring:message code="label.tr.common.notLoginMessage" />
  </caption>
  <tr>
    <td><spring:message code="label.tr.common.kana" /></td>
    <td>${f:h(customerForm.customerKana)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.name" /></td>
    <td>${f:h(customerForm.customerName)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.birthday" /></td>
    <td><c:set var="customerBirthString">
                ${f:h(customerForm.customerBirthYear)}/${f:h(customerForm.customerBirthMonth)}/${f:h(customerForm.customerBirthDay)}
            </c:set> <fmt:parseDate value="${customerBirthString}" pattern="yyyy/M/d" var="customerBirth" />
      <spring:message code="label.tr.common.datePattern" var="datePattern" /> <fmt:formatDate
        value="${customerBirth}" pattern="${datePattern}" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.job" /></td>
    <td>${f:h(customerForm.customerJob)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.email" /></td>
    <td>${f:h(customerForm.customerMail)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.tel" /></td>
    <td>${f:h(customerForm.customerTel)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.zip" /></td>
    <td>${f:h(customerForm.customerPost)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.address" /></td>
    <td>${f:h(customerForm.customerAdd)}</td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.common.password" /></td>
    <td>********</td>
  </tr>
</table>