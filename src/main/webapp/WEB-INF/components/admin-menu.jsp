<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<body>
<fmt:setLocale value="${sessionScope.lang}" scope="session" />
<fmt:setBundle basename="content"/>

<div class="container my-2">
    <div class="row justify-content-center my-3">
        <div class="col-7">
            <form method="post" action="${pageContext.request.contextPath}/api/profile">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.profile" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-7">
            <form method="post" action="${pageContext.request.contextPath}/api/showRequests">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.show.requests" /></button>
            </form>
        </div>
    </div>
    <form method="post" class="form-row justify-content-center my-3" action="${pageContext.request.contextPath}/api/infoUser">
        <div class="col-3 input-group-lg">
            <input type="number" class="form-control" name="userId" placeholder="<fmt:message key="content.menu.admin.placeholder.user" />" required>
        </div>
        <div class="col-4">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.user" /></button>
        </div>
    </form>
    <form method="post" class="form-row justify-content-center my-3" action="${pageContext.request.contextPath}/api/infoAccount">
        <div class="col-3 input-group-lg">
            <input type="number" class="form-control" name="accountId" placeholder="<fmt:message key="content.menu.admin.placeholder.account" />" required>
        </div>
        <div class="col-4">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.account" /></button>
        </div>
    </form>
    <form method="post" class="form-row justify-content-center my-3" action="${pageContext.request.contextPath}/api/infoTransaction">
        <div class="col-3 input-group-lg">
            <input type="number" class="form-control" name="transactionId" placeholder="<fmt:message key="content.menu.admin.placeholder.transaction" />" required>
        </div>
        <div class="col-4">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.admin.info.transaction" /></button>
        </div>
    </form>
</div>
</body>
</html>
