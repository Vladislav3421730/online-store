<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>edit product ${requestScope.product.getTitle()}</title>
    <style>
        <%@include file="/WEB-INF/css/addProducts.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="add-product">
    <form action="${pageContext.request.contextPath}/manager/product/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" value="${requestScope.product.getId()}" name="id">
        <label for="name" class="form-label">Изменить название товара</label>
        <input class="form-control" value="${requestScope.product.getTitle()}" type="text" id="name" minlength="3" name="name" placeholder="Название товара" required>

        <label for="category" class="form-label">Изменить категорию товара</label>
        <input class="form-control"  value="${requestScope.product.getCategory()}" type="text" id="category"
               minlength="3" name="category" placeholder="Категория товара" required>

        <label for="description" class="form-label">Изменить описание товара</label>
        <textarea class="form-control"  id="description" minlength="10" name="description" required>${requestScope.product.getDescription()}
        </textarea>
        <label for="amount" class="form-label">Введите доступное количество на складе</label>
        <input class="form-control" value="${requestScope.product.getAmount()}" id="amount" type="number" min="20" placeholder="Количество на складе" step="1" name="amount" required>
        <label for="coast" class="form-label">Введите цену товара</label>
        <input type="number" value="${requestScope.product.getCoast()}" class="form-control" id="coast" min="0.01" placeholder="Цена товара" name="coast" step="0.01" required>
        <label for="file-input" class="form-label">Вставьте картинки, если хотите добавить больше нажмите +.</label>
        <p> Если хотите измениить значение прошлых файлов,<br>
            их можно изменить нажав "выбрать файл" на кнопки,<br>
            которые уже  с самого начала были на форме</p>
        <div id="file-input">
            <div id="file-window">
                <c:forEach var="item" items="${requestScope.product.getImageList()}" varStatus="status">
                    <input type="file" class="mt-1 mb-1" accept="image/jpeg, image/png" id="file${status.index+1}"
                           name="file${status.index+1}"
                          multiple>
                </c:forEach>
            </div>
            <div >
                <button type="button" id="plus-button">+</button>
                <button type="button" id="minus-button">-</button>
            </div>
        </div>
        <input type="submit" class="btn btn-success mt-4" value="Редактировать">
    </form>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/addProduct.js"%>
</script>

</html>
