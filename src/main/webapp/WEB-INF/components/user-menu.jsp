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
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.user.info.profile" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/request">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.user.request" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/showAccounts">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.user.accounts" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/makeTransaction">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.user.make.transaction" /></button>
            </form>
        </div>
    </div>
    <div class="row justify-content-center my-3">
        <div class="col-5">
            <form method="post" action="${pageContext.request.contextPath}/api/createInvoice">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.menu.user.create.invoice" /></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>