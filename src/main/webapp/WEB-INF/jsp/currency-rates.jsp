<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Currencies rates</title>

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

<div class="container">
    <div class="row justify-content-center my-5">
        <div class="col-2">
            <form method="post" action="${pageContext.request.contextPath}/api/currencyRate">
                <h5 class="text-center"><fmt:message key="content.rates.base" /></h5>
                <select class="form-control" name="base" onchange="submit()">
                    <option value="EUR" ${requestScope.base eq 'EUR' ? 'selected' : ''}>EUR</option>
                    <option value="USD" ${requestScope.base eq 'USD' ? 'selected' : ''}>USD</option>
                    <option value="UAH" ${requestScope.base eq 'UAH' ? 'selected' : ''}>UAH</option>
                </select>
            </form>
        </div>
        <div class="col-6">
            <table class="table table-bordered">
                <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="content.rates.target" /></th>
                    <th scope="col"><fmt:message key="content.rates.rate" /></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="elem" items="${requestScope.rates}">
                        <tr>
                            <td>${elem.key}</td>
                            <td><fmt:formatNumber value="${elem.value}" maxFractionDigits="4" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />
</body>
</html>
