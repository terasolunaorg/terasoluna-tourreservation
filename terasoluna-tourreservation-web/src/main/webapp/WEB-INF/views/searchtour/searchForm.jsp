
<div class="container">
  <jsp:include page="../common/top.jsp" />
  <h2 id="screenName">
    <spring:message code="label.tr.searchtour.searchTourMessage" />
  </h2>
  <div class="info">
    <spring:message code="label.tr.searchtour.searchTourFlowMessage" />
    <spring:message code="label.tr.searchtour.searchTourInstructionsMessage" />
  </div>

  <div class="span-24 last">
    <form:form method="get" modelAttribute="searchTourForm" cssClass="inline">
      <fieldset>
        <legend>
          <spring:message code="label.tr.searchtour.SearchCriteriaTourMessage" />
        </legend>
        <form:errors path="*" cssClass="error" element="div" />
        <div class="span-14">
          <form:label path="depYear">
            <spring:message code="label.tr.common.depDate" />
          </form:label>
          <form:select path="depYear" items="${CL_DEP_YEAR}" />
          <spring:message code="label.tr.common.year" />
          <form:select path="depMonth" items="${CL_MONTH}" />
          <spring:message code="label.tr.common.month" />
          <form:select path="depDay" items="${CL_DAY}" />
          <spring:message code="label.tr.common.day" />
          <div>
            (
            <spring:message code="label.tr.searchtour.depDateRestrictMessage" />
            )
          </div>
        </div>
        <div class="span-8 last">
          <form:label path="tourDays">
            <spring:message code="label.tr.common.tourDays" />
          </form:label>
          <form:select path="tourDays" items="${CL_TOUR_DAYS}" />
        </div>
        <spring:message code="label.tr.searchtour.placeMessage" var="place_label" />
        <div class="span-14">
          <form:label path="depCode">
            <spring:message code="label.tr.common.depPlace" />
          </form:label>
          <form:select path="depCode">
            <form:option value="" label="${place_label}" />
            <form:options items="${CL_PREF}" />
          </form:select>
        </div>
        <div class="span-8 last">
          <form:label path="arrCode">
            <spring:message code="label.tr.common.arrPlace" />
          </form:label>
          <form:select path="arrCode">
            <form:option value="" label="${place_label}" />
            <form:options items="${CL_PREF}" />
          </form:select>
        </div>
        <div class="span-14">
          <form:label path="adultCount">
            <spring:message code="label.tr.common.persons" />
          </form:label>
          <spring:message code="label.tr.common.adult" />
          <form:select path="adultCount" items="${CL_ADULT_COUNT}" />
          <spring:message code="label.tr.common.person" />
          &nbsp;&nbsp;
          <spring:message code="label.tr.common.child" />
          <form:select path="childCount" items="${CL_CHILD_COUNT}" />
          <spring:message code="label.tr.common.person" />
          <div>
            (
            <spring:message code="label.tr.searchtour.personsRestrictMessage" />
            )
          </div>
        </div>
        <div class="span-8 last">
          <form:label path="basePrice">
            <spring:message code="label.tr.common.basePrice" />
          </form:label>
          <form:select path="basePrice" items="${CL_BASE_PRICE}" />
        </div>
        <div class="span-24 last">
          <form:button id="searchBtn">
            <spring:message code="label.tr.common.search" />
          </form:button>
        </div>
      </fieldset>
    </form:form>
  </div>
  <hr>
  <c:if test="${page != null}">
    <spring:message code="label.tr.common.currencyPattern" var="currencyPattern" />
    <spring:message code="label.tr.common.datePattern" var="datePattern" />
    <p class="notice">
      <spring:message code="label.tr.searchtour.resultCountMessage"
        arguments="${page.totalElements}" />
    </p>
    <div class="pagination">
      <t:pagination page="${page}" criteriaQuery="${f:query(searchTourForm)}" />
    </div>
    <table id="toursTable">
      <caption>
        <spring:message code="label.tr.searchtour.searchTourResultMessage" />
      </caption>
      <thead>
        <tr>
          <th><spring:message code="label.tr.common.idx" /></th>
          <th><spring:message code="label.tr.common.tourName" /></th>
          <th><spring:message code="label.tr.common.depDate" /></th>
          <th><spring:message code="label.tr.common.tourDays" /></th>
          <th><spring:message code="label.tr.common.depPlace" /></th>
          <th><spring:message code="label.tr.common.arrPlace" /></th>
          <th><spring:message code="label.tr.common.basePrice" /></th>
        </tr>
      </thead>
      <c:forEach var="tourInfo" items="${page.content}" varStatus="rowStatus">
        <tr>
          <td>${(page.number * page.size) + rowStatus.index + 1}</td>
          <td><c:set var="operationPath"></c:set> <sec:authorize access="hasRole('USER')">
              <c:set var="operationPath">/reserve</c:set>
            </sec:authorize> <a
            href="${pageContext.request.contextPath}/tours/${f:h(tourInfo.tourCode)}${operationPath}?form&${f:query(searchTourForm)}&page=${f:h(page.number)}&size=${f:h(page.size)}">
              ${f:h(tourInfo.tourName)} </a></td>
          <td><fmt:formatDate value="${tourInfo.depDay}" pattern="${datePattern}" /></td>
          <td>${f:h(tourInfo.tourDays)}</td>
          <td>${f:h(tourInfo.departure.depName)}</td>
          <td>${f:h(tourInfo.arrival.arrName)}</td>
          <td><fmt:formatNumber value="${f:h(tourInfo.basePrice)}" pattern="${currencyPattern}" /></td>
        </tr>
      </c:forEach>
    </table>
    <div class="pagination">
      <t:pagination page="${page}" criteriaQuery="${f:query(searchTourForm)}" />
    </div>
  </c:if>
</div>
