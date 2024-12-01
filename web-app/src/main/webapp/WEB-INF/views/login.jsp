<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4 w-100">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card" style="width: 18rem;">
                <div class="card-header ">Вход в систему</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <p style="color: red">${requestScope.error}</p>
                        <div class="mb-3">
                            <label for="login" class="form-label">email</label>
                            <input type="email" value="${requestScope.email}" class="form-control" id="login" name="email">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" value="${requestScope.password}" class="form-control" id="password" name="password">
                        </div>
                        <button type="submit" class="btn btn-primary">Войти</button>
                    </form>
                </div>
            </div>
            <div id="add-info">
                <h5 class="mt-4">Нет аккаунта?</h5>
                <a href="${pageContext.request.contextPath}/registration" class="btn btn-primary">Зарегистрироваться</a>
                <h4 id="login-result"></h4>
            </div>
        </div>
    </div>
</div>

</div>
</body>
</html>
