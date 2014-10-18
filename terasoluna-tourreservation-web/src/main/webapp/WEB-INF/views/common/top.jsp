<sec:authorize ifNotGranted="ROLE_USER">
    <p id="messagesArea" class="box">
        <spring:message code="label.tr.common.notLoginMessage" />
    </p>
</sec:authorize>
<sec:authorize ifAnyGranted="ROLE_USER">
    <form:form action="${pageContext.request.contextPath}/logout" method="POST"
        cssClass="inline">
        <input id="logoutBtn" type="submit" name="logout"
            value="<spring:message code="label.tr.common.logout"/>"></input>
    </form:form>
</sec:authorize>