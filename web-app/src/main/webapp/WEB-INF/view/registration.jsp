<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="registration.title" bundle="${lang}"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center"><fmt:message key="registration.heading" bundle="${lang}"/></h5>
                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger">
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${error!=null}">
                        <div class="alert alert-danger">
                            <ul>
                                <fmt:message key="${error}" bundle="${lang}"/>
                            </ul>
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/registration" method="post">
                        <div class="form-group mb-2">
                            <label for="username"><fmt:message key="registration.username" bundle="${lang}"/></label>
                            <input type="text" value="${registerUserDto.username}" minlength="5"
                                   class="form-control mt-1" name="username" id="username"
                                   placeholder="<fmt:message key='registration.usernamePlaceholder' bundle='${lang}' />"
                                   required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="email"><fmt:message key="registration.email" bundle="${lang}"/></label>
                            <input type="email" value="${registerUserDto.email}" class="form-control mt-1" name="email"
                                   id="email"
                                   placeholder="<fmt:message key='registration.emailPlaceholder' bundle='${lang}' />"
                                   required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="phone"><fmt:message key="registration.phone" bundle="${lang}"/></label>
                            <input type="text" value="${registerUserDto.phoneNumber}" class="form-control mt-1"
                                   name="phoneNumber" id="phone"
                                   placeholder="<fmt:message key='registration.phonePlaceholder' bundle='${lang}' />"
                                   pattern="\+375[0-9]{9}">
                        </div>
                        <div class="form-group mb-2">
                            <label for="register-password"><fmt:message key="registration.password"
                                                                        bundle="${lang}"/></label>
                            <input type="password" value="${registerUserDto.password}" class="form-control mt-1"
                                   minlength="6" name="password"
                                   id="register-password"
                                   placeholder="<fmt:message key='registration.passwordPlaceholder' bundle='${lang}' />"
                                   required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="confirmPassword"><fmt:message key="registration.confirmPassword"
                                                                      bundle="${lang}"/></label>
                            <input type="password" class="form-control mt-1" minlength="6" name="confirmPassword"
                                   id="confirmPassword"
                                   placeholder="<fmt:message key='registration.confirmPasswordPlaceholder' bundle='${lang}' />"
                                   required>
                            <p style="color: red" id="passwordNotTheSame"></p>
                        </div>
                        <button id="register-button" type="submit" class="btn btn-primary btn-block mt-2">
                            <fmt:message key="registration.submit" bundle="${lang}"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    const password = document.getElementById("register-password")
    const passwordConfirm = document.getElementById("confirmPassword")
    const message = document.getElementById("passwordNotTheSame")
    const submitButton = document.getElementById("register-button")
    passwordConfirm.addEventListener('change', function () {

        if (password.value !== passwordConfirm.value) {
            message.textContent = '<fmt:message key='registration.password.same' bundle='${lang}' />'
            submitButton.setAttribute('disabled', '')
        } else {
            message.textContent = ''
            submitButton.removeAttribute('disabled')
        }
    })
    <%@include file="/WEB-INF/script/modalSelectValue.js"%>
</script>
</html>
