<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<body>
    <fmt:setLocale value="${sessionScope.lang}" scope="session" />
    <fmt:setBundle basename="content"/>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><fmt:message key="content.navbar.logo" /></a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="content.navbar.home" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/api/currencyRates"><fmt:message key="content.navbar.rates" /></a>
                </li>
                <c:if test="${sessionScope.role != null && sessionScope.role == 'GUEST'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/api/workspace"><fmt:message key="content.navbar.workspace" /></a>
                    </li>
                </c:if>
            </ul>
            <ul class="navbar-nav navbar-right mt-3">
                <c:choose>
                    <c:when test="${sessionScope.role == 'GUEST'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/sign_in.jsp"><fmt:message key="content.navbar.sign.in" /></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/sign_up.jsp"><fmt:message key="content.navbar.sign.up" /></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/api/signOut"><fmt:message key="content.navbar.sign.out" /></a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/api/changeLanguage">
                        <select class="form-control" name="lang" onchange="submit()">
                            <option value="en_US" ${sessionScope.lang eq 'en_US' ? 'selected' : ''}>EN</option>
                            <option value="ua_UA" ${sessionScope.lang eq 'ua_UA' ? 'selected' : ''}>UA</option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</body>
</html>
