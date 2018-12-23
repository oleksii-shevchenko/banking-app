<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Transactions</title>

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
            <p class="h2"><fmt:message key="content.all.transactions.welcome" /></p>
        </div>
        <div class="col-2">
            <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                <input type="hidden" name="id" value="${requestScope.masterAccount}">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.all.transactions.account" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.transactions.id" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.all.transactions.type" /></p>
        </div>
        <div class="col-2">
            <p class="font-weight-bold"><fmt:message key="content.all.transactions.amount" /></p>
        </div>
        <div class="col-2">
            <p class="font-weight-bold"><fmt:message key="content.all.transactions.time" /></p>
        </div>
        <div class="col-2">
            <p class="font-weight-bold"><fmt:message key="content.all.transactions.info" /></p>
        </div>
    </div>
    <c:forEach items="${requestScope.page.items}" var="transaction">
        <div class="row justify-content-center my-2">
            <div class="col-3">
                <p>${transaction.id}</p>
            </div>
            <div class="col-3">
                <p>${transaction.type}</p>
            </div>
            <div class="col-2">
                <c:choose>
                    <c:when test="${requestScope.masterAccount eq transaction.receiver}">
                        <p class="text-info">+<ctg:balance balance="${transaction.amount}" currency="${transaction.currency}" /></p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger">-<ctg:balance balance="${transaction.amount}" currency="${transaction.currency}" /></p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-2">
                <p><ctg:time time="${transaction.time}" localeTag="${sessionScope.lang}" /></p>
            </div>
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/infoTransaction">
                    <input type="hidden" name="id" value="${transaction.id}" />
                    <input type="hidden" name="masterAccount" value="${requestScope.masterAccount}" />
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.all.transactions.info" /></button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<nav aria-label="page">
    <ul class="pagination justify-content-center">
        <li class="page-item ${requestScope.page.currentPage eq 1 ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/api/showTransactions?page=${requestScope.page.currentPage - 1}&accountId=${requestScope.masterAccount}">
                <span>&laquo;</span>
            </a>
        </li>
        <c:forEach var="i" begin="1" end="${requestScope.page.pagesNumber}">
            <li class="page-item ${requestScope.page.currentPage eq i ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/api/showTransactions?page=${i}&accountId=${requestScope.masterAccount}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${requestScope.page.currentPage eq requestScope.page.pagesNumber ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/api/showTransactions?page=${requestScope.page.currentPage + 1}&accountId=${requestScope.masterAccount}">
                <span>&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
