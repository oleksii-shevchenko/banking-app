<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Invoices</title>

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

<div class="container my-lg-4">
    <div class="row justify-content-start">
        <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
            <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.invoice.account" /></button>
        </form>
    </div>
    <div class="row justify-content-start my-2">
        <p class="h2"><fmt:message key="content.all.invoices.welcome.payer" /></p>
    </div>
    <c:choose>
        <c:when test="${not empty requestScope.payer}">
            <div class="row justify-content-center">
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.id" /></p>
                </div>
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.requester" /></p>
                </div>
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.amount" /></p>
                </div>
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.status" /></p>
                </div>
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.info" /></p>
                </div>
            </div>
            <c:forEach var="invoices" items="${requestScope.payer}">
                <div class="row justify-content-center">
                    <div class="col-2">
                        <p>${invoices.id}</p>
                    </div>
                    <div class="col-2">
                        <p>${invoices.requester}</p>
                    </div>
                    <div class="col-3">
                        <p><ctg:balance balance="${invoices.amount}" currency="${invoices.currency}" /></p>
                    </div>
                    <div class="col-3">
                        <c:choose>
                            <c:when test="${invoices.status eq 'ACCEPTED'}" >
                                <p class="text-info"><fmt:message key="content.all.invoices.accepted" /></p>
                            </c:when>
                            <c:when test="${invoices.status eq 'DENIED'}">
                                <p class="text-danger"><fmt:message key="content.all.invoices.denied" /></p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-warning"><fmt:message key="content.all.invoices.processing" /></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-2">
                        <form method="post" action="${pageContext.request.contextPath}/api/infoInvoice">
                            <input type="hidden" name="masterAccount" value="${requestScope.masterAccount}">
                            <input type="hidden" name="invoiceId" value="${invoices.id}" />
                            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.all.invoices.info" /></button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="row justify-content-center my-2">
                <div class="h2 text-muted"><fmt:message key="content.all.invoices.empty" /></div>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="row justify-content-start my-2">
        <p class="h2"><fmt:message key="content.all.invoices.welcome.requester" /></p>
    </div>
    <c:choose>
        <c:when test="${not empty requestScope.requester}">
            <div class="row justify-content-center">
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.id" /></p>
                </div>
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.payer" /></p>
                </div>
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.amount" /></p>
                </div>
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.status" /></p>
                </div>
                <div class="col-2">
                    <p class="font-weight-bold"><fmt:message key="content.all.invoices.info" /></p>
                </div>
            </div>
            <c:forEach var="invoices" items="${requestScope.requester}">
                <div class="row justify-content-center">
                    <div class="col-2">
                        <p>${invoices.id}</p>
                    </div>
                    <div class="col-2">
                        <p>${invoices.payer}</p>
                    </div>
                    <div class="col-3">
                        <p><ctg:balance balance="${invoices.amount}" currency="${invoices.currency}" /></p>
                    </div>
                    <div class="col-3">
                        <c:choose>
                            <c:when test="${invoices.status eq 'ACCEPTED'}" >
                                <p class="text-info"><fmt:message key="content.all.invoices.accepted" /></p>
                            </c:when>
                            <c:when test="${invoices.status eq 'DENIED'}">
                                <p class="text-danger"><fmt:message key="content.all.invoices.denied" /></p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-warning"><fmt:message key="content.all.invoices.processing" /></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-2">
                        <form method="post" action="${pageContext.request.contextPath}/api/infoInvoice">
                            <input type="hidden" name="masterAccount" value="${requestScope.masterAccount}">
                            <input type="hidden" name="invoiceId" value="${invoices.id}" />
                            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.all.invoices.info" /></button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="row justify-content-center my-2">
                <div class="h2 text-muted"><fmt:message key="content.all.invoices.empty" /></div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>