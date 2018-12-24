<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Invoice</title>

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
                <fmt:message key="content.info.invoice.welcome" />
            </div>
        </div>
        <div class="col-2">
            <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.invoice.account" /></button>
            </form>
        </div>
        <div class="col-2">
            <form method="post" action="${pageContext.request.contextPath}/api/showInvoices">
                <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.invoice.all.invoices" /></button>
            </form>
        </div>
        <c:if test="${requestScope.invoice.status eq 'PROCESSING'}" >
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/completeInvoice">
                    <input type="hidden" name="masterAccount" value="${requestScope.masterAccount}">
                    <input type="hidden" name="invoiceId" value="${requestScope.invoice.id}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.invoice.accept" /></button>
                </form>
            </div>
            <div class="col-2">
                <form method="post" action="${pageContext.request.contextPath}/api/denyInvoice">
                    <input type="hidden" name="masterAccount" value="${requestScope.masterAccount}">
                    <input type="hidden" name="invoiceId" value="${requestScope.invoice.id}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.invoice.deny" /></button>
                </form>
            </div>
        </c:if>
    </div>
    <c:if test="${not empty requestScope.notEnough}">
        <div class="row justify-content-start my-2">
            <div class="col-10">
                <h3 class="text-danger"><c:out value="${requestScope.notEnough}" /></h3>
            </div>
        </div>
    </c:if>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.requester" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.invoice.requester}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.payer" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.invoice.payer}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.amount" /></p>
        </div>
        <div class="col-4">
            <p class="text-left"><ctg:balance balance="${requestScope.invoice.amount}" currency="${requestScope.invoice.currency}" /></p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.currency" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.invoice.currency}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.description" /></p>
        </div>
        <div class="col-4">
            <p class="text-left">${requestScope.invoice.description}</p>
        </div>
    </div>
    <div class="row justify-content-start my-3">
        <div class="col-4">
            <p class="text-right text-primary text"><fmt:message key="content.info.invoice.status" /></p>
        </div>
        <div class="col-4">
            <c:choose>
                <c:when test="${requestScope.invoice.status eq 'ACCEPTED'}" >
                    <p class="text-info"><fmt:message key="content.info.invoice.status.accepted" /></p>
                </c:when>
                <c:when test="${requestScope.invoice.status eq 'DENIED'}">
                    <p class="text-danger"><fmt:message key="content.info.invoice.status.denied" /></p>
                </c:when>
                <c:otherwise>
                    <p class="text-warning"><fmt:message key="content.info.invoice.status.processing" /></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${requestScope.invoice.status eq 'ACCEPTED'}">
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary text"><fmt:message key="content.info.invoice.transaction" /></p>
            </div>
            <div class="col-3">
                <form method="post" action="${pageContext.request.contextPath}/api/infoTransaction">
                    <input type="hidden" name="transactionId" value="${requestScope.invoice.transaction}" />
                    <button class="btn btn-lg btn-primary btn-block" type="submit">${requestScope.invoice.transaction}</button>
                </form>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
