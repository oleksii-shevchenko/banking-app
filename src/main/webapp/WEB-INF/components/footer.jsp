<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<body>
<fmt:setLocale value="${sessionScope.lang}" scope="session" />
<fmt:setBundle basename="content"/>
<footer class="footer">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-3">
                <span class="text-muted">&copy;2018-2019 <fmt:message key="content.footer.author" /></span>
            </div>
        </div>
    </div>
</footer>
</body>
</html>