<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>control products</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4">
    <input type="button" class="btn btn-success mb-3" value="Добавить"
           onclick="window.location.href='${pageContext.request.contextPath}/manager/product/add'">
    <c:choose>
        <c:when test="${requestScope.products.isEmpty()}">
            <h3>Товаров не найдено</h3>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Название</th>
                    <th scope="col">Количество</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Информация</th>
                    <th scope="col">Редактировать</th>
                    <th scope="col">Удалить</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${requestScope.products}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${product.getTitle()}</td>
                        <td>${product.getAmount()}</td>
                        <td>${product.getCoast()}</td>
                        <td>
                            <input type="button" class="btn btn-primary" value="Информация"
                                   onclick="window.location.href=
                                           '${pageContext.request.contextPath}/product/get?id=${product.getId()}'">
                        </td>
                        <td>
                            <input type="button" class="btn btn-success" value="Редактировать"
                                   onclick="window.location.href=
                                           '${pageContext.request.contextPath}/manager/product/edit?id=${product.getId()}'">
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/manager/product/delete" method="post">
                                <input type="hidden" name="id" value="${product.getId()}">
                                <input type="submit" value="Удалить" class="btn btn-danger">
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
