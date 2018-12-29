<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>User info</title>

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
                <fmt:message key="content.info.user.welcome" />
            </div>
        </div>
        <div class="col-6">
            <form method="post" class="form-inline" action="${pageContext.request.contextPath}/api/infoUser">
                <div class="form-group input-group-lg">
                    <input type="number" class="form-control" value="${requestScope.user.id}" name="userId" placeholder="<fmt:message key="content.info.user.form.placeholder" />" required>
                </div>
                <div class="form-group mx-2">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.user.form.submit" /></button>
                </div>
            </form>
        </div>
    </div>
    <c:if test="${not empty requestScope.user}" >
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary text"><fmt:message key="content.info.user.id" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.id}</p>
            </div>
        </div>
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary text"><fmt:message key="content.info.user.login" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.login}</p>
            </div>
        </div>
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary"><fmt:message key="content.info.user.email" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.email}</p>
            </div>
        </div>
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary"><fmt:message key="content.info.user.role" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.role}</p>
            </div>
        </div>
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary"><fmt:message key="content.info.user.first.name" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.firstName}</p>
            </div>
        </div>
        <div class="row justify-content-start my-3">
            <div class="col-4">
                <p class="text-right text-primary"><fmt:message key="content.info.user.second.name" /></p>
            </div>
            <div class="col-4">
                <p class="text-left">${requestScope.user.secondName}</p>
            </div>
        </div>
        <c:if test="${not empty requestScope.user.accounts}">
            <div class="row justify-content-start">
                <div class="h3"><fmt:message key="content.info.user.accounts" /></div>
            </div>
            <div class="row justify-content-center my-2">
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.info.user.accounts.id" /></p>
                </div>
                <div class="col-3">
                    <p class="font-weight-bold"><fmt:message key="content.info.user.accounts.info" /></p>
                </div>
            </div>
            <c:forEach var="account" items="${requestScope.user.accounts}">
                <div class="row justify-content-center">
                    <div class="col-3">
                        ${account}
                    </div>
                    <div class="col-3">
                        <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                            <input type="hidden" name="accountId" value="${account}" />
                            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.info.user.accounts.info" /></button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </c:if>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
