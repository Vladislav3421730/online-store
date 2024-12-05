<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">Регистрация</h5>
                    <p style="color: red">${requestScope.error}</p>
                    <form action="${pageContext.request.contextPath}/registration" method="post">
                        <div class="form-group mb-2">
                            <label for="username">Логин</label>
                            <input type="text" value="${requestScope.username}" class="form-control mt-1" minlength="5" name="username" id="username"
                                   placeholder="Введите логин" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="email">Электронная почта</label>
                            <input type="email" value="${requestScope.email}" class="form-control mt-1" name="email" id="email"
                                   placeholder="Введите адрес электронной почты" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="phone">Номер телефона</label>
                            <input type="text" value="${requestScope.email}" class="form-control mt-1" name="phone" id="phone"
                                   placeholder="Номер телефона (+375XXXXXXXXX)" pattern="\+375[0-9]{9}">
                        </div>
                        <div class="form-group mb-2">
                            <label for="register-password">Пароль</label>
                            <input type="password" value="${requestScope.password}" class="form-control mt-1" minlength="6" name="password"
                                   id="register-password" placeholder="Введите пароль" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="confirmPassword">Подтвердите пароль</label>
                            <input type="password" class="form-control mt-1" minlength="6" name="confirmPassword"
                                   id="confirmPassword" placeholder="Подтвердите пароль" required>
                            <p style="color: red" id="passwordNotTheSame"></p>
                        </div>
                        <button id="register-button" type="submit" class="btn btn-primary btn-block mt-2">
                            Зарегистрироваться
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
