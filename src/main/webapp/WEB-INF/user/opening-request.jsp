<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Opening Request</title>

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
        <p class="h2"><fmt:message key="content.request.welcome" /></p>
    </div>
    <div class="row justify-content-center">
        <div class="col-4">
            <form action="${pageContext.request.contextPath}/api/request" method="post">
                <div class="form-group my-2">
                    <select name="account" class="form-control">
                        <option value="CREATE_DEPOSIT_ACCOUNT"><fmt:message key="content.request.deposit" /></option>
                        <option value="CREATE_CREDIT_ACCOUNT"><fmt:message key="content.request.credit" /></option>
                    </select>
                </div>
                <div class="form-group my-2">
                    <select class="form-control" name="currency">
                        <option value="EUR">EUR</option>
                        <option value="USD">USD</option>
                        <option value="UAH">UAH</option>
                    </select>
                </div>
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" ><fmt:message key="content.request.make"/></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
