<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Requests</title>

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

<div class="container my-lg-4">
    <div class="row justify-content-start">
        <div class="col-3">
            <p class="h2"><fmt:message key="content.all.requests.welcome" /></p>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.requests.id" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.requests.requester" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.requests.considered" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.requests.info" /></p>
        </div>
    </div>
    <c:forEach items="${requestScope.page.items}" var="request">
        <div class="row justify-content-center my-2">
            <div class="col-3">
                <p>${request.id}</p>
            </div>
            <div class="col-3">
                <p>${request.requesterId}</p>
            </div>
            <div class="col-3">
                <c:choose>
                    <c:when test="${request.considered}">
                        <p class="text-secondary"><fmt:message key="content.all.requests.considered.true" /></p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-info"><fmt:message key="content.all.requests.considered.false" /></p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-3">
                <form method="post" action="${pageContext.request.contextPath}/api/processRequest">
                    <input type="hidden" name="requestId" value="${request.id}" />
                    <button class="btn btn-lg btn-primary btn-block" type="submit" ${request.considered ? 'disabled' : ''}><fmt:message key="content.all.requests.info" /></button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<nav aria-label="page">
    <ul class="pagination justify-content-center">
        <li class="page-item ${requestScope.page.currentPage eq 1 ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/api/showRequests?page=${requestScope.page.currentPage - 1}&accountId=${requestScope.masterAccount}">
                <span>&laquo;</span>
            </a>
        </li>
        <c:forEach var="i" begin="1" end="${requestScope.page.pagesNumber}">
            <li class="page-item ${requestScope.page.currentPage eq i ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/api/showRequests?page=${i}&accountId=${requestScope.masterAccount}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${requestScope.page.currentPage eq requestScope.page.pagesNumber ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/api/showRequests?page=${requestScope.page.currentPage + 1}&accountId=${requestScope.masterAccount}">
                <span>&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<jsp:include page="../components/footer.jsp" />
</body>
</html>

