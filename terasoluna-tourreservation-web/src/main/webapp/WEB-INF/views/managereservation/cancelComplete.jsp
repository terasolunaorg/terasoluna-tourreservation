
<div class="container">
  <h2 id="screenName">
    <spring:message code="label.tr.managereservation.aftercancelScreenTitleMessage" />
  </h2>

  <div class="success">
    <spring:message code="label.tr.managereservation.aftercancelScreenFlowMessage" />
  </div>

  <br />
  <spring:message code="label.tr.managereservation.aftercancelScreenMessage"
    arguments="${f:h(param.reserveNo)}" />
  <br />
  <div class="span-5">

    <table>
      <tr>
        <td><form:form action="${f:h(pageContext.request.contextPath)}/managereservation/list"
            method="GET">
            <spring:message code="label.tr.managereservation.returnToListScreenBtnMessage"
              var="returnToListScreenButton" />
            <input type="submit" value="${f:h(returnToListScreenButton)}">
          </form:form></td>
        <td><form:form action="${f:h(pageContext.request.contextPath)}/" method="GET">
            <spring:message code="label.tr.common.gotoMenuMessage" var="gotoMenuButton" />
            <input type="submit" value="${f:h(gotoMenuButton)}">
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

