<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Banking App</title>

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

<jsp:include page="WEB-INF/components/navbar.jsp" />

<div class="features-boxed">
    <div class="container">
        <div class="row justify-content-center features">
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <i class="material-icons md-48">euro_symbol</i>
                    <h3 class="name"><fmt:message key="content.index.currency.head" /></h3>
                    <p class="description"><fmt:message key="content.index.currency.text" /></p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <i class="material-icons md-48">local_atm</i>
                    <h3 class="name"><fmt:message key="content.index.account.head" /></h3>
                    <p class="description"><fmt:message key="content.index.account.text" /></p>
                </div>
            </div>
        </div>
        <div class="row justify-content-center features">
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <i class="material-icons md-48">shop</i>
                    <h3 class="name"><fmt:message key="content.index.invoice.head" /></h3>
                    <p class="description"><fmt:message key="content.index.invoice.text" /></p>
                </div>
            </div>
            <div class="col-sm-6 col-md-5 col-lg-4 item">
                <div class="box">
                    <i class="material-icons md-48">autorenew</i>
                    <h3 class="name"><fmt:message key="content.index.update.head" /></h3>
                    <p class="description"><fmt:message key="content.index.update.text" /></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="WEB-INF/components/footer.jsp" />
</body>
</html>
