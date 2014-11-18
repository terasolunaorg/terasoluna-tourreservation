<div class="container">
 <jsp:include page="../common/top.jsp" />
	<h2 id="screenName">
		<spring:message code="label.tr.searchtour.titleDetailScreenMessage" />
	</h2>

	<div class="info">
		<spring:message code="label.tr.searchtour.detailScreenFlowMessage" />
	</div>

	<sec:authorize ifNotGranted="ROLE_USER">
		<p>
			<spring:message code="label.tr.searchtour.loginToReserveMessage" />
		</p>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
		<p>
			<spring:message code="label.tr.searchtour.pressReserveBtnMessage" />
		</p>
	</sec:authorize>
	<div class="span-24">
		<t:messagesPanel />
		<form:errors path="*" class="error" />
		<br>
		<jsp:include page="../common/fragment/tourInfoTable.jsp" />
		<br>
		<sec:authorize ifAllGranted="ROLE_USER">
			<br>
			<jsp:include page="../common/fragment/customerTable.jsp" />
		</sec:authorize>
		<br>
		<jsp:include page="../common/fragment/priceTable.jsp" />
	</div>
	<sec:authorize ifAllGranted="ROLE_USER">
		<br>
		<table>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<form:form
				action="${pageContext.request.contextPath}/reservetour/reserve"
				method="POST" modelAttribute="reserveTourForm">
				<table>
					<form:errors path="*" cssClass="error" element="div" />
					<caption>
						<spring:message code="label.tr.common.specialNotes"></spring:message>
					</caption>
					<tr>
						<td><spring:message code="label.tr.common.opinionSuggestion"></spring:message></td>
						<td><form:textarea path="remarks" tabindex="1" /> <br>
							<spring:message
								code="label.tr.searchtour.lessThanEightyCharsMessage"></spring:message>
							<br> <spring:message
								code="label.tr.searchtour.opinionSuggestionMessage"></spring:message></td>
					</tr>
					<form:hidden path="tourCode" />
					<form:hidden path="childCount" />
					<form:hidden path="adultCount" />
				</table>
				<br />
				<input id="confirmBtn" type="submit" name="confirm"
					value="<spring:message code="label.tr.common.confirm" />" />
			</form:form>
			<form:form
				action="${pageContext.request.contextPath}/searchtour/search"
				method="get">
				<input id="backToToursBtn" type="submit"
					value="<spring:message code="label.tr.common.goBackMessage"/>">
				<input type="hidden" name="page"
					value="${f:h(param['page'])}" />
				<input type="hidden" name="size"
					value="${f:h(param['size'])}" />
			</form:form>
		</table>
	</sec:authorize>
	<sec:authorize ifNotGranted="ROLE_USER">
		<form:form action="${pageContext.request.contextPath}/login"
			method="get" cssClass="inline">
			<input id="loginBtn" type="submit"
				value="<spring:message code="label.tr.menu.loginBtnMessage" />">
			<input type="hidden" name="redirectTo"
				value="${pageContext.request.contextPath}/reservetour/read?${f:query(reserveTourForm)}&page=${f:h(param['page'])}&size=${f:h(param['size'])}" />
		</form:form>
		<form:form
			action="${pageContext.request.contextPath}/searchtour/search"
			method="get" cssClass="inline">
			<input type="hidden" name="page"
					value="${f:h(param['page'])}" />
			<input type="hidden" name="size"
					value="${f:h(param['size'])}" />
			<input id="backToToursBtn" type="submit" value="<spring:message code="label.tr.common.goBackMessage"/>">
		</form:form>
	</sec:authorize>
</div>

