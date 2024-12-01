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
            height: auto;
            padding: 20px;
            margin: 20px;
            background-color: antiquewhite;
            border: 0px solid black;
            border-radius: 20px;
        }
        #file-input {
            display: flex;
            justify-content: space-between;
        }
        #file-window{
            display: flex;
            flex-direction: column;
        }
        #plus-button,#minus-button{
            width: 25px;
            height: 25px;
            padding: 2px;
            margin: 3px;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 0px solid black;
            border-radius: 6px;
            background-color: black;
            font-size: 20px;
            color: white;
            cursor: pointer;
            transition: all 0.2ms ease-in-out;
        }
        #plus-button:hover,
        #minus-button:hover{
            transform: scale(1.05);;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="add-product">
    <form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
        <label for="name" class="form-label">Введите название товара</label>
        <input class="form-control" type="text" id="name" name="name" placeholder="Название товара" required>

        <label for="category" class="form-label">Введите категорию товара</label>
        <input class="form-control" type="text" id="category" name="category" placeholder="Категория товара" required>

        <label for="description" class="form-label">Введите описание товара</label>
        <textarea class="form-control" id="description" name="description" required></textarea>
        <label for="amount" class="form-label">Введите доступное количество на складе</label>
        <input class="form-control" id="amount" type="number" placeholder="Количество на складе" step="1" name="amount" required>
        <label for="coast" class="form-label">Введите цену товара</label>
        <input type="number" class="form-control" id="coast" placeholder="Цена товара" name="coast" step="0.01" required>
        <label for="file-input" class="form-label">Вставьте картинки, если хотите добавить больше нажмите +</label>
        <div id="file-input">
            <div id="file-window">
                <input type="file" accept="image/jpeg, image/png" id="file" name="file">
            </div>
            <div >
                <button type="button" id="plus-button">+</button>
                <button type="button" id="minus-button">-</button>
            </div>
        </div>
        <input type="submit" class="btn btn-primary mt-2" value="Добавить">
    </form>
</div>
</body>
<script>

    const fileWindow = document.getElementById("file-window")
    console.log("length "+ fileWindow.children.length)
    document.getElementById("plus-button").addEventListener('click',function (){
        const newFileInput = document.createElement('input');
        newFileInput.style.paddingTop="5px"
        newFileInput.type = "file";
        newFileInput.accept = "image/jpeg, image/png";
        newFileInput.name = "file"+ fileWindow.children.length;
        newFileInput.id="file"+ fileWindow.children.length;
        console.log("add element "+newFileInput.name,)
        fileWindow.appendChild(newFileInput);
        console.log("length "+ fileWindow.children.length)
    })
    document.getElementById("minus-button").addEventListener('click',function (){
        fileWindow.removeChild(fileWindow.lastElementChild)

    })

</script>
</html>
