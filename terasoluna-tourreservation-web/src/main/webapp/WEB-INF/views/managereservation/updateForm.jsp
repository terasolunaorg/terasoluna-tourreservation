
<div class="container">
	<h2>
		<span><spring:message
				code="label.tr.managereservation.manageReservationEditScreenTitle" /></span>
	</h2>

	<!-- end title -->

	<!-- begin main -->
	<div class="info">
		<spring:message
			code="label.tr.managereservation.manageReservationEditFlowMessage" />
	</div>
	<!-- begin message -->
	<spring:message
		code="label.tr.managereservation.manageReservationEditMessage" />
	<!-- end message -->
	<form:form method="post" modelAttribute="manageReservationForm"
		action="${pageContext.request.contextPath}/managereservation/update">
		<table>
			<caption>
				<spring:message code="label.tr.searchtour.tourDetailCaptionMessage" />
			</caption>
			<spring:message code="label.tr.common.datePattern" var="datePattern" />
			<tr>
				<th><spring:message code="label.tr.searchtour.reserveNo" /></th>
				<td>${f:h(reserve.reserveNo)}</td>
				<th><spring:message code="label.tr.searchtour.reserveDate" /></th>
				<td><fmt:formatDate value="${reserve.reservedDay}"
						pattern="${datePattern}" /></td>
			</tr>
			<tr>
				<th><spring:message code="label.tr.searchtour.tourname" /></th>
				<td colspan="3">${f:h(reserve.tourInfo.tourName)}</td>
			</tr>
			<tr>
				<th><spring:message code="label.tr.searchtour.depDay" /></th>
				<td><fmt:formatDate value="${reserve.tourInfo.depDay}"
						pattern="${datePattern}" /></td>
				<th><spring:message code="label.tr.searchtour.tourDays" /></th>
				<td>${f:h(reserve.tourInfo.tourDays)}</td>
			</tr>
			<tr>
				<th><spring:message code="label.tr.searchtour.depName" /></th>
				<td>${f:h(reserve.tourInfo.departure.depName)}</td>
				<th><spring:message code="label.tr.searchtour.arrName" /></th>
				<td>${f:h(reserve.tourInfo.arrival.arrName)}</td>
			</tr>
			<tr>
				<th><spring:message code="label.tr.common.persons" /></th>
				<td colspan="3"><spring:message code="label.tr.common.adult" />
					<form:select path="adultCount">
						<form:options items="${CL_ADULT_COUNT}" />
					</form:select> <spring:message code="label.tr.common.person" /> <spring:message
						code="label.tr.common.child" /> <form:select path="childCount"
						items="${CL_CHILD_COUNT}" /> <spring:message
						code="label.tr.common.person" /></td>
			</tr>
		</table>

		<!-- tour-info end -->
		<br />
		<br />
		<!-- note start -->
		<table>
			<caption>
				<spring:message code="label.tr.common.specialNotes"></spring:message>
			</caption>
			<tr>
				<td><spring:message code="label.tr.common.opinionSuggestion"></spring:message></td>
				<td colspan="3">${f:h(reserve.remarks)}<br />
			</tr>
		</table>
		<br />
		<!-- note end -->

		<!-- begin buttons -->
		<div class="span-4 append-20">
			<table>
				<tr>
					<td>
						<div class="button">
							<spring:message
								code="label.tr.managereservation.returnToListScreenBtnMessage"
								var="returnToListScreen" />
							<input type="submit" name="backTolist"
								value="${returnToListScreen}" tabindex="2" />
						</div>
					</td>
					<td>
						<div class="button">
							<spring:message
								code="label.tr.managereservation.confirmEditBtnMessage"
								var="changeReservationButton" />
							<input type="hidden" name="reserveNo"
								value="${f:h(reserve.reserveNo)}" /> <input type="submit"
								name="confirm" value="${changeReservationButton}" />
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- end buttons -->
	</form:form>
</div>
