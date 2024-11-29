<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        select {
            border: 1px solid black;
            border-radius: 5px;
            padding: 3px;
        }

        input {
            border: 0px solid black;
            border-bottom-width: 2px;
        }
    </style>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Фильтры</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/filter" method="get">
                    <label for="category" class="form-label">Категория</label><br>
                    <select id="category" name="category">
                        <option selected>Выберите категорию</option>
                        <option>Игрушки</option>
                        <option>Мужская одежда</option>
                        <option>Женская одежда</option>
                        <option>Для дома</option>
                        <option>Обувь</option>
                        <option>Инструменты</option>
                        <option>Техника</option>
                    </select><br>
                    <label for="sort" class="form-label mt-2">Сортировать по:</label><br>
                    <select id="sort" name="sort">
                        <option selected>Выберите сортировку</option>
                        <option value="cheap">Сначала подороже</option>
                        <option value="expensive">Сначала подешевле</option>
                        <option value="alphabet">По алфавиту</option>
                    </select><br>
                    <div class="d-flex align-items-center mt-2">
                        <div class="mt-2" style="margin-right: 10px">
                            <label for="minPrice" class="form-label">Минимальная цена:</label><br>
                            <input type="number" step="0.1" id="minPrice" name="minPrice" placeholder="0">
                        </div>
                        <div class="mt-2">
                            <label for="maxPrice" class="form-label">Максимальная цена:</label>
                            <input type="number" step="0.1" id="maxPrice" name="maxPrice" placeholder="10000"><br>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Закрыть</button>
                        <button type="submit" class="btn btn-primary">Применить фильтры</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    const options = document.querySelectorAll('#category option');
    options.forEach(option => {
        if(option.hasAttribute('selected')) {
            option.value='';
        }
        else {
            option.value = option.textContent;
        }
    });
</script>
</body>
</html>
