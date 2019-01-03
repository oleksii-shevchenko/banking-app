<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Error!</title>

    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css" />">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-grid.css" />">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-reboot.css" />">
    <link rel="stylesheet" href="<c:url value="/css/custom.css" />">

    <script src="<c:url value="/js/jquery-3.2.1.slim.min.js" />"></script>
    <script src="<c:url value="/js/popper.min.js" />"></script>
    <script src="<c:url value="/js/bootstrap.js" />"></script>
    <script src="<c:url value="/js/bootstrap.bundle.js" />"></script>
</head>

<body>
<fmt:setLocale value="${sessionScope.lang}" scope="session" />
<fmt:setBundle basename="content" />

<jsp:include page="WEB-INF/components/navbar.jsp" />

<div class="container">
    <div class="row justify-content-center">
        <p style="font-size: 64px"><fmt:message key="content.error.text" /></p>
    </div>
    <div class="row justify-content-center">
        <a href="${pageContext.request.contextPath}/index.jsp" class="text-center" style="font-size: 48px"><fmt:message key="content.error.home" /></a>
    </div>
</div>

<jsp:include page="WEB-INF/components/footer.jsp" />
</body>
</html>
