<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Sign Up</title>

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
    <div class="row justify-content-center">
        <div class="col-4">
            <form class="form-signin" action="${pageContext.request.contextPath}/api/signIn" method="post">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="content.sign.in.welcome" /></h1>
                <div class="form-group">
                    <input type="text" name="login" class="form-control my-2 ${not empty requestScope.loginWrong ? 'is-invalid' : ''}" value="${param.login}" placeholder="<fmt:message key="content.sign.in.login"/>" required autofocus>
                    <p class="text-danger">${requestScope.loginWrong}</p>
                </div>
                <div class="form-group">
                    <input type="password" name="pass" class="form-control my-2 ${not empty requestScope.passWrong ? 'is-invalid' : ''}" value="${param.pass}" placeholder="<fmt:message key="content.sign.in.password" />" required>
                    <p class="text-danger">${requestScope.passWrong}</p>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.sign.in.submit" /></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
