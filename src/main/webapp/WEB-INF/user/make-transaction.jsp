<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom" %>


<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Make transaction</title>

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
            <form action="${pageContext.request.contextPath}/api/makeTransaction" method="post">
                <h1 class="h3 mb-3 font-weight-normal">Please, make transaction</h1>
                <div class="form-group my-2">
                    <p class="font-weight-bold">Sending account</p>
                    <select class="form-control" name="sender" required>
                        <c:forEach var="account" items="${requestScope.accountIds}">
                            <option value="${account}" ${param.sender eq account ? 'selected' : ''}>${account}</option>
                        </c:forEach>
                    </select>
                    <p class="text-danger">${requestScope.notEnough}</p>
                </div>
                <div class="form-group my-2">
                    <p class="font-weight-bold">Receiving account</p>
                    <input type="number" name="receiver" class="form-control my-2" value="${param.receiver}" placeholder="Receiver" required>
                </div>
                <div class="form-group my-2">
                    <p class="font-weight-bold">Amount</p>
                    <input type="number" step="0.01" name="amount" class="form-control my-2" value="${param.amount}" placeholder="Amount" required>
                    <p class="text-danger">${requestScope.amountWrong}</p>
                </div>
                <div class="form-group my-2">
                    <p class="font-weight-bold">Currency</p>
                    <select class="form-control" name="currency" required>
                        <c:forEach var="currency" items="${requestScope.currencies}">
                            <option value="${currency}" ${param.currency eq currency ? 'selected' : ''}>${currency}</option>
                        </c:forEach>
                    </select>
                </div>
                <button class="btn btn-lg btn-primary btn-block my-2" type="submit" >Make transaction</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
