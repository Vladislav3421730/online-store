<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="registration.title" bundle="${lang}" /></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center"><fmt:message key="registration.heading" bundle="${lang}" /></h5>
                    <p style="color: red">${requestScope.error}</p>
                    <form action="${pageContext.request.contextPath}/registration" method="post">
                        <div class="form-group mb-2">
                            <label for="username"><fmt:message key="registration.username" bundle="${lang}" /></label>
                            <input type="text" value="${requestScope.username}" class="form-control mt-1" minlength="5" name="username" id="username"
                                   placeholder="<fmt:message key='registration.usernamePlaceholder' bundle='${lang}' />" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="email"><fmt:message key="registration.email" bundle="${lang}" /></label>
                            <input type="email" value="${requestScope.email}" class="form-control mt-1" name="email" id="email"
                                   placeholder="<fmt:message key='registration.emailPlaceholder' bundle='${lang}' />" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="phone"><fmt:message key="registration.phone" bundle="${lang}" /></label>
                            <input type="text" value="${requestScope.phone}" class="form-control mt-1" name="phone" id="phone"
                                   placeholder="<fmt:message key='registration.phonePlaceholder' bundle='${lang}' />" pattern="\+375[0-9]{9}">
                        </div>
                        <div class="form-group mb-2">
                            <label for="register-password"><fmt:message key="registration.password" bundle="${lang}" /></label>
                            <input type="password" value="${requestScope.password}" class="form-control mt-1" minlength="6" name="password"
                                   id="register-password" placeholder="<fmt:message key='registration.passwordPlaceholder' bundle='${lang}' />" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="confirmPassword"><fmt:message key="registration.confirmPassword" bundle="${lang}" /></label>
                            <input type="password" class="form-control mt-1" minlength="6" name="confirmPassword"
                                   id="confirmPassword" placeholder="<fmt:message key='registration.confirmPasswordPlaceholder' bundle='${lang}' />" required>
                            <p style="color: red" id="passwordNotTheSame"></p>
                        </div>
                        <button id="register-button" type="submit" class="btn btn-primary btn-block mt-2">
                            <fmt:message key="registration.submit" bundle="${lang}" />
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/modalSelectValue.js"%>
</script>
</html>
