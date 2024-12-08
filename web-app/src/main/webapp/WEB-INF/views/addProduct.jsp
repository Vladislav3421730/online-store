<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding product</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <style>
        <%@include file="/WEB-INF/css/addProducts.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="add-product">
    <form action="${pageContext.request.contextPath}/manager/product/add" method="post" enctype="multipart/form-data">
        <label for="name" class="form-label">Введите название товара</label>
        <input class="form-control" type="text" id="name" minlength="3" name="name" placeholder="Название товара" required>

        <label for="category" class="form-label">Введите категорию товара</label>
        <input class="form-control" type="text" id="category" minlength="3" name="category" placeholder="Категория товара" required>

        <label for="description" class="form-label">Введите описание товара</label>
        <textarea class="form-control" id="description" minlength="10" name="description" required></textarea>
        <label for="amount" class="form-label">Введите доступное количество на складе</label>
        <input class="form-control" id="amount" type="number" min="20" placeholder="Количество на складе" step="1" name="amount" required>
        <label for="coast" class="form-label">Введите цену товара</label>
        <input type="number" class="form-control" id="coast" min="0.01" placeholder="Цена товара" name="coast" step="0.01" required>
        <label for="file-input" class="form-label">Вставьте картинки, если хотите добавить больше нажмите +</label>
        <div id="file-input">
            <div id="file-window">
                <input type="file" accept="image/jpeg, image/png" id="file" name="file1">
            </div>
            <div >
                <button type="button" id="plus-button">+</button>
                <button type="button" id="minus-button">-</button>
            </div>
        </div>
        <input type="submit" class="btn btn-primary mt-4" value="Добавить">
    </form>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/addProduct.js"%>
</script>
</html>
