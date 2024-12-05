<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Panel</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4">
    <form action="${pageContext.request.contextPath}/admin/search" method="get" class="w-100  mr-3">
        <div class="input-group mb-3">
            <input value="${requestScope.search}" type="text" class="form-control" id="search"
                   placeholder="Введите точный email пользователя"
                   name="search"/>
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit">Поиск</button>
            </div>
        </div>
    </form>
    <c:choose>
        <c:when test="${requestScope.users.isEmpty()}">
            <h3>Пользователей не найдено</h3>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">id</th>
                    <th scope="col">email</th>
                    <th scope="col">username</th>
                    <th scope="col">Забанить</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${user.getId()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getUsername()}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/bun" method="post">
                                <input type="hidden" name="userId" value="${user.getId()}">
                                <c:choose>
                                    <c:when test="${user.isBun()}">

                                        <input type="submit" class="btn btn-success" value="Разбанить">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" class="btn btn-danger" value="Забанить">
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
