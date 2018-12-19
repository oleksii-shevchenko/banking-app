<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Sign In</title>

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
            <form class="form-signup" action="${pageContext.request.contextPath}/api/signUp" method="post">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="content.sign.up.welcome" /></h1>
                <div class="form-group my-2">
                    <input type="text" name="login" class="form-control my-2" value="${param.login}" placeholder="<fmt:message key="content.sign.up.login"/>" required autofocus>
                    <p class="text-danger">${requestScope.wronglogin}</p>
                </div>
                <div class="form-group my-2">
                    <input type="password" name="pass" class="form-control my-2" value="${param.pass}" placeholder="<fmt:message key="content.sign.up.password" />" required>
                    <p class="text-danger">${requestScope.wrongpass}</p>
                </div>
                <div class="form-group my-2">
                    <input type="email" name="email" class="form-control my-2" value="${param.email}" placeholder="<fmt:message key="content.sign.up.email" />" required>
                    <p class="text-danger">${requestScope.wrongemail}</p>
                </div>
                <div class="form-group my-2">
                    <input type=text name="firstname" class="form-control my-2" value="${param.firstname}" placeholder="<fmt:message key="content.sign.up.first.name" />" required>
                    <p class="text-danger">${requestScope.wrongfirstname}</p>
                </div>
                <div class="form-group my-2">
                    <input type="text" name="secondname" class="form-control my-2" value="${param.secondname}" placeholder="<fmt:message key="content.sign.up.second.name" />" required>
                    <p class="text-danger">${requestScope.wrongsecondname}</p>
                </div>
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" ><fmt:message key="content.sign.up.submit"/></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
