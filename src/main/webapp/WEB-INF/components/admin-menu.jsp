<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<body>
<fmt:setLocale value="${sessionScope.lang}" scope="session" />
<fmt:setBundle basename="content"/>

<div class="container my-2">
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/profile">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.profile" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/infoUser">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.user" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/infoAccount">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.account" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/infoTransaction">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.transaction" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/showRequests">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.show.requests" /></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
