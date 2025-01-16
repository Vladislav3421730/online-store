<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>control products</title>
    <style>
        <%@include file="/WEB-INF/css/inputForms.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal/modalCoastFilter.jsp"/>
<div class="container mt-4">
    <div class="viewProductSearching mb-2">
        <form action="${pageContext.request.contextPath}/manager/product/search" method="get" class="w-100  mr-3">
            <div class="input-group">
                <input value="${requestScope.search}" type="text" class="form-control" id="search" placeholder="Найти"
                       name="id"/>
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit">Поиск</button>
                </div>
            </div>
        </form>
        <div>
            <button type="button" class="btn btn-outline-secondary" style="margin-bottom: 17px" data-toggle="modal"
                    data-target="#modal">
                Применить фильтры
            </button>
        </div>
    </div>
    <p style="color: red">${requestScope.error}</p>
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
                    <th scope="col">id</th>
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
                        <td>${product.getId()}</td>
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
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/manager/products?page=${currentPage - 1}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}">&laquo;
                        Назад</a>
                </c:if>
                <c:if test="${totalPages > 1}">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="${pageContext.request.contextPath}/manager/products?page=${i}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}"
                           class="${currentPage == i ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/manager/products?page=${currentPage + 1}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}">Вперёд
                        &raquo;</a>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
