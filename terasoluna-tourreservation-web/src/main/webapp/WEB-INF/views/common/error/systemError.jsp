<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />

<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/blueprint-1.0/950px/screen.css"
  type="text/css" media="screen, projection">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/blueprint-1.0/950px/print.css"
  type="text/css" media="print">
<!--[if lt IE 8]><link rel="stylesheet" href="${pageContext.request.contextPath}/css/blueprint-1.0/950px/ie.css" type="text/css" media="screen, projection"><![endif]-->
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/blueprint-1.0/plugins/fancy-type/screen.css"
  type="text/css" media="screen, projection">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css"
  type="text/css" media="screen, projection">

<script type="text/javascript"
  src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>

<c:set var="titleKey">
  <tiles:insertAttribute name="title" ignore="true" />
</c:set>
<title><spring:message code="${titleKey}" text="Terasoluna Tour Web" /></title>
</head>

<body>
  <div class="container">
    <jsp:include page="../../layout/header.jsp" />
    <div class="error">
      <c:if test="${!empty exceptionCode}">
        <ul>
          <li><spring:message code="${f:h(exceptionCode)}" /></li>
        </ul>
      </c:if>
      <c:if test="${empty exceptionCode}">
        <ul>
          <li><spring:message code="e.tr.fw.0003" /></li>
        </ul>
      </c:if>
    </div>
    <jsp:include page="../../layout/footer.jsp" />
  </div>
</body>
</html>