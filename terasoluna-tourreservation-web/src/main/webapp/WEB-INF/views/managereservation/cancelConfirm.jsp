
<div class="container">
	<h2 id="screenName">
		<spring:message
			code="label.tr.managereservation.beforecancelScreenTitleMessage" />
	</h2>

	<div class="info">
		<spring:message
			code="label.tr.managereservation.beforecancelScreenFlowMessage" />
	</div>
	<p>
		<spring:message
			code="label.tr.managereservation.beforeCancelScreenMessage" />
	</p>
	<div class="span-24">
		<t:messagesPanel />
		<br>
		<jsp:include page="../common/fragment/reserveTable.jsp" />
		<br>
		<jsp:include page="../common/fragment/customerTable.jsp" />
		<br>
		<hr>
		<jsp:include page="../common/fragment/priceTable.jsp" />
		<br>
		<hr />
		<table>
			<caption>
				<spring:message code="label.tr.common.specialNotes"></spring:message>
			</caption>
			<tr>
				<td><spring:message code="label.tr.common.opinionSuggestion"></spring:message></td>
				<td colspan="3">${f:h(output.reserve.remarks)}<br />
			</tr>
		</table>
	</div>
	<div class="span-4 append-20">
		<table>
			<tr>
				<td><form:form
						action="${pageContext.request.contextPath}/managereservation/list"
						method="GET">
						<spring:message
							code="label.tr.managereservation.returnToListScreenBtnMessage"
							var="returnToListScreen" />
						<input id="backToListBtn" type="submit" value="${returnToListScreen}" tabindex="2" />
					</form:form></td>
				<td><form:form id="reservationCanelForm"
						action="${pageContext.request.contextPath}/managereservation/cancel"
						method="POST">
						<input type="hidden" name="reserveNo"
							value="${f:h(output.reserve.reserveNo)}">
						<input id="cancelBtn" type="submit"
							value="<spring:message code="label.tr.managereservation.cancelReservationBtnMessage"/>">
					</form:form></td>
			</tr>
		</table>
	</div>
</div>

