
<div class="container">
  <h2 id="screenName">
    <spring:message code="label.tr.managereservation.aftercancelScreenTitleMessage" />
  </h2>

  <div class="success">
    <spring:message code="label.tr.managereservation.aftercancelScreenFlowMessage" />
  </div>

  <br />
  <spring:message code="label.tr.managereservation.aftercancelScreenMessage"
    arguments="${f:h(reserveNo)}" />
  <br />
  <div class="span-8">

    <table>
      <tr>
        <td><form:form method="get"
            action="${f:h(pageContext.request.contextPath)}/reservations/me">
            <button id="returnToListScreenBtn">
              <spring:message code="label.tr.managereservation.returnToListScreenBtnMessage" />
            </button>
          </form:form></td>
        <td><form:form method="get" action="${f:h(pageContext.request.contextPath)}/">
            <button id="gotoMenuBtn">
              <spring:message code="label.tr.common.gotoMenuMessage" />
            </button>
          </form:form></td>
      </tr>
    </table>
  </div>
  <p>
    <br />
  </p>
  <p>
    <br />
  </p>
</div>

