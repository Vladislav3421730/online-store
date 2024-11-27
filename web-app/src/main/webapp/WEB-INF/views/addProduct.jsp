<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding product</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <style>
        .add-product {

            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

        }

        form {
            font-family: "Arial Black";
            padding: 20px;
            margin: 20px;
            background-color: antiquewhite;
            border: 0px solid black;
            border-radius: 20px;

        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="add-product">
    <form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
        <p>Добавьте название</p>
        <input type="text" name="name" required>
        <p>Добавьте описание</p>
        <textarea name="description" required></textarea>
        <p>Введите доступное количество на данный момент</p>
        <input type="number" name="amount"  required>
        <p>Введите цену</p>
        <input type="number" name="coast" step="0.01" required>
        <p> Вставьте картинки</p>
            <input type="file" accept="image/jpeg, image/png" name="files" multiple>
        <input type="submit" class="btn btn-default" value="Добавить">
    </form>
</div>
</body>
</html>
