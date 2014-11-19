
<div class="container">
 <jsp:include page="../common/top.jsp" />
	<h2 id="screenName">
		<spring:message code="label.tr.searchtour.titleConfirmScreenMessage" />
	</h2>

	<div class="info">
		<spring:message code="label.tr.searchtour.confirmScreenFlowMessage" />
	</div>
	<p>
		<spring:message code="label.tr.searchtour.pressReserveBtnMessage" />
	</p>
	<div class="span-24">
		<br>

		<jsp:include page="../common/fragment/tourInfoTable.jsp" />
		<br>
		<jsp:include page="../common/fragment/customerTable.jsp" />
		<br>
		<hr>
		<jsp:include page="../common/fragment/priceTable.jsp" />
		<br>
		<hr />
		<table id="specialNotesTable">
			<caption>
				<spring:message code="label.tr.common.specialNotes"></spring:message>
			</caption>
			<tr>
				<td><spring:message code="label.tr.common.opinionSuggestion"></spring:message></td>
				<td colspan="3">${f:h(reserveTourForm.remarks)}<br />
			</tr>
		</table>
	</div>
	<div class="span-4 append-20">
		<table>
			<tr>
				<td><form:form action="read" method="POST">
						<input id="goBackBtn" type="submit" name="redo"
							value="<spring:message code="label.tr.common.goBackMessage"/>">
					</form:form></td>
				<td><form:form
						action="${pageContext.request.contextPath}/reservetour/reserve"
						modelAttribute="reserveTourForm" method="POST">
						<input id="reserveBtn" type="submit"
							value="<spring:message code="label.tr.searchtour.confirmedMessage"/>">
					</form:form></td>
			</tr>
		</table>
	</div>
</div>


