<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Opening Request</title>

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
    <div class="row justify-content-start my-3">
        <div class="col-2">
            <div class="h2">
                <fmt:message key="content.holders.welcome" />
            </div>
        </div>
        <div class="col-2">
            <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                <input type="hidden" name="id" value="${requestScope.masterAccount}">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.holders.account" /></button>
            </form>
        </div>
        <c:if test="${requestScope.permission eq 'RESTRICTED'}">
            <div class="col-3">
                <form method="post" action="${pageContext.request.contextPath}/api/removeHolder">
                    <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.holders.remove.yourself" /></button>
                </form>
            </div>
        </c:if>
        <div class="col-5">
            <form method="post" class="form-inline" action="${pageContext.request.contextPath}/api/addHolder">
                <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
                <div class="form-group input-group-lg">
                    <input type="number" class="form-control" name="holderId" placeholder="<fmt:message key="content.holders.add.placeholder" />" required>
                </div>
                <div class="form-group mx-2">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.holders.add" /></button>
                </div>
            </form>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.holders.id" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.holders.login" /></p>
        </div>
        <div class="col-3">
            <p class="font-weight-bold"><fmt:message key="content.holders.permission" /></p>
        </div>
        <c:if test="${requestScope.permission eq 'ALL'}">
            <div class="col-3">
                <p class="font-weight-bold"><fmt:message key="content.holders.remove" /></p>
            </div>
        </c:if>
    </div>
    <c:forEach var="holders" items="${requestScope.holders}">
        <div class="row justify-content-center">
            <div class="col-3">
                <p >${holders.key.id}</p>
            </div>
            <div class="col-3">
                <p>${holders.key.login}</p>
            </div>
            <div class="col-3">
                <c:choose>
                    <c:when test="${holders.value eq 'ALL'}">
                        <p><fmt:message key="content.holders.owner" /></p>
                    </c:when>
                    <c:otherwise>
                        <p><fmt:message key="content.holders.user" /></p>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:if test="${requestScope.permission eq 'ALL'}">
                <div class="col-3">
                    <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                        <input type="hidden" name="accountId" value="${requestScope.masterAccount}">
                        <input type="hidden" name="holderId" value="${holders.key.id}" />
                        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.holders.remove" /></button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>