
<div class="container">
	<jsp:include page="../common/top.jsp" />
	<h2>
		<spring:message code="label.tr.searchtour.searchTourMessage" />
	</h2>
	<div class="info">
		<spring:message code="label.tr.searchtour.searchTourFlowMessage" />
		<spring:message
			code="label.tr.searchtour.searchTourInstructionsMessage" />
	</div>

	<div class="span-24 last">
		<form:form method="get" modelAttribute="tourInfoSearchCriteria"
			cssClass="inline">
			<fieldset>
				<legend>
					<spring:message
						code="label.tr.searchtour.SearchCriteriaTourMessage" />
				</legend>
				<form:errors path="*" cssClass="error" element="div" />
				<div class="span-14">
					<form:label path="depYear">
						<spring:message code="label.tr.common.depDate" />
					</form:label>
					<form:select path="depYear" items="${CL_YEAR}" />
					<spring:message code="label.tr.common.year" />
					<form:select path="depMonth" items="${CL_MONTH}" />
					<spring:message code="label.tr.common.month" />
					<form:select path="depDay" items="${CL_DAY}" />
					<spring:message code="label.tr.common.day" />
					<br> (
					<spring:message code="label.tr.searchtour.depDateRestrictMessage" />
					)
				</div>
				<div class="span-8 last">
					<form:label path="tourDays">
						<spring:message code="label.tr.common.tourDays" />
					</form:label>
					<form:select path="tourDays" items="${CL_DAYS}" />
				</div>
				<spring:message code="label.tr.searchtour.placeMessage"
					var="place_label" />
				<div class="span-14">
					<form:label path="depCode">
						<spring:message code="label.tr.common.depPlace" />
					</form:label>
					<form:select path="depCode">
						<form:option value="" label="${f:h(place_label)}" />
						<form:options items="${CL_PREF}" />
					</form:select>
				</div>
				<div class="span-8 last">
					<form:label path="arrCode">
						<spring:message code="label.tr.common.arrPlace" />
					</form:label>
					<form:select path="arrCode">
						<form:option value="" label="${f:h(place_label)}" />
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
					(
					<spring:message code="label.tr.searchtour.personsRestrictMessage" />
					)
				</div>
				<div class="span-8 last">
					<form:label path="basePrice">
						<spring:message code="label.tr.common.basePrice" />
					</form:label>
					<form:select path="basePrice" items="${CL_BASE_PRICE}" />
				</div>
				<div class="span-24 last">
					<input id="search" type="submit"
						name="search"
						value="<spring:message code="label.tr.common.search" />">
				</div>
			</fieldset>
		</form:form>
	</div>
	<hr>
	<c:if test="${page != null}">
		<spring:message code="label.tr.common.currencyPattern"
			var="currencyPattern" />
		<spring:message code="label.tr.common.datePattern" var="datePattern" />
		<p class="notice">
			<spring:message code="label.tr.searchtour.resultCountMessage"
				arguments="${page.totalElements}" />
		</p>
		<div class="pagination">
			<t:pagination page="${page}" criteriaQuery="${f:query(tourInfoSearchCriteria)}" />
		</div>
		<table>
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
			<c:forEach var="tourInfo" items="${page.content}" varStatus="sts">
				<tr>
					<td>${page.number * page.size + sts.index + 1}</td>
					<td>
					<div>
					<a href="${pageContext.request.contextPath}/reservetour/read?tourCode=${f:h(tourInfo.tourCode)}&${f:query(tourInfoSearchCriteria)}&page=${f:h(page.number)}&size=${f:h(page.size)}" id="${page.number * page.size + sts.index + 1}">
							${f:h(tourInfo.tourName)} </a></div></td>
					<td><fmt:formatDate value="${tourInfo.depDay}"
							pattern="${datePattern}" /></td>
					<td>${f:h(tourInfo.tourDays)}</td>
					<td>${f:h(tourInfo.departure.depName)}</td>
					<td>${f:h(tourInfo.arrival.arrName)}</td>
					<td><fmt:formatNumber value="${f:h(tourInfo.basePrice)}"
							pattern="${currencyPattern}" /></td>
				</tr>
			</c:forEach>
		</table>
		<div class="pagination">
			<t:pagination page="${page}" criteriaQuery="${f:query(tourInfoSearchCriteria)}" />
		</div>
	</c:if>
</div>
