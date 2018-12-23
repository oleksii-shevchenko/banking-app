<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Account</title>

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
        <div class="col-2">
            <div class="h2">
                <fmt:message key="content.info.account.welcome" />
            </div>
        </div>
        <c:if test="${sessionScope.role eq 'USER'}" >
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/showAccounts">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.account.show" /></button>
                </form>
            </div>
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/showTransactions">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.account.transactions" /></button>
                </form>
            </div>
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/showInvoices">
                    <input type="hidden" name="id" value="${requestScope.account.id}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.account.invoices" /></button>
                </form>
            </div>
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/showHolders">
                    <input type="hidden" name="id" value="${requestScope.account.id}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.account.holders" /></button>
                </form>
            </div>
        </c:if>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.account.id" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.account.id}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.account.balance" /></p>
        </div>
        <div class="col-4">
            <p class="text-left"><ctg:balance balance="${requestScope.account.balance}" currency="${requestScope.account.currency}" /></p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.account.currency" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.account.currency}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.account.expires.end" /></p>
        </div>
        <div class="col-4">
            <p class="text-left"><ctg:date localeTag="${sessionScope.lang}" date="${requestScope.account.expiresEnd}" /></p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.account.status" /></p>
        </div>
        <div class="col-4 text-left">
            <ctg:status account="${requestScope.account}" localeTag="${sessionScope.lang}" />
        </div>
    </div>
    <c:choose>
        <c:when test="${requestScope.type eq 'DepositAccount'}">
            <div class="row justify-content-start my-3">
                <div class="col-4">
                    <p class="text-right text-primary text"><fmt:message key="content.info.account.deposit.rate" /></p>
                </div>
                <div class="col-4">
                    <p class="text-left">${requestScope.account.depositRate}</p>
                </div>
            </div>
            <div class="row justify-content-start my-3">
                <div class="col-4">
                    <p class="text-right text-primary text"><fmt:message key="content.info.account.deposit.update" /></p>
                </div>
                <div class="col-4">
                    <p class="text-left">${requestScope.account.updatePeriod}</p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row justify-content-start my-3">
                <div class="col-4">
                    <p class="text-right text-primary text"><fmt:message key="content.info.account.credit.limit" /></p>
                </div>
                <div class="col-4">
                    <p class="text-left"><fmt:formatNumber value="${requestScope.account.creditLimit}" maxFractionDigits="2" /></p>
                </div>
            </div>
            <div class="row justify-content-start my-3">
                <div class="col-4">
                    <p class="text-right text-primary text"><fmt:message key="content.info.account.credit.rate" /></p>
                </div>
                <div class="col-4">
                    <p class="text-left">${requestScope.account.creditRate}</p>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <c:if test="${sessionScope.role eq 'USER'}">
        <div class="row justify-content-start my-2">
            <p class="h2"><fmt:message key="content.info.account.transactions.welcome" /></p>
        </div>

        <form class="form-row justify-content-center" action="${pageContext.request.contextPath}/api/makeTransaction" method="post">
            <div class="form-group my-2 mx-2 input-group-lg">
                <select class="form-control my-2" name="sender" required>
                    <c:forEach var="account" items="${requestScope.accountIds}">
                        <option value="${account}" ${requestScope.account.id eq account ? 'selected' : ''}>${account}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group my-2 mx-2 input-group-lg">
                <input type="number" name="receiver" class="form-control my-2" placeholder="<fmt:message key="content.info.account.transaction.receiver" />" required>
            </div>
            <div class="form-group my-2 mx-2 input-group-lg">
                <input type="number" step="0.01" name="amount" class="form-control my-2" placeholder="<fmt:message key="content.info.account.transaction.amount" />" required>
            </div>
            <div class="form-group my-2 mx-2 input-group-lg">
                <select class="form-control my-2" name="currency" required>
                    <c:forEach var="currency" items="${requestScope.currencies}">
                        <option value="${currency}" ${requestScope.account.currency eq currency ? 'selected' : ''}>${currency}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group my-2 mx-2">
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" ><fmt:message key="content.info.account.transaction.make" /></button>
            </div>
        </form>

        <div class="row justify-content-start my-2">
            <p class="h2"><fmt:message key="content.info.account.invoice.welcome" /></p>
        </div>

        <form class="form-row justify-content-center" action="${pageContext.request.contextPath}/api/createInvoice" method="post">
            <div class="form-group input-group-lg my-2 mx-1">
                <select class="form-control my-2" name="requester" required>
                    <c:forEach var="account" items="${requestScope.accountIds}">
                        <option value="${account}" ${requestScope.account.id eq account ? 'selected' : ''}>${account}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group input-group-lg my-2 mx-1">
                <input type="number" name="payer" class="form-control my-2" placeholder="<fmt:message key="content.info.account.invoice.payer" />" required>
            </div>
            <div class="form-group input-group-lg my-2 mx-1">
                <input type="number" step="0.01" name="amount" class="form-control my-2" value="${param.amount}" placeholder="<fmt:message key="content.info.account.invoice.amount" />" required>
            </div>
            <div class="form-group input-group-lg my-2 mx-1">
                <select class="form-control my-2" name="currency" required>
                    <c:forEach var="currency" items="${requestScope.currencies}">
                        <option value="${currency}" ${requestScope.account.currency eq currency ? 'selected' : ''}>${currency}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group input-group-lg my-2 mx-1">
                <textarea class="form-control my-2" name="description" rows="1" placeholder="<fmt:message key="content.info.account.invoice.description" />"></textarea>
            </div>
            <div class="form-group my-2 mx-1">
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" ><fmt:message key="content.info.account.invoice.create" /></button>
            </div>
        </form>
    </c:if>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
