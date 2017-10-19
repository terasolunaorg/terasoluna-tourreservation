
<div class="container">

  <h2 id="screenName">
    <spring:message code="label.tr.managereservation.manageReservationConfirmScreenTitleMessage" />
  </h2>

  <!-- end title -->

  <!-- begin main -->
  <div class="info">
    <spring:message code="label.tr.managereservation.manageReservationConfirmScreenFlowMessage" />
  </div>
  <!-- begin message -->
  <spring:message code="label.tr.managereservation.manageReservationConfirmDetailMessage" />
  <!-- end message -->
  <spring:message code="label.tr.common.currencyPattern" var="currencyPattern" />
  <spring:message code="label.tr.common.personcountPattern" var="personPattern" />
  <spring:message code="label.tr.common.datePattern" var="datePattern" />
  <table>
    <caption>
      <spring:message code="label.tr.searchtour.tourDetailCaptionMessage" />
    </caption>
    <tr>
      <th><spring:message code="label.tr.searchtour.reserveNo" /></th>
      <td>${f:h(output.reserve.reserveNo)}</td>
      <th><spring:message code="label.tr.searchtour.reserveDate" /></th>
      <td><fmt:formatDate value="${output.reserve.reservedDay}" pattern="${datePattern}" /></td>
    </tr>
    <tr>
      <th><spring:message code="label.tr.searchtour.tourname" /></th>
      <td colspan="3">${f:h(output.reserve.tourInfo.tourName)}</td>
    </tr>
    <tr>
      <th><spring:message code="label.tr.searchtour.depDay" /></th>
      <td>${f:h(output.reserve.tourInfo.depDay)}</td>
      <th><spring:message code="label.tr.searchtour.tourDays" /></th>
      <td>${f:h(output.reserve.tourInfo.tourDays)}</td>
    </tr>
    <tr>
      <th><spring:message code="label.tr.searchtour.depName" /></th>
      <td>${f:h(output.reserve.tourInfo.departure.depName)}</td>
      <th><spring:message code="label.tr.searchtour.arrName" /></th>
      <td>${f:h(output.reserve.tourInfo.arrival.arrName)}</td>
    </tr>
  </table>
  <!-- tour-info end -->
  <br />
  <hr />
  <table id="priceTable">
    <caption>
      <spring:message code="label.tr.searchtour.statementOfCharges" />
    </caption>
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
  <br />
  <hr />
  <!-- note start -->
  <table>
    <caption>
      <spring:message code="label.tr.common.specialNotes" />
    </caption>
    <tr>
      <td><spring:message code="label.tr.common.opinionSuggestion" /></td>
      <td colspan="3">${f:h(output.reserve.remarks)}<br />
    </tr>
  </table>
  <br />
  <spring:message code="label.tr.managereservation.manageReservationConfirmCautionMessage" />
  <br />
  <hr />
  <table>
    <caption>
      <spring:message code="label.tr.common.paymentInfo" />
    </caption>
    <tr>
      <td><spring:message code="label.tr.common.paymentMethod" /></td>
      <td colspan="3"><spring:message code="label.tr.common.bankTransfer" /></td>
    </tr>
    <tr>
      <td><spring:message code="label.tr.common.paymentAccount" /></td>
      <td colspan="3"><spring:message code="label.tr.common.companyName" /> <br /> <spring:message
          code="label.tr.common.savingsAccount" /></td>
    </tr>
    <tr>
      <td><spring:message code="label.tr.common.paymentTimeLimit" /></td>
      <td colspan="3"><fmt:formatDate value="${output.paymentTimeLimit}"
          pattern="${datePattern}" /></td>
    </tr>
    <tr>
      <td><spring:message code="label.tr.common.paymentInquiry" /></td>
      <td colspan="3"><spring:message code="label.tr.common.paymentCompanyname" /> <spring:message
          code="label.tr.common.companyTel" /> <spring:message code="label.tr.common.companyEmail" />
      </td>
    </tr>
  </table>
  <br />
  <spring:message code="label.tr.managereservation.manageReservationConfirmMessage" />
  <br />
  <!-- begin buttons -->
  <form:form modelAttribute="manageReservationForm" cssClass="inline">

    <form:hidden path="adultCount" />
    <form:hidden path="childCount" />

    <div class="span-6">
      <table>
        <tr>
          <td>
            <button id="backToFormBtn" name="redo">
              <spring:message code="label.tr.managereservation.changeReservationStaBtnMessage" />
            </button>
          </td>
          <td>
            <button id="changeBtn">
              <spring:message code="label.tr.managereservation.changeReservationFinBtnMessage" />
            </button>
          </td>
        </tr>
      </table>
    </div>
  </form:form>
  <!-- end buttons -->
</div>
