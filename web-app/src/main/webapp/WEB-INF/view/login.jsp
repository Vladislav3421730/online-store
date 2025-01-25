<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="page.login.title" bundle="${lang}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4 w-100">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card" style="width: 18rem;">
                <div class="card-header"><fmt:message key="login.header" bundle="${lang}"/></div>
                <div class="card-body">
                    <c:if test="${param.error != null}">
                        <p style="color: red"><fmt:message key="login.error.invalid_credentials"
                                                           bundle="${lang}"/></p>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="login" class="form-label"><fmt:message key="login.email"
                                                                               bundle="${lang}"/></label>
                            <input type="email" value="${requestScope.email}" class="form-control" id="login"
                                   name="username">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label"><fmt:message key="login.password"
                                                                                  bundle="${lang}"/></label>
                            <input type="password" value="${requestScope.password}" class="form-control" id="password"
                                   name="password">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button type="submit" class="btn btn-primary"><fmt:message key="login.submit"
                                                                                   bundle="${lang}"/></button>
                    </form>
                </div>
            </div>
            <div id="add-info">
                <h5 class="mt-4"><fmt:message key="login.noAccount" bundle="${lang}"/></h5>
                <a href="${pageContext.request.contextPath}/registration" class="btn btn-primary"><fmt:message
                        key="login.register" bundle="${lang}"/></a>
                <h4 id="login-result"></h4>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
