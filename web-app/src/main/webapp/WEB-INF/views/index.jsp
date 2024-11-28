<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main page</title>
    <style>
        img {
            width: 200px;
            height: 400px;
        }
    </style>
</head>
<jsp:include page="header.jsp"/>
<body>
<h1>Hello world</h1>
<div class="container mt-4">
    <c:forEach var="product" items="${requestScope.products}">
        <div>
            <c:choose>
                <c:when test="${product.getImageList().isEmpty()}">
                    <p>Тут должна быть картинка</p>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/image?id=${product.getImageList().get(0).getId()}"/>
                </c:otherwise>
            </c:choose>
            <p>Название: ${product.getTitle()}</p>
            <p>Описание: ${product.getDescription()}</p>
            <p>Остаток на складе ${product.getAmount()}</p>
            <p>Цена товара: ${product.getCoast()}</p>
        </div>
    </c:forEach>
    <button class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/product/add'">
        Добавить товар
    </button>

</div>


</body>
</html>