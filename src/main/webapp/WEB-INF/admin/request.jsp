<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>


<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Process request</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-grid.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-reboot.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
</head>



<body>

<jsp:include page="../components/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="content" />

<div class="container my-lg-4">
    <div class="row justify-content-start">
        <div class="col-3">
            <p class="h2"><fmt:message key="content.request.process.welcome" /></p>
        </div>
        <div class="col-3">
            <form method="post" action="${pageContext.request.contextPath}/api/considerRequest">
                <input type="hidden" name="requestId" value="${requestScope.request.id}">
                <button class="btn btn-lg btn-primary btn-block" type="submit" ><fmt:message key="content.request.process.consider" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/openAccount" >
                <input type="hidden" name="requestId" value="${requestScope.request.id}">
                <div class="form-group">
                    <p class="font-weight-bold"><fmt:message key="content.request.process.type" /></p>
                    <input type="hidden" name="requestType" value="${requestScope.request.type}">
                    <input class="form-control" type="text" placeholder="${requestScope.type}" readonly>
                </div>
                <div class="form-group">
                    <p class="font-weight-bold"><fmt:message key="content.request.process.requester" /></p>
                    <input class="form-control" type="number" name="requester" value="${requestScope.request.requesterId}" placeholder="${requestScope.request.requesterId}" readonly>
                </div>
                <div class="form-group">
                    <p class="font-weight-bold"><fmt:message key="content.request.process.currency" /></p>
                    <input class="form-control" type="text" name="currency" value="${requestScope.request.currency}" placeholder="${requestScope.request.currency}" readonly>
                </div>
                <div class="form-group">
                    <p class="font-weight-bold"><fmt:message key="content.request.process.init" /></p>
                    <input type="number" class="form-control" step="0.01" name="initDeposit" value="${param.initDeposit}" placeholder="<fmt:message key="content.request.process.init" />" required>
                    <p class="text-danger">${requestScope.initDepositWrong}</p>
                </div>
                <div class="form-group">
                    <p class="font-weight-bold"><fmt:message key="content.request.process.expires.end" /></p>
                    <input type="date" class="form-control" name="expiresEnd" value="${empty param.expiresEnd ? requestScope.now : param.expiresEnd}"  required>
                    <p class="text-danger">${requestScope.expiresEndWrong}</p>
                    <p class="text-danger">${requestScope.isBeforeNow}</p>
                </div>
                <c:choose>
                    <c:when test="${requestScope.request.type eq 'CREATE_DEPOSIT_ACCOUNT'}">
                        <div class="form-group">
                            <p class="font-weight-bold"><fmt:message key="content.request.process.update.period" /></p>
                            <input type="number" step="1" class="form-control" name="updatePeriod" value="${param.updatePeriod}" placeholder="<fmt:message key="content.request.process.update.period" />" required>
                            <p class="text-danger">${requestScope.updatePeriodWrong}</p>
                        </div>
                        <div class="form-group">
                            <p class="font-weight-bold"><fmt:message key="content.request.process.deposit.rate" /></p>
                            <input type="number" step="0.0001" class="form-control" name="depositRate" value="${param.depositRate}" placeholder="<fmt:message key="content.request.process.deposit.rate" />" required>
                            <p class="text-danger">${requestScope.depositRateWrong}</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group">
                            <p class="font-weight-bold"><fmt:message key="content.request.process.credit.limit" /></p>
                            <input type="number" class="form-control" name="creditLimit" value="${param.creditLimit}" placeholder="<fmt:message key="content.request.process.credit.limit" />" required>
                            <p class="text-danger">${requestScope.creditLimitWrong}</p>
                        </div>
                        <div class="form-group">
                            <p class="font-weight-bold"><fmt:message key="content.request.process.credit.rate" /></p>
                            <input type="number" step="0.0001" class="form-control" name="creditRate" value="${param.creditRate}" placeholder="<fmt:message key="content.request.process.credit.rate" />" required>
                            <p class="text-danger">${requestScope.creditRateWrong}</p>
                        </div>
                    </c:otherwise>
                </c:choose>
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" ><fmt:message key="content.request.process.submit" /></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
