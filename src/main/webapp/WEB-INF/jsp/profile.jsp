<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>User profile</title>

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

<jsp:include page="../components/navbar.jsp" />

<div class="container my-2">
    <div class="row justify-content-start my-3">
        <div class="col-3">
            <div class="h2">
                <fmt:message key="content.profile.user.welcome" />
            </div>
        </div>
        <c:if test="${sessionScope.role eq 'USER'}" >
            <div class="col-3">
                <form method="post" action="${pageContext.request.contextPath}/api/showAccounts">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.profile.user.accounts" /></button>
                </form>
            </div>
        </c:if>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.profile.user.id" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.id}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.profile.user.login" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.login}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary"><fmt:message key="content.profile.user.email" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.email}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary"><fmt:message key="content.profile.user.role" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.role}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary"><fmt:message key="content.profile.user.first.name" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.firstName}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary"><fmt:message key="content.profile.user.second.name" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.user.secondName}</p>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
