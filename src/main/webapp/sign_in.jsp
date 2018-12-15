<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Sign In</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/bootstrap-grid.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/bootstrap-reboot.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/custom.css">

    <script src="${pageContext.request.contextPath}js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}js/bootstrap.bundle.js"></script>
</head>



<body>

<jsp:include page="WEB-INF/includes/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="content" />

<div class="container my-lg-4">
    <div class="row justify-content-center">
        <div class="col-4">
            <form class="form-signin">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="content.sign.in.welcome" /></h1>
                <label for="inputLogin" class="sr-only"><fmt:message key="content.sign.in.login"/></label>
                <input type="text" id="inputLogin" class="form-control" placeholder="User login" required autofocus>
                <label for="inputPassword" class="sr-only"><fmt:message key="content.sign.in.password" /></label>
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.sign.in.submit" /></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="WEB-INF/includes/footer.jsp" />

</body>
</html>
