
<table id="priceTable">
  <caption>
    <spring:message code="label.tr.searchtour.statementOfCharges" />
  </caption>
  <spring:message code="label.tr.common.currencyPattern" var="currencyPattern" />
  <spring:message code="label.tr.common.personcountPattern" var="personPattern" />
  <tr>
    <td><spring:message code="label.tr.searchtour.classificationOnAge" /></td>
    <td><spring:message code="label.tr.searchtour.unitPrice" /></td>
    <td><spring:message code="label.tr.searchtour.noOfPeople" /></td>
    <td><spring:message code="label.tr.searchtour.charge" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.adult" /></td>
    <td><fmt:formatNumber pattern="${currencyPattern}"
        value="${f:h(output.priceCalculateOutput.adultUnitPrice)}" /></td>
    <td><fmt:formatNumber pattern="${personPattern}"
        value="${f:h(output.priceCalculateOutput.adultCount)}" /></td>
    <td><fmt:formatNumber pattern="${currencyPattern}"
        value="${f:h(output.priceCalculateOutput.adultPrice)}" /></td>
  </tr>
  <tr>
    <td><spring:message code="label.tr.searchtour.child" /></td>
    <td><fmt:formatNumber pattern="${currencyPattern}"
        value="${f:h(output.priceCalculateOutput.childUnitPrice)}" /></td>
    <td><fmt:formatNumber pattern="${personPattern}"
        value="${f:h(output.priceCalculateOutput.childCount)}" /></td>
    <td><fmt:formatNumber pattern="${currencyPattern}"
        value="${f:h(output.priceCalculateOutput.childPrice)}" /></td>
  </tr>
  <tr>
    <td colspan="3"><spring:message code="label.tr.searchtour.sumPrice" /></td>
    <td><fmt:formatNumber pattern="${currencyPattern}"
        value="${f:h(output.priceCalculateOutput.sumPrice)}" /></td>
  </tr>
</table>