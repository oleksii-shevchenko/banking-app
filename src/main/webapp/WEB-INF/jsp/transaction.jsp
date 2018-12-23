<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Transaction</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-grid.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-reboot.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
</head>

<body>
<fmt:setLocale value="${sessionScope.lang}" scope="session" />
<fmt:setBundle basename="content" />

<jsp:include page="../components/navbar.jsp" />

<div class="container my-2">
    <div class="row justify-content-start my-3">
        <div class="col-3">
            <div class="h2">
                <fmt:message key="content.transaction.welcome" />
            </div>
        </div>
        <c:if test="${not empty requestScope.masterAccount}" >
            <div class="col-3">
                <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                    <input type="hidden" name="id" value="${requestScope.masterAccount}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.transaction.account" /></button>
                </form>
            </div>
        </c:if>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.transaction.id" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.transaction.id}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.transaction.time" /></p>
        </div>
        <div class="col-4">
            <p class="text-left"><ctg:time time="${requestScope.transaction.time}" localeTag="${sessionScope.lang}" /></p>
        </div>
    </div>
    <c:if test="${requestScope.transaction.type eq 'MANUAL'}">
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary text"><fmt:message key="content.transaction.sender" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.transaction.sender}</p>
            </div>
        </div>
    </c:if>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.transaction.receiver" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.transaction.receiver}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.transaction.account" /></p>
        </div>
        <div class="col-4">
            <p class="text-left"><ctg:balance balance="${requestScope.transaction.amount}" currency="${requestScope.transaction.currency}" /></p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.transaction.currency" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.transaction.currency}</p>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
