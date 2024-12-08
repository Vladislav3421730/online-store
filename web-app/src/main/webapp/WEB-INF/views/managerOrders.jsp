<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4">
    <c:choose>
        <c:when test="${requestScope.users.isEmpty()}">
            <h3>Пользователей не найдено</h3>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">номер</th>
                    <th scope="col">email</th>
                    <th scope="col">создан</th>
                    <th scope="col">Информация</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${requestScope.orders}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${order.getId()}</td>
                        <td>${order.getUser().getEmail()}</td>
                        <td><p><fmt:formatDate value="${order.getCreatedAtAsDate()}"
                                                             pattern="yyyy-MM-dd HH:mm"/></p></td>
                        <td>
                            <input type="button" class="btn btn-primary" value="Информация"
                                   onclick="window.location.href=
                                           '${pageContext.request.contextPath}/manager/order?orderId=${order.getId()}'">
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
