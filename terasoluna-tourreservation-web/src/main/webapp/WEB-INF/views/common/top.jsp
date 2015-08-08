<sec:authorize access="!hasRole('ROLE_USER')">
    <p id="messagesArea" class="box">
        <spring:message code="label.tr.common.notLoginMessage" />
    </p>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
    <form:form action="${pageContext.request.contextPath}/logout" cssClass="inline">
        <button id="logoutBtn" name="logout">
            <spring:message code="label.tr.common.logout"/>
        </button>
    </form:form>
</sec:authorize>